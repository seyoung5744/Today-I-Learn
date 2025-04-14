package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 객체를 생성한 상태(비영속)
//            Member member = new Member();
//            member.setId(10L);
//            member.setName("HelloJPA");
//
//            // 영속
//            System.out.println("=== BEFORE ===");
//            em.persist(member); // 1차 캐시에 영속화
//            System.out.println("=== AFTER ===");

            // 1차 캐시에서 조회
//            Member findMember = em.find(Member.class, 10L);
//            Member findMember2 = em.find(Member.class, 10L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//            System.out.println("findMember2.getName() = " + findMember2.getName());
//            System.out.println("result = " + (findMember == findMember2)); // true

//            Member member1 = new Member(150L, "회원1");
//            Member member2 = new Member(160L, "회원2");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("==============");

//            Member findMember = em.find(Member.class, 150L);
//            findMember.setName("member1");

//            Member member = em.find(Member.class, 150L);
//            em.remove(member);

//            Member memberA = new Member(11L, "memberA");
//            Member memberB = new Member(22L, "memberB");
//            Member memberC = new Member(33L, "memberC");
//
//            em.persist(memberA);
//            em.persist(memberB);
//            em.persist(memberC);
//
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            List<Member> members = query.getResultList();
//            System.out.println("members = " + members);

            Member member = em.find(Member.class, 11L);
            member.setName("AAA"); // Dirty Checking

            em.detach(member);

            em.clear();
            System.out.println("=============");
            Member member2 = em.find(Member.class, 11L);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
