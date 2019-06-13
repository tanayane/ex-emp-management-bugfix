package jp.co.sample.emp_management.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * システムエラー発生時にログを出力し、エラーページへ遷移する. 
 * 
 * @author ayane.tanaka
 *
 */
@Component
public class GlobalExceptionErrorHandler implements HandlerExceptionResolver{
	
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionErrorHandler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("システムエラー発生",ex);
		return new ModelAndView("redirect:/common/maintenance");
	}

}
