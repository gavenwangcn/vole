package com.github.vole.common.utils;


import com.github.vole.common.constants.CommonConstant;
import com.github.vole.common.security.B64Encrypter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 认证授权相关工具类
 */
@Slf4j
public class AuthUtils {
    private static final String BASIC_ = "Basic ";

    /**
     * 从header 请求中的clientId/clientsecect
     *
     * @param header header中的参数
     * @throws RuntimeException if the Basic header is not present or is not valid
     *                          Base64
     */
    public static String[] extractAndDecodeHeader(String header)
            throws IOException {

        String base64Token = header.substring(6);
        byte[] decoded;
        try {
            decoded = B64Encrypter.decrypt(base64Token);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Failed to decode basic authentication cookie");
        }

        String token = new String(decoded, CommonConstant.UTF8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new RuntimeException("Invalid basic authentication cookie");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    /**
     * *从header 请求中的clientId/clientsecect
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String[] extractAndDecodeHeader(HttpServletRequest request)
            throws IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BASIC_)) {
            throw new RuntimeException("请求头中client信息为空");
        }

        return extractAndDecodeHeader(header);
    }
}
