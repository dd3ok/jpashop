package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA의 데이터 변경, 로직들은 트랜잭션 안에서 실행 되어야 함, LAZY 로딩 등등
// @AllArgsConstructor
// readOnly 일 경우 성능 최적화함, 더티 체킹 x, 리소스 down
@RequiredArgsConstructor // final이 있는 필드만 생성자 생성
public class MemberService {

    // 변경 할 일 없기 때문에 final로 셋팅 - 컴파일 시점에 문제 있을 때 체크 할 수 있음
    private final MemberRepository memberRepository;

    // 테스트 케이스 작성할때
    // @Autowired
    /* public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } */

    /**
     * 회원 가입
     */
    @Transactional // readOnly 아닌 경우 따로 써주면 됨 (기본 값 readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
