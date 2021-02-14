package in.dagoan.commandImpl.invited;

import in.dagoan.command.invited.PostInvitedCommand;
import in.dagoan.entity.document.Invited;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.model.request.invited.PostInvitedRequest;
import in.dagoan.model.request.kanban.PostKanbanWithProjectIdRequest;
import in.dagoan.model.response.invited.InvitedResponse;
import in.dagoan.repository.InvitedRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PostInvitedCommandImpl implements PostInvitedCommand {
    private InvitedRepository invitedRepository;

    @Autowired
    public PostInvitedCommandImpl(InvitedRepository invitedRepository) {
        this.invitedRepository = invitedRepository;
    }

    @Override
    public Mono<InvitedResponse> execute(PostInvitedRequest request) {
        return invitedRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Data not found!")))
                .map(invited -> addNewUser(invited, request))
                .flatMap(invited -> invitedRepository.save(invited))
                .map(this::toResponse);
    }

    private Invited addNewUser(Invited invited, PostInvitedRequest request) {
        invited.getUserId().add(request.getUserId());
        return invited;
    }

    private InvitedResponse toResponse(Invited invited) {
        InvitedResponse response = new InvitedResponse();
        response.setInvited(invited);
        return response;
    }
}
