package egovframework.login;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import egovframework.login.domain.member.Member;
import egovframework.login.domain.service.MemberService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    
    public void init() {
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        
        memberService.save(member);
    }
}