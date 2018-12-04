package com.github.vole.mps.controller;


import com.github.vole.common.constants.SecurityConstants;
import com.github.vole.common.utils.R;
import com.github.vole.common.validate.Assert;
import com.github.vole.mps.service.MemberService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;


/**
 * 验证码提供
 */
@Controller
public class ValidateCodeController {
    @Autowired
    private Producer producer;
    @Autowired
    private MemberService memberService;

    /**
     * 创建验证码
     *
     * @param request request
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{randomStr}")
    public void createCode(@PathVariable String randomStr, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Assert.isBlank(randomStr, "机器码不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        memberService.saveImageCode(randomStr, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "JPEG", out);
        out.close();
    }

    /**
     * 发送手机验证码
     * 后期要加接口限制
     *
     * @param mobile 手机号
     * @return R
     */
    @ResponseBody
    @GetMapping(SecurityConstants.MOBILE_VALIDATE_CODE_URL_PREFIX + "/{mobile}")
    public R<Boolean> createCode(@PathVariable String mobile) {
        Assert.isBlank(mobile, "手机号不能为空");
        return memberService.sendSmsCode(mobile);
    }
}
