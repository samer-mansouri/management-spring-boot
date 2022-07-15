package backend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.server.entity.Details;

@Repository
public interface IDetailsRepo extends JpaRepository<Details, Long> {
    
    //A query to select details by user id
    //@Query("SELECT d FROM Details d WHERE d.user.id = ?1")
    public Details findByUserId(Long id);

    //Details findByUserId(Long userId);
    
}
