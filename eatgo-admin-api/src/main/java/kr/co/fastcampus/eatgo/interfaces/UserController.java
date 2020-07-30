package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User resource) throws URISyntaxException {
        userService.addUser(resource.getEmail(), resource.getName());

        String uri = "/users/" + resource.getId();
        return ResponseEntity.created(new URI(uri)).body("{}");
    }

    @PatchMapping("/users/{id}")
    public String update(
            @PathVariable("id") Integer id,
            @RequestBody User resource){
        String email = resource.getEmail();
        String name = resource.getName();
        Integer level = resource.getLevel();

        userService.updateUser(id, email, name, level);
        return "{}";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") Integer id){
        userService.deactiveUser(id);

        return "{}";
    }
}
