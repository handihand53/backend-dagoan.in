package in.dagoan.commandImpl.kanban;

import in.dagoan.command.kanban.PostKanbanWithProjectIdCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.enums.ProjectStatus;
import in.dagoan.model.request.kanban.PostKanbanWithProjectIdRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;
import in.dagoan.model.response.kanban.PostKanbanWithProjectIdResponse;
import in.dagoan.repository.KanbanRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    @Slf4j
    @Service
public class PostKanbanWithProjectIdCommandImpl implements PostKanbanWithProjectIdCommand {

    private KanbanRepository kanbanRepository;

    @Autowired
    public PostKanbanWithProjectIdCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<PostKanbanWithProjectIdResponse> execute(PostKanbanWithProjectIdRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> addNewKanban(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban addNewKanban(Kanban kanban, PostKanbanWithProjectIdRequest request) {
        List<KanbanForm> kanbanForms = kanban.getKanbanForms();
        KanbanForm kanbanForm = KanbanForm.builder()
                .kanbanId(UUID.randomUUID())
                .name(request.getKanbanName())
                .taskLists(new ArrayList<>())
                .build();
        kanbanForms.add(kanbanForm);
        kanban.setKanbanForms(kanbanForms);
        return kanban;
    }

    private PostKanbanWithProjectIdResponse toResponse(Kanban kanban) {
        PostKanbanWithProjectIdResponse response = new PostKanbanWithProjectIdResponse();
        response.setKanban(kanban);
        return response;
    }
}
