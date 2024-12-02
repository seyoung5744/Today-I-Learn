package com.zerobase.domain.repository;

import com.zerobase.domain.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUserKey(String userKey);
}
