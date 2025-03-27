package kr.co.samplecompany.myrestfulservice.controller;

import jakarta.validation.Valid;
import kr.co.samplecompany.myrestfulservice.bean.User;
import kr.co.samplecompany.myrestfulservice.exception.UserNotFoundException;
import kr.co.samplecompany.myrestfulservice.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

    private final UserRepository userRepository;

    public UserJPAController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public UserDto retrieveAllUsers() {
        List<User> users = userRepository.findAll();
        return new UserDto(users.size(), users);
    }

    @Getter
    static class UserDto {
        private final int count;
        private final List<User> users;

        public UserDto(int count, List<User> users) {
            this.count = count;
            this.users = users;
        }
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("ID[%s] not found", id)));

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
