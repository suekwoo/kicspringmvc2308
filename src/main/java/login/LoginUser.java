package login;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginUser extends HandlerInterceptorAdapter {
    
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String login=(String)request.getSession().getAttribute("id");
		if(login==null) {
			response.sendRedirect(request.getContextPath()+"/member/alert?id=login");
			return false; 
		} else {
		return true; }
	}
	
	
	

}
