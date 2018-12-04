package com.github.vole.mps.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.vole.common.utils.R;
import com.github.vole.portal.common.controller.AbstractController;
import com.github.vole.mps.constants.GrantType;
import com.github.vole.mps.model.entity.OauthClientDetails;
import com.github.vole.mps.service.OauthClientDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 */
@Controller
@RequestMapping("/mps/client")
public class OauthClientDetailsController extends AbstractController<OauthClientDetailsService,OauthClientDetails> {


    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysOauthClientDetails
     */
    @GetMapping("/{id}")
    public OauthClientDetails get(@PathVariable Integer id) {
        return baseService.getById(id);
    }


    @Override
    protected String getTemplatePath() {
        return "ftl/mps/oauth2/";
    }

    @Override
    protected QueryWrapper<OauthClientDetails> getSearchQW(String search, Model model) {
        QueryWrapper<OauthClientDetails> qw = new QueryWrapper<OauthClientDetails>();
        if(StringUtils.isNotBlank(search)){
            qw.like("clientId",search);
            model.addAttribute("search",search);
        }
        return qw;
    }


    @Override
    protected void addModelData(Model model) {
        List<GrantType> grantTypes = GrantType.list();
        model.addAttribute("grantTypes",grantTypes);
    }

    /**
     * 验证会员名是否已存在
     */
    @RequestMapping("/checkClient")
    @ResponseBody
    public R<Boolean> checkClient(String clientId){
        List<OauthClientDetails> list = baseService.list(new QueryWrapper<OauthClientDetails>().eq("client_id", clientId));
        R r = new R<Boolean>(true);
        if(list.size() > 0){
            r.setData(false);
            r.setCode(R.FAIL);
            r.setMsg("客户端已存在");
        }
        return r;
    }

    /**
     * 执行新增
     */
    @RequestMapping("/doAddAction")
    @ResponseBody
    public R<Boolean> doAdd(OauthClientDetails clientDetails, @RequestParam(value="grantTypes[]",required=false) String[] grantTypes){
        String authorizedGrantTypes = StringUtils.join(grantTypes,",");
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        baseService.save(clientDetails);
        return R.rest(true);
    }

    /**
     * 执行编辑
     */
    @RequestMapping("/doEditAction")
    @ResponseBody
    public  R<Boolean> doEdit(OauthClientDetails clientDetails,@RequestParam(value="grantTypes[]",required=false) String[] grantTypes,Model model){
        String authorizedGrantTypes = StringUtils.join(grantTypes,",");
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        baseService.saveOrUpdate(clientDetails);
        return R.rest(true);
    }
}
