package in.dagoan.repository;

import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.ProjectForm;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface KanbanRepository extends ReactiveMongoRepository<Kanban, UUID> {
    Mono<Kanban> findFirstByProjectId(UUID projectId);
    Mono<Kanban> findFirstByUserIdAndProjectId(UUID userId, UUID projectId);
}
