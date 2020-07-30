package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name) {
        return userRepository.save(User.builder()
                                    .name(name)
                                    .email(email)
                                    .level(1).build());
    }

    public void updateUser(Integer id, String email, String name, Integer level) {
        User user = userRepository.findById(id).orElse(null);
        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);
        userRepository.save(user);
    }

    public User deactiveUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        user.deactive();
        userRepository.save(user);
        return user;
    }
}
