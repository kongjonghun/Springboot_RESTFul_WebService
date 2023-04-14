package LGCNS.RestfulWebService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

//Static Method : linkTo
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    // GET /users/1 or users/10 -> String
    // HATROAS
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int  id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS : HyperMedia(링크) Response로 전달
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());  // 현재 Class의 retrieveAllUser() 메소드 추가
        model.add(linkTo.withRel("all-users")); // URI (HyperLink)

        return model;
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
