package backend.server.repository;

import backend.server.entity.Role;
import backend.server.entity.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepo extends JpaRepository<User, Long> {


    @Transactional
    User findByEmail(String email);
    long count();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = ?1")
    long countByRole(String role);

}