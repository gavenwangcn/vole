package com.github.vole.auth.util.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.github.vole.auth.model.vo.RoleVO;
import com.github.vole.auth.util.UserDetailsImpl;
import java.io.IOException;
import java.util.List;

public class UserDetailsImplDeserializer extends JsonDeserializer<UserDetailsImpl> {

    public UserDetailsImplDeserializer(){}

    @Override
    public UserDetailsImpl deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        JsonNode password = readJsonNode(jsonNode, "password");
        JsonNode userId = readJsonNode(jsonNode, "userId");
        JsonNode username = readJsonNode(jsonNode, "username");
        JsonNode status = readJsonNode(jsonNode, "status");
        List<RoleVO> roleVoList = mapper.convertValue(jsonNode.get("roleVoList"), new TypeReference<List<RoleVO>>() {
        });
        UserDetailsImpl result =  new UserDetailsImpl();
        result.setUserId(userId.asLong());
        result.setUsername(username.asText());
        result.setPassword(password.asText());
        result.setStatus(status.asText());
        result.setRoleVoList(roleVoList);
        return result;
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt,
                                      TypeDeserializer typeDeserializer) throws IOException {
        return deserialize(p, ctxt);
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
