package in.dagoan.commandImpl.invited;

import in.dagoan.command.invited.DeleteInvitedCommand;
import in.dagoan.command.invited.PostInvitedCommand;
import in.dagoan.entity.document.Invited;
import in.dagoan.model.request.invited.DeleteInvitedRequest;
import in.dagoan.model.request.invited.PostInvitedRequest;
import in.dagoan.model.response.invited.InvitedResponse;
import in.dagoan.repository.InvitedRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DeleteInvitedCommandImpl implements DeleteInvitedCommand {
    private InvitedRepository invitedRepository;

    @Autowired
    public DeleteInvitedCommandImpl(InvitedRepository invitedRepository) {
        this.invitedRepository = invitedRepository;
    }

    @Override
    public Mono<InvitedResponse> execute(DeleteInvitedRequest request) {
        return invitedRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Data not found!")))
                .map(invited -> deleteUser(invited, request))
                .flatMap(invited -> invitedRepository.save(invited))
                .map(this::toResponse);
    }

    private Invited deleteUser(Invited invited, DeleteInvitedRequest request) {
        List<UUID> listUserId = new ArrayList<>();
        invited.getUserId().forEach(uuid -> {
                if (!uuid.equals(request.getUserId())) {
                    listUserId.add(uuid);
                }
            }
        );
        invited.setUserId(listUserId);
        return invited;
    }

    private InvitedResponse toResponse(Invited invited) {
        InvitedResponse response = new InvitedResponse();
        response.setInvited(invited);
        return response;
    }
}
