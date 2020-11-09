package in.dagoan.repository;

import in.dagoan.entity.form.ProjectForm;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProjectFormRepository extends ReactiveMongoRepository<ProjectForm, UUID> {
    Mono<ProjectForm> findFirstByProjectId(UUID projectId);
}
