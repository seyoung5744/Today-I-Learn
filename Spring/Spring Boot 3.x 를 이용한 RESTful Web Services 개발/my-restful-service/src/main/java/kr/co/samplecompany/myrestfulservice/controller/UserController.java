package kr.co.samplecompany.myrestfulservice.controller;

import kr.co.samplecompany.myrestfulservice.bean.User;
import kr.co.samplecompany.myrestfulservice.dao.UserDaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return service.save(user);
    }
}
