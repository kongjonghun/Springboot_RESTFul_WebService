package LGCNS.RestfulWebService.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public List<Users> retrieveAllUsers(){
        return service.findAll();
    }

    // GET /users/1 or users/10 -> String
    // HATROAS
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Users> retrieveUser(@PathVariable int  id){
        Users user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS : HyperMedia(링크) Response로 전달
        EntityModel<Users> model = EntityModel.of(user);
        WebMvcLinkBuilder builder = linkTo(methodOn(this.getClass()).retrieveAllUsers());  // 현재 Class의 retrieveAllUser() 메소드 추가
        model.add(builder.withRel("all-users")); // URI (HyperLink)

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users users){
        Users savedUser = service.save(users);

        // ServletUriComponentsBuilder : ResponseEntity에 의해 Response Header에 서버 데이터 클라이언트로 전송
        // HTTP Status 제어
        // .fromCurrentRequest() : 현재 요청된 Uri 사용
        // Response Header에 Location : http://localhost:8088/users/{id}으로 전달
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        Users user = service.deleteById(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody Users updateUsers){
        Users updatedUser = service.updateById(id, updateUsers);

        if(updatedUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    // GET : 조회, POST : 추가, PUT : 수정, DELETE : 삭제

}
