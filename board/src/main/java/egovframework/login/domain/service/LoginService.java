package egovframework.login.domain.service;

import org.springframework.stereotype.Service;

import egovframework.login.domain.member.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberService memberService;

    public Member login(String loginId, String password) {
        return memberService.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}