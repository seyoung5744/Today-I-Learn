package com.example.springjdbc.repository;

import static com.example.springjdbc.connection.ConnectionConst.PASSWORD;
import static com.example.springjdbc.connection.ConnectionConst.URL;
import static com.example.springjdbc.connection.ConnectionConst.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.springjdbc.connection.ConnectionConst;
import com.example.springjdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManger - 항상 새로운 커넥션 획득
//         DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 커넥션 풀링 : HikariProxyConnection -> JdbcConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    public void crud() throws SQLException {
        // save
        Member member = new Member("memberV0", 10_000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update : money 10_000 -> 20_000
        repository.update(member.getMemberId(), 20_000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20_000);

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
            .isInstanceOf(NoSuchElementException.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}