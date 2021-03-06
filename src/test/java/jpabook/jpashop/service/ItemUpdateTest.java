package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        // given
        Book book = em.find(Book.class, 1L);

        // when
        // 트랜잭션 안에서 변경이 생기고 커밋이 되면 JPA가 변경분에 대해 업데이트 쿼리를 생성, 반영함 (=flush 할때)
        // = 더티체킹 == 변경감지
        book.setName("Change Name");

        // then


    }
}
