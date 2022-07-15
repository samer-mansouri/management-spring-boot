package backend.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.server.entity.Personne;

public interface IPersonneRepo extends JpaRepository<Personne, Long> {

    List<Personne> findAllByUserId(Long userId);
    
}
