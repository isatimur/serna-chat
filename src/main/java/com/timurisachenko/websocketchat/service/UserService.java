//package com.timurisachenko.websocketchat.service;
//
//import com.timurisachenko.websocketchat.model.Role;
//import com.timurisachenko.websocketchat.model.User;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import javax.annotation.PostConstruct;
//
//@Service
//public class UserService {
//
//    private Map<String, User> data;
//
//    @PostConstruct
//    public void init() {
//        data = new HashMap<>();
//
//        //username:passwowrd -> user:user
//        data.put("user", new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER)));
//
//        //username:passwowrd -> guest:user
//        data.put("user", new User("guest", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_GUEST)));
//
//        //username:passwowrd -> admin:admin
//        data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN)));
//    }
//
//    public Mono<User> findByUsername(String username) {
//        return Mono.justOrEmpty(data.get(username));
//    }
//}
