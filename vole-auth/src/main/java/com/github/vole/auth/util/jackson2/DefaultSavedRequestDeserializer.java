package com.github.vole.auth.util.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedCookie;

import java.io.IOException;
import java.util.*;

public class DefaultSavedRequestDeserializer extends JsonDeserializer<DefaultSavedRequest> {
    @Override
    public DefaultSavedRequest deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JsonNode contextPath = readJsonNode(jsonNode, "contextPath");
        JsonNode method = readJsonNode(jsonNode, "method");
        JsonNode pathInfoNode = readJsonNode(jsonNode, "pathInfo");
        JsonNode queryStringNode = readJsonNode(jsonNode, "queryString");
        JsonNode requestURI = readJsonNode(jsonNode, "requestURI");
        JsonNode requestURL = readJsonNode(jsonNode, "requestURL");
        JsonNode scheme = readJsonNode(jsonNode, "scheme");
        JsonNode serverName = readJsonNode(jsonNode, "serverName");
        JsonNode servletPath = readJsonNode(jsonNode, "servletPath");
        JsonNode serverPort = readJsonNode(jsonNode, "serverPort");
        String pathInfo = null;
        if (!pathInfoNode.isMissingNode() && !pathInfoNode.isNull()) {
            pathInfo = pathInfoNode.asText();
        }
        String queryString = null;
        if (!queryStringNode.isMissingNode() && !queryStringNode.isNull()) {
            pathInfo = pathInfoNode.asText();
        }

        ArrayList<SavedCookie> cookies = mapper.convertValue(jsonNode.get("cookies"), new TypeReference<ArrayList<SavedCookie>>() {
        });
        List<Locale> locales = mapper.convertValue(jsonNode.get("locales"), new TypeReference<List<Locale>>() {
        });
        Map<String, List<String>> headers = mapper.convertValue(jsonNode.get("headers"), new TypeReference<Map<String, List<String>>>() {
        });
        Map<String, String[]> parameters = mapper.convertValue(jsonNode.get("parameters"), new TypeReference<Map<String, String[]>>() {
        });
        DefaultSavedRequestDeserializer.Builder builder =
                new DefaultSavedRequestDeserializer.Builder(contextPath.asText(), method.asText(), pathInfo, queryString,
                        requestURI.asText(), requestURL.asText(), scheme.asText(), serverName.asText(), servletPath.asText(), serverPort.asInt(), cookies, locales, headers, parameters);
        DefaultSavedRequest result = builder.build();
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

    class Builder extends DefaultSavedRequest.Builder {

        public Builder(String contextPath, String method, String pathInfo, String queryString, String requestURI, String requestURL, String scheme, String serverName, String servletPath,
                       int serverPort, ArrayList<SavedCookie> cookies, List<Locale> locales, Map<String, List<String>> headers, Map<String, String[]> parameters) {
            super.setContextPath(contextPath);
            super.setMethod(method);
            if (StringUtils.isNotBlank(pathInfo)) {
                super.setPathInfo(pathInfo);
            }
            if (StringUtils.isNotBlank(queryString)) {
                super.setQueryString(queryString);
            }
            super.setRequestURI(requestURI);
            super.setRequestURL(requestURL);
            super.setScheme(scheme);
            super.setServerName(serverName);
            super.setServletPath(servletPath);
            super.setServerPort(serverPort);
            if (CollectionUtils.isNotEmpty(cookies)) {
                super.setCookies(cookies);
            }
            if (CollectionUtils.isNotEmpty(locales)) {
                super.setLocales(locales);
            }
            if (headers != null && !headers.isEmpty()) {
                super.setHeaders(headers);
            }
            if (parameters != null && !parameters.isEmpty()) {
                super.setParameters(parameters);
            }
        }
    }
}
