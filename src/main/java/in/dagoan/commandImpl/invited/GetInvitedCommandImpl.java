package in.dagoan.commandImpl.invited;

import in.dagoan.command.invited.GetInvitedCommand;
import in.dagoan.entity.document.Invited;
import in.dagoan.model.request.invited.GetInvitedRequest;
import in.dagoan.model.request.invited.PostInvitedRequest;
import in.dagoan.model.response.invited.InvitedResponse;
import in.dagoan.repository.InvitedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetInvitedCommandImpl implements GetInvitedCommand {
    private InvitedRepository invitedRepository;

    @Autowired
    public GetInvitedCommandImpl(InvitedRepository invitedRepository) {
        this.invitedRepository = invitedRepository;
    }

    @Override
    public Mono<InvitedResponse> execute(GetInvitedRequest request) {
        return invitedRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(createInvited(request))
                .flatMap(invited -> invitedRepository.save(invited))
                .map(this::toResponse);
    }

    private Mono<Invited> createInvited(GetInvitedRequest request) {
        return Mono.fromCallable(() -> toInvited(request))
                .flatMap(invited -> invitedRepository.save(invited));
    }

    private Invited toInvited(GetInvitedRequest request) {
        Invited invited = new Invited();
        List<UUID> invitedId = new ArrayList<>();
        invited.setInvitedId(UUID.randomUUID());
        invited.setProjectId(request.getProjectId());
        invited.setUserId(invitedId);
        return invited;
    }

    private InvitedResponse toResponse(Invited invited) {
        InvitedResponse response = new InvitedResponse();
        response.setInvited(invited);
        return response;
    }
}
