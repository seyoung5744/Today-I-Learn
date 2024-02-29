package com.example.springjdbc.service;

import static com.example.springjdbc.connection.ConnectionConst.PASSWORD;
import static com.example.springjdbc.connection.ConnectionConst.URL;
import static com.example.springjdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.springjdbc.domain.Member;
import com.example.springjdbc.repository.MemberRepositoryV1;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
    }

    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("정상 이제")
    public void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    public void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        //when
        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
            .isInstanceOf(IllegalStateException.class);

        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx = memberRepository.findById(memberEx.getMemberId());

        //memberA의 돈만 2000원 줄었고, ex의 돈은 10000원 그대로이다.
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}