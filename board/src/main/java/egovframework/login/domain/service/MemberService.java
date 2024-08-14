package egovframework.login.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.login.domain.exception.DuplicateLoginIdException;
import egovframework.login.domain.member.Member;
import egovframework.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
    	validateDuplicateLoginId(member.getLoginId());
        memberRepository.save(member);
        return member;
    }
    
    private void validateDuplicateLoginId(String loginId) {
    	memberRepository.findByLoginId(loginId)
    					.ifPresent(m -> {
    						throw new DuplicateLoginIdException("이미 존재하는 아이디입니다: " + loginId);
    					});
    }

    public Optional<Member> findMember(Long memberId) {
        return Optional.ofNullable(memberRepository.findById(memberId));
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}