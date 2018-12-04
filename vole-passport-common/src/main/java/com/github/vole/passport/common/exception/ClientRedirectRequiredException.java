package com.github.vole.passport.common.exception;

import java.util.Map;

public class ClientRedirectRequiredException extends RuntimeException {

    private final String redirectUri;

    private final Map<String, String> requestParams;

    public ClientRedirectRequiredException(String redirectUri, Map<String, String> requestParams){

        super("A redirect is required to get the passport token");
        this.redirectUri = redirectUri;
        this.requestParams = requestParams;

    }

    public String getRedirectUri() {
        return redirectUri;
    }


    public Map<String, String> getRequestParams() {
        return requestParams;
    }
}
