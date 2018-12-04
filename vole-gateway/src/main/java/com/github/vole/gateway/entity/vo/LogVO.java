package com.github.vole.gateway.entity.vo;

import com.github.vole.gateway.entity.SysLog;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private SysLog sysLog;
    private String username;
}
