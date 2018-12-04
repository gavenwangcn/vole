package com.github.vole.portal.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.vole.portal.controller.BaseController;
import com.github.vole.portal.model.entity.SysLog;
import com.github.vole.portal.service.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志控制器
 */
@Controller
@RequestMapping("/admin/log")
public class LogController extends BaseController {

	@Autowired
	private ISysLogService sysLogService;
	
	/**
	 * 分页查询日志
	 */
    @RequestMapping("/list/{pageNumber}")
    public  String list(@PathVariable Integer pageNumber, @RequestParam(defaultValue="15") Integer pageSize, String search,String daterange,Model model){
    	
		Page<SysLog> page = getPage(pageNumber,pageSize);
		page.setAsc("create_time");
		// 查询分页
		QueryWrapper<SysLog> ew = new QueryWrapper<SysLog>();
		if(StringUtils.isNotBlank(search)){
			ew.like("user_name",search).or().like("title",search);
			model.addAttribute("search", search);
		}
		//日期查询
		if(StringUtils.isNotBlank(daterange)){
			model.addAttribute("daterange", daterange);
			String[] dateranges = StringUtils.split(daterange, "-");
			ew.ge("create_time",dateranges[0].trim().replaceAll("/","-") + " 00:00:00")
					.and(q ->ew.le("create_time",dateranges[1].trim().replaceAll("/","-") + " 23:59:59"));
		}
		Page<SysLog> pageData = (Page<SysLog>) sysLogService.page(page, ew);
		model.addAttribute("pageData", pageData);
		return "ftl/admin/log/list";
    } 
    
    /**
     * 获取参数
     */
    @RequestMapping("/params/{id}")
    @ResponseBody
    public String params(@PathVariable String id,Model model){
    	SysLog sysLog = sysLogService.getById(id);
    	return sysLog.getParams();
    }
    
}
