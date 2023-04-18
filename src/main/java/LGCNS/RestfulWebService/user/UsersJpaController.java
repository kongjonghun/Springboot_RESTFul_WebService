package LGCNS.RestfulWebService.user;

import LGCNS.RestfulWebService.user.Users;
import LGCNS.RestfulWebService.user.UsersRepository;
import com.ctc.wstx.shaded.msv_core.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.plaf.SpinnerUI;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class UsersJpaController{
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostRepository postRepository;

    // https://localhost:8088/jpa/users
    @GetMapping("/users")
    public List<Users> retrieveAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<Users> retrieveUser(@PathVariable int id){
        Optional<Users> user = usersRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID [%s] not found", id));
        }

        // HATEOAS : Users 전체 목록 URL 형태로 Response에 전달
        EntityModel<Users> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder builder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(builder.withRel("all-users"));

        return entityModel;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        usersRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user){
        Users savedUser = usersRepository.save(user);

        // ServletUriComponentsBuilder : ResponseEntity에 의해 서버 데이터 클라이언트에 전송
        // HTTP Status 제어
        // .fromCurrentRequest() : 현재 요청된 Uri 사용
        // Response Header에 Location : http://localhost:8088/jpa/users/{id}으로 전달
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional<Users> user = usersRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){
        Optional<Users> user = usersRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPost.getId())     // Post의 ID를 path("{id}") 바인딩해서 Response
                        .toUri();

        return ResponseEntity.created(location).build();
    }

}
