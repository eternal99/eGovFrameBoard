package egovframework.login.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import egovframework.login.domain.member.Member;
import egovframework.login.domain.member.MemberRepository;
import egovframework.login.web.login.SessoinConst;
import egovframework.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;
	
	@GetMapping("/")
	public String homeLoginV3Spring(
			@SessionAttribute(name= SessoinConst.LOGIN_MEMBER, required = false)Member loginMember, Model model) {
		
		// 세션에 회원 데이터가 없으면 home
		if (loginMember == null) {
			return "home";
		}
		
		// 세션이 유지되면 로그인 이동
		model.addAttribute("member", loginMember);
		return "loginHome";
	}
	
}
