package LGCNS.RestfulWebService.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // JpaRepository<Entity, Id 자료형>
}
