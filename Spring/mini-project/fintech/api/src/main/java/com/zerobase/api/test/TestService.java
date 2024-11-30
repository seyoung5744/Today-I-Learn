package com.zerobase.api.test;

import com.zerobase.api.test.TestDto.UserInfoDto;
import com.zerobase.domain.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoDto testGetService(String userKey) {
        return TestDto.UserInfoDto.of(
            userInfoRepository.findByUserKey(userKey)
        );
    }
}
