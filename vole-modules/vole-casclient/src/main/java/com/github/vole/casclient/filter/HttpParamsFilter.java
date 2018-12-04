package com.github.vole.casclient.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HttpParamsFilter implements Filter {
    public final static String REQUESTED_URL = "CasRequestedUrl";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        String requestPath = httpRequest.getRequestURL().toString();

        session.setAttribute(REQUESTED_URL, requestPath);

        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }
}

