package com.github.vole.mps.constants;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信通道模板
 */
public class GrantType {

//    /**
//     * 客户端验证
//     */
//    CLIENT("client", "客户端验证"),
//    /**
//     * 用户名密码验证
//     */
//    PASSWORD("password", "用户名密码验证"),
//    /**
//     * 刷新验证
//     */
//    REFRESH_TOKEN("refresh_token","刷新验证"),
//    /**
//     * 授权验证
//     */
//    AUTHORIZATION_CODE("authorization_code", "授权验证");


    /**
     * 模板名称
     */
    @Getter
    @Setter
    private String key;
    /**
     * 模板签名
     */
    @Getter
    @Setter
    private String name;

    GrantType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static List<GrantType> list(){
        List<GrantType>  grantTypes = new ArrayList<GrantType>();
        GrantType client = new GrantType("client", "客户端验证");
        grantTypes.add(client);
        GrantType password = new GrantType("password", "用户名密码验证");
        grantTypes.add(password);
        GrantType refreshToken = new GrantType("refresh_token", "刷新验证");
        grantTypes.add(refreshToken);
        GrantType authorizationCode = new GrantType("authorization_code", "授权验证");
        grantTypes.add(authorizationCode);
        return grantTypes;
    }
}
