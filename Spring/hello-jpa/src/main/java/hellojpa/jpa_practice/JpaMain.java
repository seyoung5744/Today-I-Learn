package hellojpa.jpa_practice;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 저장
//            for (int i = 0; i < 100; i++) {
//                Member member = new Member();
//                member.setId((long) i);
//                member.setName("Hello" + i);
//                em.persist(member);
//            }

//            em.persist(member);

            // 조회 및 수정
//            Member findMember = em.find(Member.class, 2L);
//            findMember.setName("HelloJPA");

            // JPQL
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            System.out.println(result);

            // JPQL Paging
            List<Member> pagingResult = em.createQuery("select m from Member m", Member.class)
                .setFirstResult(5)
                .setMaxResults(8)
                .getResultList();
            System.out.println(pagingResult);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
