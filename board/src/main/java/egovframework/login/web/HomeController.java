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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import egovframework.example.sample.service.MainService;
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
	
//	@GetMapping("/")
	public String home() {
		return "home";
	}
	
//	@GetMapping("/")
	public String homeLogin(@CookieValue(name="memberId", required = false) Long memberId, Model model) {
		if (memberId == null) {
			return "home";
		}
		// 로그인
		Member loginMember = memberRepository.findById(memberId);
		if (loginMember == null) {
			return "home";
		}
		
		model.addAttribute("member", loginMember);
		return "loginHome";
	}
	
//	@GetMapping("/")
	public String homeLoginV2(HttpServletRequest request, Model model) {
		
		// 세션 관리자에 저장된 회원 정보 조회
		Member member = (Member)sessionManager.getSession(request);
		
		// 로그인
		if (member == null) {
			return "home";
		}
		
		model.addAttribute("member", member);
		return "loginHome";
	}
	
//	@GetMapping("/")
	public String homeLoginV3(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "home";
		}
		
		Member loginMember = (Member)session.getAttribute(SessoinConst.LOGIN_MEMBER);
		
		
		// 세션에 회원 데이터가 없으면 home
		if (loginMember == null) {
			return "home";
		}
		
		// 세션이 유지되면 로그인 이동
		model.addAttribute("member", loginMember);
		return "loginHome";
	}
	
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
	/**
	@Resource(name="MainService")
	MainService mainService;

	@RequestMapping(value= "/")
	public String main() {
		return "login/login";
	}
	
	@RequestMapping(value="/login.do")
	public String login(HttpServletRequest request, ModelMap model) {
		return "login/login";
	}
	
	@RequestMapping(value= "/loginSubmission.do")
	public String loginSubmission(HttpServletRequest request, ModelMap model) {
		HashMap<String, Object> resultMap = new HashMap<>();
		try {
			resultMap = mainService.selectLogin(request);
			request.getSession().setAttribute("myid", resultMap.get("userid").toString());
			model.addAttribute("serverId", resultMap.get("userid").toString());
		} catch (Exception e) {
			//로그기록, 상태코드 반환 또는 에러페이지 전달
			String error = e.getMessage();
			log.info("에러메시지:" + error);
			if (error.equals("에러가 발생했습니다!")) {
				return "redirect:/login.do";
			}
			return "error/error";
		}
		return "main/home";
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(HttpServletRequest request, ModelMap model) {
		System.out.println(request.getSession().getAttribute("myid").toString());
		request.getSession().invalidate();
		System.out.println("," + request.getSession().getAttribute("myid").toString());
		return "login/logout";
	} **/
}
