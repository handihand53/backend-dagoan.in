package in.dagoan.repository;

import in.dagoan.entity.document.Label;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface LabelRepository extends ReactiveMongoRepository<Label, UUID> {
    Mono<Label> findFirstByProjectIdAndUserId(UUID projectId, UUID userId);
    Mono<Label> findFirstByProjectId(UUID projectId);
}
