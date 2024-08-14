package egovframework.login.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.PatternMatchUtils;

import egovframework.login.web.login.SessoinConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter{
	
	private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
			
			if (isLoginCheckPath(requestURI)) {
				HttpSession session = httpRequest.getSession(false);
				if (session == null || session.getAttribute(SessoinConst.LOGIN_MEMBER) == null) {
					// 로그인으로 redirect
					httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
					return;
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			throw e; //예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야함
		} finally {
			log.info("인증 체크 필터 종료 {}", requestURI);
		}
	}
	
	/**
	 * 화이트 리스트의 경우 인증 체크 X
	 */
	private boolean isLoginCheckPath(String requestURI) {
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
}
