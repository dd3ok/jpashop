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
@Transactional // 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    // JPA의 같은 트랜잭션 내에서는 같은 엔티티=ID 값이 똑같으면 같은 영속성 컨텍스트 내에서 같은 엔티티임
    @Test
    public void 회원가입() throws Exception {
        // given - 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        // when - 이렇게 하면
        Long savedId = memberService.join(member);

        // then - 이렇게 된다
        em.flush(); // 영속성 컨텍스트에 있는 내용을 DB에 반영
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 함

        // then
        fail("예외 발생해야 함.");
    }

}