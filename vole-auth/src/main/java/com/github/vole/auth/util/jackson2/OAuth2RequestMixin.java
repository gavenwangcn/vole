package com.github.vole.auth.util.jackson2;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class",include = JsonTypeInfo.As.PROPERTY)
@JsonDeserialize(using = OAuth2RequestDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
//@JsonIgnoreProperties(ignoreUnknown = true,value = {"refresh"})
public abstract  class OAuth2RequestMixin {
}
