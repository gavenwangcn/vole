package com.github.vole.auth.util.jackson2;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import java.io.IOException;
import java.util.*;

public class OAuth2AccessTokenDeserializer extends JsonDeserializer<OAuth2AccessToken> {

    public OAuth2AccessTokenDeserializer(){}

    @Override
    public OAuth2AccessToken deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        String tokenValue = null;
        String tokenType = null;
        String refreshToken = null;
        Long expiresIn = null;
        Set<String> scope = null;
        Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

        // TODO What should occur if a parameter exists twice
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String name = jp.getCurrentName();
            if (name == null) {
                break;
            }
            if (OAuth2AccessToken.ACCESS_TOKEN.equals(name)) {
                tokenValue = jp.getText();
            }
            else if (OAuth2AccessToken.TOKEN_TYPE.equals(name)) {
                tokenType = jp.getText();
            }
            else if (OAuth2AccessToken.REFRESH_TOKEN.equals(name)) {
                refreshToken = jp.getText();
            }
            else if (OAuth2AccessToken.EXPIRES_IN.equals(name)) {
                try {
                    expiresIn = jp.getLongValue();
                } catch (JsonParseException e) {
                    expiresIn = Long.valueOf(jp.getText());
                }
            }
            else if (OAuth2AccessToken.SCOPE.equals(name)) {
                scope = parseScope(jp);
            } else {
                additionalInformation.put(name, jp.readValueAs(Object.class));
            }
            jp.nextToken();
        }

        // TODO What should occur if a required parameter (tokenValue or tokenType) is missing?

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenValue);
        accessToken.setTokenType(tokenType);
        if (expiresIn != null) {
            accessToken.setExpiration(new Date(System.currentTimeMillis() + (expiresIn * 1000)));
        }
        if (refreshToken != null) {
            accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
        }
        accessToken.setScope(scope);
        accessToken.setAdditionalInformation(additionalInformation);

        return accessToken;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
                                      TypeDeserializer typeDeserializer) throws IOException {
        return deserialize(p, ctxt);
    }

    private Set<String> parseScope(JsonParser jp) throws IOException {
        Set<String> scope;
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            scope = new TreeSet<String>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                scope.add(jp.getValueAsString());
            }
        } else {
            String text = jp.getText();
            scope = OAuth2Utils.parseParameterList(text);
        }
        return scope;
    }
}
