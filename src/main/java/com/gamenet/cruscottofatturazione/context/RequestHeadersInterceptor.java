package com.gamenet.cruscottofatturazione.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestHeadersInterceptor implements HandlerInterceptor {

	final RequestHeadersHolder headersHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
	{
		headersHolder.setIdToken(request.getHeader("Authorization"));
		headersHolder.setUser(request.getHeader("current-user"));
		headersHolder.setUri(request.getRequestURI());
		headersHolder.setUserAgent(request.getHeader("User-Agent"));
		
		return true;
	}
}
