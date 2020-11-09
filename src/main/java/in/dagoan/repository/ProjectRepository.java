package in.dagoan.repository;

import in.dagoan.entity.document.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, UUID> {
    Mono<Project> findFirstByProjectId(UUID projectId);
    Mono<Project> findFirstByUserId(UUID id);
}
