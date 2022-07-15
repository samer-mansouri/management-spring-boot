package backend.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.server.entity.Conges;

public interface ICongesRepo extends JpaRepository<Conges, Long> {

    List<Conges> findAllByUserId(Long userId);
    
}
