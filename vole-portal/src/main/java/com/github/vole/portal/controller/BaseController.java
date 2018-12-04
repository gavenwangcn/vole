package com.github.vole.portal.controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.vole.common.config.db.Query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vole.portal.common.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 基础控制器
 */
@Slf4j
public class BaseController {

	@Resource
	protected HttpServletRequest request;

	@Resource
	protected HttpServletResponse response;

	@Resource
	protected HttpSession session;

	@Resource
	protected ServletContext application;


	/**
	 * <p>
	 * 获取分页对象
	 * </p>
	 */
	protected <T> Page<T> getPage(int pageNumber) {
		return getPage(pageNumber,15);
	}


	/**
	 * <p>
	 * 获取分页对象
	 * </p>
	 * 
	 * @param pageSize
	 *            每页显示数量
	 * @return
	 */
	protected <T> Page<T> getPage( int pageNumber,int pageSize) {
		return new Query<T>(pageNumber, pageSize);
	}


	/**
	 * 重定向至地址 url
	 * 
	 * @param url
	 *            请求地址
	 * @return
	 */
	protected String redirectTo( String url ) {
		StringBuffer rto = new StringBuffer("redirect:");
		rto.append(url);
		return rto.toString();
	}


	/**
	 * 
	 * 返回 JSON 格式对象
	 * 
	 * @param object
	 *            转换对象
	 * @return
	 */
	protected String toJson( Object object ) {
		return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
	}


	/**
	 * 
	 * 返回 JSON 格式对象
	 * 
	 * @param object
	 *            转换对象
	 * @param format
	 *            序列化特点
	 * @return
	 */
	protected String toJson( Object object, String format ) {
		if ( format == null ) {
			return toJson(object);
		}
		return JSON.toJSONStringWithDateFormat(object, format, SerializerFeature.WriteDateUseDateFormat);
	}

	/**
	 * @return 角色名
	 */
	public List<GrantedAuthority> getRoles() {
		return SecurityContextUtil.getRoles();
	}

	/**
	 * @return 用户ID
	 */
	public Integer getUserId() {
		return SecurityContextUtil.getUserId();
	}


}
