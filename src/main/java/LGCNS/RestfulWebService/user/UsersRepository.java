package LGCNS.RestfulWebService.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    // JpaRepository<Entity, Id 자료형>
}
