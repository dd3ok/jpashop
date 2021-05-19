package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
// 스프링 통합 테스트 - 위 2가지는 필수
@Transactional // Test에 있으면 테스트 끝나고 롤백을함, 반복적인 테스트를 하기 위함, 필요시 해당 메소드에 @Rollback(false) 사용
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    // JPA의 같은 트랜잭션 내에서는 같은 엔티티=ID 값이 똑같으면 같은 영속성 컨텍스트 내에서 같은 엔티티임
    @Test
//    @Rollback(false) // 실제 데이터 들어가는거 보려면 rollback false
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("injae");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트에 있는걸 db에 날리고 rollback, 로그 확인 가능
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("injae");

        Member member2 = new Member();
        member2.setName("injae");

        em.flush();
        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 함

        // then
        fail("예외 발생해야 함.");
    }

}