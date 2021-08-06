//package io.github.shuoros.allAboutSpring.controller;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
//
//	@RequestMapping(value = "/error")
//	public String handleError(HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//		if (status != null) {
//			Integer statusCode = Integer.valueOf(status.toString());
//
//			if (statusCode == HttpStatus.NOT_FOUND.value()) {
//				return "page-404";
//			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//				return "page-403";
//			} else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
//				return "page-503";
//			}
//		}
//		return "error";
//	}
//	
//}