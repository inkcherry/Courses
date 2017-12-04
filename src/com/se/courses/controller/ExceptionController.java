package com.se.courses.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.se.courses.exception.CourseException;


/**
 * 用于Controller全局控制
 * @author BO
 *
 */
@ControllerAdvice
public class ExceptionController {

	/**
	 * 异常页面重定向，携带参数，可以刷新
	 * 全局SEWMException异常显示，任何可能抛出该异常的页面仅需定义exception即可接收异常信息
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CourseException.class)  
	public String  handleSEWMException(Exception e, HttpServletRequest request, RedirectAttributes rMap) {
		rMap.addFlashAttribute("exception", e.getMessage());
	  return "redirect:" + request.getHeader("Referer");
	}  
}
