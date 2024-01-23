package hellojpa.jpa_practice;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 객체를 생성한 상태(비영속)
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("회원1");
//
//            // 객체를 영속성 컨덱스트에 저장한 상태(영속화)
//            System.out.println("==== BEFORE ====");
//            em.persist(member); // 1차 캐시에 영속화
//            System.out.println("==== AFTER ====");

            // 1차 캐시에서 조회
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // 동일성 (identity) 보장
//            Member findMember1 = em.find(Member.class, 1L);
//            Member findMember2 = em.find(Member.class, 1L);
//            System.out.println("result = " + (findMember1 == findMember2) );

            // 쓰기 지연
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println("=============================");

            // 엔티티 수정 - 변경감지(Dirty Checking)
//            Member findMember = em.find(Member.class, 150L);
//            System.out.println("수정 전 = " + findMember);
//            // 영속 엔티티 데이터 수정
//            findMember.setName("User110");
//
//            System.out.println("수정 후 = " + findMember);

            // 엔티티 삭제
//            Member findMember = em.find(Member.class, 150L);
//            em.remove(findMember);

            // 플러시
            Member member2 = new Member(2L, "member2");
            Member member3 = new Member(3L, "member3");
            Member member4 = new Member(4L, "member4");

            em.persist(member2);
            em.persist(member3);
            em.persist(member4);

            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            System.out.println(result);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
