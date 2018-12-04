package com.github.vole.portal.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.vole.common.utils.R;
import com.github.vole.portal.controller.BaseController;
import com.github.vole.portal.model.entity.SysMenu;
import com.github.vole.portal.service.ISysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
/**
 * 角色控制器
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

	/**
	 * 菜单服务
	 */
	@Autowired
	private ISysMenuService sysMenuService;
	
	/**
	 * 分页查询菜单
	 */
    @RequestMapping("/list/{pageNumber}")
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize, String search,Model model){
    	
		Page<SysMenu> page = getPage(pageNumber,pageSize);
		page.setAsc("code");
		// 查询分页
		QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
		if(StringUtils.isNotBlank(search)){
			ew.like("menu_name",search);
			model.addAttribute("search",search);
		}
		Page<SysMenu> pageData = (Page<SysMenu>) sysMenuService.page(page, ew);
		
		for(SysMenu menu : pageData.getRecords()){
			if(menu.getParentId() == null || menu.getDeep() !=3){
				menu.setMenuName(StringUtils.join("<i class='fa fa-folder-open'></i> ",menu.getMenuName()));
			}else{
				menu.setMenuName(StringUtils.join("<i class='fa fa-file'></i> ",menu.getMenuName()));
			}
			for(int i=1;i<menu.getDeep();i++){
				menu.setMenuName(StringUtils.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",menu.getMenuName()));
			}
			
		}
		
		model.addAttribute("pageData", pageData);
		return "ftl/admin/menu/list";
    } 
   
    /**
     * 增加菜单
     */
    @RequestMapping("/add")
    public String add(Model model){
		QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
    	ew.orderByAsc("code");
    	ew.eq("parent_id","0");
    	List<SysMenu> list = sysMenuService.list(ew);
    	model.addAttribute("list",list);
    	return "ftl/admin/menu/add";
		
    } 
    /**
     * 添加目录
     */
    @RequestMapping("/doAddDir")
	@ResponseBody
    public R<Boolean> doAddDir(SysMenu sysMenu, Model model){
    	
    	sysMenu.setParentId(0);
    	sysMenu.setDeep(1);
    	return R.rest(sysMenuService.save(sysMenu));
    }
    
    /**
     * 添加菜单
     */
    @RequestMapping("/doAddMenu")
	@ResponseBody
    public R<Boolean> doAddMenu(SysMenu sysMenu,Model model){
    	sysMenu.setDeep(2);
		return R.rest(sysMenuService.save(sysMenu));
    }
    /**
     * 编辑菜单
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id,Model model){
    	SysMenu sysMenu =sysMenuService.getById(id);
    	model.addAttribute("sysMenu", sysMenu);
    	
    	if(sysMenu.getDeep() == 1){
    		return  "ftl/admin/menu/edit";
    	}else if(sysMenu.getDeep() == 2){
			QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
			ew.orderByAsc("code");
        	ew.eq("parent_id","0");
        	List<SysMenu> list = sysMenuService.list(ew);
        	model.addAttribute("list",list);
        	return "ftl/admin/menu/editMenu";
    	}
    	else{
			QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
			ew.orderByAsc("code");
        	ew.eq("parent_id","0");
        	List<SysMenu> list = sysMenuService.list(ew);
        	model.addAttribute("list",list);
        	model.addAttribute("pSysMenu",sysMenuService.getById(sysMenu.getParentId()));
    		return  "ftl/admin/menu/editAction";
    	}
    } 
    
    /**
     * 执行编辑菜单
     */
    @RequestMapping("/doEdit")
	@ResponseBody
    public R<Boolean> doEdit(SysMenu sysMenu){
    	return R.rest(sysMenuService.updateById(sysMenu));
    }
    
    /**
     * 执行编辑菜单
     */
    @RequestMapping("/delete")
    @ResponseBody
    public R<Boolean> delete(String id){
    	return R.rest(sysMenuService.removeById(Integer.valueOf(id)));
    }
    
    /**
     * 根据父节点获取子菜单
     */
    @RequestMapping("/json")
    @ResponseBody
    public R<List<Map<String, Object>>> json(String parentId){
		QueryWrapper<SysMenu> ew = new QueryWrapper<SysMenu>();
		ew.orderByAsc("sort");
    	ew.eq("parent_id",parentId);
    	List<SysMenu> list = sysMenuService.list(ew);
    	
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
    	for(SysMenu m : list){
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("id", m.getId());
    		map.put("text",StringUtils.join(m.getCode(),"-",m.getMenuName()));
    		listMap.add(map);
    	}
    	return new R<List<Map<String, Object>>>(listMap);
    }
    
    @RequestMapping("/doAddAction")
	@ResponseBody
    public R<Boolean>  doAddAction(SysMenu sysMenu,Model model){
    	sysMenu.setDeep(3);
		return R.rest(sysMenuService.save(sysMenu));
    }
}
