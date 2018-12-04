package com.github.vole.passport.common.filter;

import com.github.vole.passport.common.contants.PassportConstants;
import com.github.vole.passport.common.exception.ClientRedirectRequiredException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PassportClientContextFilter implements Filter, InitializingBean {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setAttribute(PassportConstants.CURRENT_URI, calculateCurrentUri(request));

        try {
            chain.doFilter(servletRequest, servletResponse);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            ClientRedirectRequiredException redirect = (ClientRedirectRequiredException) throwableAnalyzer
                    .getFirstThrowableOfType(
                            ClientRedirectRequiredException.class, causeChain);
            if (redirect != null) {
                redirectUser(redirect, request, response);
            } else {
                if (ex instanceof ServletException) {
                    throw (ServletException) ex;
                }
                if (ex instanceof RuntimeException) {
                    throw (RuntimeException) ex;
                }
                throw new NestedServletException("Unhandled exception", ex);
            }
        }
    }


    protected void redirectUser(ClientRedirectRequiredException e,
                                HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String redirectUri = e.getRedirectUri();
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(redirectUri);
        Map<String, String> requestParams = e.getRequestParams();
        for (Map.Entry<String, String> param : requestParams.entrySet()) {
            builder.queryParam(param.getKey(), param.getValue());
        }

        this.redirectStrategy.sendRedirect(request, response, builder.build()
                .encode().toUriString());
    }

    /**
     * Calculate the current URI given the request.
     */
    protected String calculateCurrentUri(HttpServletRequest request)
            throws UnsupportedEncodingException {
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder
                .fromRequest(request);
        String queryString = request.getQueryString();
        boolean legalSpaces = queryString != null && queryString.contains("+");
        if (legalSpaces) {
            builder.replaceQuery(queryString.replace("+", "%20"));
        }
        UriComponents uri = builder.build();
        String query = uri.getQuery();
        if (legalSpaces) {
            query = query.replace("%20", "+");
        }
        return ServletUriComponentsBuilder.fromUri(uri.toUri())
                .replaceQuery(query).build().toString();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redirectStrategy, "A redirect strategy must be supplied.");
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                public Throwable extractCause(Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable,
                            ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }
}
