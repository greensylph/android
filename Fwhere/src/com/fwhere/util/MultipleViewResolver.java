
package com.fwhere.util;

import java.util.Locale;
import java.util.Map;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/** 
 * @version: 1.0
 */
public class MultipleViewResolver implements ViewResolver {

	private Map<String, ViewResolver> resolvers;

	public void setResolvers(Map<String, ViewResolver> resolvers) {
		this.resolvers = resolvers;
	}
	/** 
	 */
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		int n = viewName.lastIndexOf(".");
		if (n == (-1)){
			return null;
		}

		String suffix = viewName.substring(n + 1);

		ViewResolver resolver = resolvers.get(suffix);
		if (resolver != null){
			return resolver.resolveViewName(viewName, locale);
		}

		return null;
	}

}
