package com.github.vole.auth.util.jackson2;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class",include = JsonTypeInfo.As.PROPERTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(using = DefaultSavedRequestDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true,value = {"string"})
public abstract  class DefaultSavedRequestMixin {

}
