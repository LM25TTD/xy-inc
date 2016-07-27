package com.lm25ttd.xyinc.rest.config;

import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Bootstraps the web application and replaces the need of declaring a web.xml.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public class RESTInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static final String[] SERVLET_MAPPINGS = { "/" };
	private static final Class<?>[] SERVLET_CONFIG = { RESTSettings.class };
	private static final Class<?>[] ROOT_CONFIG = { RESTSecuritySettings.class, XYIncDatabaseSettings.class };

	@Override
	protected String getServletName() {
		return "XYInc";
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return ROOT_CONFIG;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return SERVLET_CONFIG;
	}

	@Override
	protected String[] getServletMappings() {
		return SERVLET_MAPPINGS;
	}

	@Override
	protected Filter[] getServletFilters() {
		OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
		filter.setEntityManagerFactoryBeanName("xyincEntityManagerFactory");
		return new Filter[] { filter };
	}
}
