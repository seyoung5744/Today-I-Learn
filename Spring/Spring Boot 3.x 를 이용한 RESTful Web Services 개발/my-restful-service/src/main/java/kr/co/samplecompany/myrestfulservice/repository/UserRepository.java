package kr.co.samplecompany.myrestfulservice.repository;

import kr.co.samplecompany.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
