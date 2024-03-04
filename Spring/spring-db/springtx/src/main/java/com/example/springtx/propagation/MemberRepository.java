package com.example.springtx.propagation;

import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;


    @Transactional
    public void save(Member member) {
        log.info("member 저장");
        em.persist(member);
    }

    @Transactional
    public Optional<Member> find(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", username)
            .getResultList().stream().findAny();
    }
}
