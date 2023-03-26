package LGCNS.RestfulWebService.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    // GET /users/1 or users/10 -> String
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int  id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        // ServletUriComponentsBuilder : ResponseEntity에 의해 서버 데이터 클라이언트에 전송
        // HTTP Status 제어
        // .fromCurrentRequest() : 현재 요청된 Uri 사용
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User updateUser){
        User updatedUser = service.updateById(id, updateUser);

        if(updatedUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    // GET : 조회, POST : 추가, PUT : 수정, DELETE : 삭제

}
