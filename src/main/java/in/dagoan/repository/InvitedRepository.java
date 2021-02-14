package in.dagoan.repository;

import in.dagoan.entity.document.Invited;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface InvitedRepository extends ReactiveMongoRepository<Invited, UUID> {
    Mono<Invited> findFirstByProjectId(UUID projectId);
}
