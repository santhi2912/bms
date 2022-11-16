package com.micro.zuulapigatewayserver.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object run() throws ZuulException {
		//getting the current HTTP request that is to be handle  
		HttpServletRequest request=RequestContext.getCurrentContext().getRequest();  
		//prints the detail of the requestin the log  
		logger.info("request -> {} request uri-> {}", request, request.getRequestURI());  
		return null;  
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
