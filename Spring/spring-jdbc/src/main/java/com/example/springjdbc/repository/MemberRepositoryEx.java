package com.example.springjdbc.repository;

import com.example.springjdbc.domain.Member;
import java.sql.SQLException;

public interface MemberRepositoryEx {

    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;
}
