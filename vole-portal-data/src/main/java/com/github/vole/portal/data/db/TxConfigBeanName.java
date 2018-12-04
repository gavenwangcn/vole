package com.github.vole.portal.data.db;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 全局事务配置
 */
@Configuration
public class TxConfigBeanName {

	// 事务管理器
	@Autowired
	private DataSourceTransactionManager transactionManager;

	/**
	 * 创建事务通知
	 * @return
	 */
	@Bean(name = "txAdvice")
	public TransactionInterceptor getAdvisor(){
		Properties properties = new Properties();
		// PROPAGATION_REQUIRED如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。
		properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("write*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("batch*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("create*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("do*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("edit*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("execute*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("validate*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("export*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("import*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("process*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("publish*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("remove*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("submit*", "PROPAGATION_REQUIRED,-Exception");
		properties.setProperty("set*", "PROPAGATION_REQUIRED,-Exception");

		properties.setProperty("onAuthenticationSuccess", "PROPAGATION_REQUIRED,-Exception");

		properties.setProperty("get*", "readOnly");
		properties.setProperty("load*", "readOnly");
		properties.setProperty("query*", "readOnly");
		properties.setProperty("search*", "readOnly");
		properties.setProperty("find*", "readOnly");
		properties.setProperty("*", "readOnly");
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor(transactionManager, properties);
//		// 设置事务超时时间
//		transactionManager.setDefaultTimeout(30);
		return transactionInterceptor;
	}

	/**
	 * 使用BeanNameAutoProxyCreator来创建AOP代理
	 * (根据代理bean所需的事务拦截器和指定需要代理的Bean的名称表达式做匹配)
	 * @return
	 */
	@Bean
	public BeanNameAutoProxyCreator txProxy() {
		BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
		// 设置拦截链名字(这些拦截器是有先后顺序的)
		creator.setInterceptorNames("txAdvice");
		// 设置要创建代理的那些Bean的名字
		creator.setBeanNames("*Service", "*ServiceImpl");
		creator.setProxyTargetClass(true);
		return creator;
	}
}