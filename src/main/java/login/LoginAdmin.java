package login;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kic.mskim.MsInterceptor;

public class LoginAdmin implements MsInterceptor {

	@Override
	public String loginCheck(HttpServletRequest request, HttpServletResponse arg1) {

		System.out.println("admin");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String login = (String) request.getSession().getAttribute("id");
		request.setAttribute("msg", "접근 불가 합니다");
		request.setAttribute("url", "/member/loginForm");
		
		if (login == null || !login.equals("admin")) {
			return "alert";
		} else {
			return null;
		}

	}

}
