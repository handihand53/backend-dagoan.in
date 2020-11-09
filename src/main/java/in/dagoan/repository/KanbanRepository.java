package in.dagoan.repository;

import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KanbanRepository extends ReactiveMongoRepository<Kanban, UUID> {
}
