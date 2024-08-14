package egovframework.login.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sound.midi.Sequence;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Mapper
public interface MemberRepository {
    int save(Member member);  // 변경: Member를 반환하는 대신 int (영향받은 행 수)를 반환
    Member findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    List<Member> findAll();

}
