package com.github.vole.common.constants;


public interface SecurityConstants {
    /**
     * 用户信息头
     */
    String USER_HEADER = "x-member-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";
    /**
     * 项目的license
     */
    String Vole_LICENSE = "made by vole";
    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * oauth cookie
     */
    String OAUTH_TOKEN_URL = "/oauth/cookie";

    /**
     * 手机登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/cookie";

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 手机号的处理验证码的url前缀
     */
    String MOBILE_VALIDATE_CODE_URL_PREFIX = "/smsCode";

    /**
     * 默认生成图形验证码宽度
     */
    String DEFAULT_IMAGE_WIDTH = "100";

    /**
     * 默认生成图像验证码高度
     */
    String DEFAULT_IMAGE_HEIGHT = "40";

    /**
     * 默认生成图形验证码长度
     */
    String DEFAULT_IMAGE_LENGTH = "4";

    /**
     * 默认生成图形验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;
    /**
     * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
     */
    String DEFAULT_COLOR_FONT = "black";

    /**
     * 图片边框
     */
    String DEFAULT_IMAGE_BORDER = "no";
    /**
     * 默认图片间隔
     */
    String DEFAULT_CHAR_SPACE = "5";

    /**
     * 默认保存code的前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";
    /**
     * 验证码文字大小
     */
    String DEFAULT_IMAGE_FONT_SIZE = "30";

    /**
     * cookie-uservo
     */
    String TOKEN_USER_DETAIL = "cookie-member-detail";

    /**
     * oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     *JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from oauth_client_details";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

}
