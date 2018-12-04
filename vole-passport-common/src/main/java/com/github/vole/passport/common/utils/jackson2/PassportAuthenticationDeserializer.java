package com.github.vole.passport.common.utils.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.vole.passport.common.auth.PassportAuthentication;
import com.github.vole.passport.common.auth.PassportAuthenticationDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

public class PassportAuthenticationDeserializer extends JsonDeserializer<PassportAuthentication> {

    @Override
    public PassportAuthentication deserialize(JsonParser  jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JsonNode authoritiesNode = readJsonNode(jsonNode, "authorities");
        JsonNode detailsNode = readJsonNode(jsonNode, "details");
        JsonNode principal = readJsonNode(jsonNode, "principal");
        JsonNode userId = readJsonNode(jsonNode, "userId");

        List<SimpleGrantedAuthority>  authorities = null;
        if (authoritiesNode != null && !authoritiesNode.isMissingNode()) {
            authorities = mapper.convertValue(authoritiesNode,
                    new TypeReference<List<SimpleGrantedAuthority>>() {});
        }

        PassportAuthentication result =  new PassportAuthentication(authorities);
        result.setPrincipal(principal.asText());
        result.setUserId(userId.asInt());
        if (detailsNode != null && !detailsNode.isMissingNode()) {
            PassportAuthenticationDetails details = mapper.convertValue(detailsNode,
                    new TypeReference<PassportAuthenticationDetails>() {});
            result.setDetails(details);
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}


