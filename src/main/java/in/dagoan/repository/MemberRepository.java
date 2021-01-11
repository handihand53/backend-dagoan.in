package in.dagoan.repository;

import in.dagoan.entity.document.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MemberRepository extends ReactiveMongoRepository<Member, UUID> {
    Mono<Member> findFirstByUserId(UUID userId);
}
