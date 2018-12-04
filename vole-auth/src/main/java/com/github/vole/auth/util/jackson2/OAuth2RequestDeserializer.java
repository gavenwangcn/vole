package com.github.vole.auth.util.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OAuth2RequestDeserializer extends JsonDeserializer<OAuth2Request> {

    @Override
    public OAuth2Request deserialize(JsonParser  jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JsonNode clientId = readJsonNode(jsonNode, "clientId");
        Set<String> scope = mapper.convertValue(jsonNode.get("scope"), new TypeReference<Set<String>>() {});
        Map<String, String> requestParameters = mapper.convertValue(jsonNode.get("requestParameters"), new TypeReference<Map<String, String>>() {});
        HashSet<String> resourceIds = mapper.convertValue(jsonNode.get("resourceIds"), new TypeReference<HashSet<String>>() {});
        HashSet<GrantedAuthority> authorities = mapper.convertValue(jsonNode.get("authorities"), new TypeReference<HashSet<GrantedAuthority>>() {});
        HashSet<String> responseTypes = mapper.convertValue(jsonNode.get("authorities"), new TypeReference<HashSet<String>>() {});
        HashMap<String, Serializable> extensions = mapper.convertValue(jsonNode.get("extensions"), new TypeReference<HashMap<String, Serializable>>() {});
        JsonNode approved = readJsonNode(jsonNode, "approved");
        JsonNode redirectUriNode = readJsonNode(jsonNode, "redirectUri");
        OAuth2Request result = new OAuth2Request(requestParameters, clientId.asText(),
                authorities, approved.asBoolean(), scope,
                resourceIds, redirectUriNode.asText(), responseTypes,
                extensions);
        JsonNode refreshTokenRequest = readJsonNode(jsonNode, "refresh");
        if (refreshTokenRequest != null && !refreshTokenRequest.isMissingNode()) {
            TokenRequest tokenRequest = mapper.readValue(refreshTokenRequest.traverse(mapper),TokenRequest.class);
            result =result.refresh(tokenRequest);
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}


