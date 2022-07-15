package backend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.server.entity.Evaluation;

public interface IEvaluationRepo extends JpaRepository<Evaluation, Long> {
    
    
}
