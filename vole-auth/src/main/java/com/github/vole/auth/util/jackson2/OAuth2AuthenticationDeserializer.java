package com.github.vole.auth.util.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.IOException;

public class OAuth2AuthenticationDeserializer extends JsonDeserializer<OAuth2Authentication> {

    @Override
    public OAuth2Authentication deserialize(JsonParser  jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JsonNode userAuthenticationNode = readJsonNode(jsonNode, "userAuthentication");
        JsonNode details = readJsonNode(jsonNode, "details");
        JsonNode storedRequest = readJsonNode(jsonNode, "storedRequest");
        OAuth2Request request = null;
        if (storedRequest != null && !storedRequest.isMissingNode()) {
            request = mapper.readValue(storedRequest.traverse(mapper),OAuth2Request.class);
        }
        Authentication auth = null;
        if (userAuthenticationNode != null && !userAuthenticationNode.isMissingNode()) {
            auth = mapper.readValue(userAuthenticationNode.traverse(mapper),UsernamePasswordAuthenticationToken.class);
        }

        OAuth2Authentication result =  new OAuth2Authentication(request,auth);
        if (details != null && !details.isMissingNode()) {
            OAuth2AuthenticationDetails AuthenticationDetails = mapper.readValue(details.traverse(mapper),OAuth2AuthenticationDetails.class);
            result.setDetails(AuthenticationDetails);
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}


