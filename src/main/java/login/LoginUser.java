package login;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kic.mskim.MsInterceptor;

public class LoginUser implements  MsInterceptor{
    //  login ok이면 null을 보내고 login no이면 "/board/loginForm url을 보낸다 
	@Override
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("user");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String login=(String)request.getSession().getAttribute("id");
		request.setAttribute("msg", "login하여야 합니다");
		request.setAttribute("url", "/member/loginForm");
		if(login!=null) {
			return null; 
		} else {
		return "alert"; }
	}

}
