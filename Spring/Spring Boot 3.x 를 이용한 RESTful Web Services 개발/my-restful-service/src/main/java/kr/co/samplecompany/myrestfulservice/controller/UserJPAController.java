package kr.co.samplecompany.myrestfulservice.controller;

import kr.co.samplecompany.myrestfulservice.bean.User;
import kr.co.samplecompany.myrestfulservice.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    private final UserRepository userRepository;

    public UserJPAController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

}
