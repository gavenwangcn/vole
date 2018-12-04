package com.github.vole.common.constants.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信通道模板
 */
public enum EnumSmsChannelTemplate {
    /**
     * 登录验证
     */
    LOGIN_NAME_LOGIN("loginCodeChannel", "登录验证"),
    /**
     * 服务异常提醒
     */
    SERVICE_STATUS_CHANGE("serviceStatusChange", "vole4Cloud");


    /**
     * 模板名称
     */
    @Getter
    @Setter
    private String template;
    /**
     * 模板签名
     */
    @Getter
    @Setter
    private String signName;

    EnumSmsChannelTemplate(String template, String signName) {
        this.template = template;
        this.signName = signName;
    }
}
