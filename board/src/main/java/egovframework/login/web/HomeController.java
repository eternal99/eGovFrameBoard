package egovframework.login.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.example.sample.service.MainService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home";
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
