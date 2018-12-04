package com.github.vole.portal.common.fegin;

import com.github.vole.portal.common.fegin.fallback.SettingServiceFallbackImpl;
import com.github.vole.portal.common.vo.SysSettingMenuVO;
import com.github.vole.portal.common.vo.SysSettingVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "vole-portal-data", fallback = SettingServiceFallbackImpl.class )
public interface SettingService {

    @RequestMapping(value="/setting/findSysByGlobalKey",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    SysSettingVO findSysByGlobalKey(@RequestBody String sysGlobalKey);

    @GetMapping(value="/setting/findSysMenusById/{id}")
    List<SysSettingMenuVO> findSysMenusById(@PathVariable("id") String sysId);
}
