package egovframework.login.web.session;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import egovframework.login.domain.member.Member;

import static org.assertj.core.api.Assertions.assertThat;


class SessionManagerTest {
	
	SessionManager sessionManager = new SessionManager();
	private ObjectAssert<Object> equalTo;
	
	@Test
	void sessionTest() {
		
		
		// 세션 생성
		MockHttpServletResponse response = new MockHttpServletResponse();
		Member member = new Member();
		sessionManager.createSession(member, response);
		
		//요청에 응답 쿠키 저장
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies());
		
		//세션 조회
		Object result = sessionManager.getSession(request);
		assertThat(result).isEqualTo(member);
		
		//세션 만료
		sessionManager.exprie(request);
		Object expired = sessionManager.getSession(request);
		assertThat(expired);
	}

}
