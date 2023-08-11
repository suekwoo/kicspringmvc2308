package controller;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import model.Member;
import service.MemberMybatis;


@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	MemberMybatis  md;
	
	Model m;
	HttpSession session;
	HttpServletRequest request;
	
	//초기화 작업을 한다, 객체 초기화시에 사용한다 
	@ModelAttribute
	void init(HttpServletRequest request, Model m) {
		this.request=request;
		this.m=m;
		session = request.getSession();
		
	}
	
	    @RequestMapping("index")  // /member/index
		public String  index(HttpServletRequest request, 
				HttpServletResponse response) {
			request.setAttribute("index", "member 입니다");
			// /view/member/index.jsp
			return "index";
		}
	    
	    @RequestMapping("joinForm") //  /member/joinForm
		public String  joinForm(HttpServletRequest request, 
				HttpServletResponse response) {
			
			return "member/joinForm";
		}
	    @RequestMapping("loginForm") //  /member/joinForm
		public String  loginForm(HttpServletRequest request, 
				HttpServletResponse response) {
			
			return "member/loginForm";
		}
	    
	    @RequestMapping("joinPro") //  /member/joinForm
		public String  joinPro(HttpServletRequest request, 
				HttpServletResponse response) {
	    	try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	Member mem = new Member();
	    	mem.setId(request.getParameter("id"));
	    	mem.setPass(request.getParameter("pass"));
	    	mem.setName(request.getParameter("name"));
	    	mem.setGender(Integer.parseInt(request.getParameter("gender")));
	    	mem.setTel(request.getParameter("tel"));
	    	mem.setEmail(request.getParameter("email"));
	    	
	    	mem.setPicture(request.getParameter("picture"));

	    	
	    	int num = md.insertMember(mem);
	    	String msg = "";
	    	String url = "";
	    	if (num > 0) {
	    		//insert ok
	    		msg = mem.getName() + "님이 가입을 하였습니다";
	    		url = "/member/loginForm";
	    	} else {
	    		// insert error
	    		msg = "회원가입이 실패 하였습니다";
	    		url = "/member/joinForm";
	    	}
	    	request.setAttribute("msg", msg);
	    	request.setAttribute("url", url);
			return "alert";
		}
		
	    
	    @RequestMapping("loginPro") //  /member/joinForm
		public String  loginPro(HttpServletRequest request, 
				HttpServletResponse response) {
	    	
	    	String id = request.getParameter("id");
	    	String pass = request.getParameter("pass");

	    	
	    	Member mem = md.oneMember(id); 
	    	String msg = "";
	    	String url = "";
	    	if (mem ==null) {   //id 없음
	    		msg="아이디를 확인 하세요";
	    	    url="/member/loginForm";
	    	} else  {	
	    		if (pass.equals(mem.getPass())) {  //login ok
	    			request.getSession().setAttribute("id", id);
	    		    msg=mem.getName()+"님이 로그인 하셨습니다";
	    			url="/member/index";
	    		} else {
	    			msg = "비밀번호를 확인 하세요";
	    			url="/member/loginForm";
	    		}}
	    	request.setAttribute("msg", msg);
	    	request.setAttribute("url", url);
			return "alert";
		}
	    
	    
	    
	    @RequestMapping("logout") //  /member/joinForm
		public String  logout(HttpServletRequest request, 
				HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	String login = (String)session.getAttribute("id");
	    	session.invalidate();
	    	String msg = login + "님이 로그아웃 되었습니다";
	    	String url = "member/loginForm";
	    	request.setAttribute("msg", msg);
	    	request.setAttribute("url", url);
	    	return "alert";
		}
	    
	  
	    @RequestMapping("memberInfo") //  /member/joinForm
	 
		public String  memberInfo(HttpServletRequest request, 
				HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	String id = (String)session.getAttribute("id");
	    	Member m = md.oneMember(id);
	    	
	    	request.setAttribute("m", m);
			return "member/memberInfo";
		}
	    
	    
	    
	    
	    
	    @RequestMapping("memberUpdateForm") 
	   
		public String  memberUpdateForm(HttpServletRequest request, 
				HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	String id = (String)session.getAttribute("id");
	    	Member m = md.oneMember(id);
	    	
	    	request.setAttribute("m", m);
			return "member/memberUpdateForm";
		}
	    
	    @RequestMapping("memberUpdatePro")
		public String  memberUpdatePro(HttpServletRequest request, 
				HttpServletResponse response) {
	    	try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			}
	    	HttpSession session = request.getSession();
	    	String id = (String) session.getAttribute("id");
	    	String msg="로그인이 필요합니다";    	String url="member/loginForm";
	    		Member newm = new Member();
	    		newm.setId(id);
	    		newm.setPass(request.getParameter("pass"));
	    		newm.setName(request.getParameter("name"));
	    		newm.setGender(Integer.parseInt(request.getParameter("gender")));
	    		newm.setTel(request.getParameter("tel"));
	    		newm.setEmail(request.getParameter("email"));
	    		newm.setPicture(request.getParameter("picture"));
	    	
	    		Member  dbm = md.oneMember(id);  //password 확인
	    		
	    		
	    		
	    		if (dbm!=null) {
	    			if (dbm.getPass().equals(newm.getPass())) {
	    				int num = md.updateMember(newm);
	    				
	    				if (num>0) {
	    					msg=newm.getName() +"님의 정보 수정이 되었습니다";
	    					url="member/memberInfo";
	    				} else {
	    					msg="정보수정이 실패 하였습니다";
	    					url="member/memberUpdateForm";
	    				}
	    				
	    				
	    				
	    			} else {
	    				msg="비밀번호가 틀렸습니다";
	    				url="member/memberUpdateForm";
	    			}
	    			
	    			
	    			
	    		}  //password 확인
	    	
	    		
	    	
	    		request.setAttribute("msg", msg);
		    	request.setAttribute("url", url);
		    	return "alert";
		}
	    
	    @RequestMapping("memberPassForm") 
	 
		public String  memberPassForm(HttpServletRequest request, 
				HttpServletResponse response) { 	 	
	    	
			return "member/memberPassForm";
		}
	    
	    
	    @RequestMapping("memberPassPro")
	  
		public String  memberPassPro(HttpServletRequest request, 
				HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	String id = (String) session.getAttribute("id");
	    	String msg="로그인이 필요합니다";    	String url="loginForm.jsp";
	    	
	    		Member  dbm = md.oneMember(id);  //password 확인
	    		String pass  = request.getParameter("pass"); 
	    		String chgpass1  = request.getParameter("chgpass1"); 
	    		if (dbm!=null) {
	    			if (dbm.getPass().equals(pass)) {
	    				int num = md.changePass(id, chgpass1); 
	    				if (num>0) { 
	    					msg=dbm.getName() +"님의 비밀번호가 수정 되었습니다";
	    					url="member/index";
	    				} else {
	    					msg="비밀번호 수정을 실패 하였습니다";
	    					url="member/memberPassForm";	}					
	    			} else {
	    				msg="비밀번호가 틀렸습니다";
	    				url="member/memberPassForm";} 		}  //password 확인
	    	request.setAttribute("msg", msg);
	    	request.setAttribute("url", url);
	    	return "alert";		}   
	    
	    
	    
	    @RequestMapping("memberDeleteForm") 
	 
		public String  memberDeleteForm(HttpServletRequest request, 
				HttpServletResponse response) {
	    	 	
	    	
			return "member/memberDeleteForm";
		}
	    
	    @RequestMapping("memberDeletePro")
	   
		public String  memberDeletePro(HttpServletRequest request, 
				HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	String id = (String) session.getAttribute("id");
	    	String msg="로그인이 필요합니다";   	String url="member/loginForm";
	    
	    		Member  dbm = md.oneMember(id);  //password 확인
	    		String pass  = request.getParameter("pass"); 
	    		if (dbm!=null) {
	    			if (dbm.getPass().equals(pass)) {
	    				int num = md.deleteMember(id); 
	    				if (num>0) {
	    					msg=dbm.getName() +"님의 탈퇴 처리 되었습니다";
	    					session.invalidate();
	    					url="member/loginForm";
	    				} else {
	    					msg="회원탈퇴가 실패 하였습니다";
	    					url="member/memberDeleteForm";
	    				}	} else {
	    				msg="비밀번호가 틀렸습니다";
	    				url="member/memberDeleteForm";	}}  //password 확인
	    	request.setAttribute("msg", msg);
	    	request.setAttribute("url", url);  	
	    	return "alert";	
		} 
	    
	    @RequestMapping("pictureimgForm")
	    public String  pictureimgForm(HttpServletRequest request, 
				HttpServletResponse response) {
	    	 	
	    	
			return "member/pictureimgForm";
		}
	    
    
	    @RequestMapping("picturePro") 
	    public String  picturePro(@RequestParam("picture") MultipartFile  multipartFile ) {
	    	String path = 
	    			request.getServletContext().getRealPath("/")
	    			+"WEB-INF/view/member/picture/";
	    	String filename = null;
	    	if (!multipartFile.isEmpty()) {
				File file = new File(path, multipartFile.getOriginalFilename());
				filename = multipartFile.getOriginalFilename();
				
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}	
		    request.setAttribute("filename", filename);
	    	
			return "member/picturePro";
		}
	    
   
	    
	    @RequestMapping("memberList") 
	   
	    public String  memberList(HttpServletRequest request, 
				HttpServletResponse response) {
	    	 String id = (String) request.getSession().getAttribute("id"); 	
	    	 List<Member>  li = md.memberList();  	
	    	 request.setAttribute("li", li);
			return "member/memberList";
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}  //end class
