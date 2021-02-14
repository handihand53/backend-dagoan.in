package in.dagoan.commandImpl.kanban;

import in.dagoan.command.kanban.GetKanbanWithProjectIdCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.kanban.GetKanbanWithProjectIdRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;
import in.dagoan.repository.KanbanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetKanbanWithProjectIdCommandImpl implements GetKanbanWithProjectIdCommand {

    private KanbanRepository kanbanRepository;

    @Autowired
    public GetKanbanWithProjectIdCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<GetKanbanWithProjectIdResponse> execute(GetKanbanWithProjectIdRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(createKanban(request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Mono<Kanban> createKanban(GetKanbanWithProjectIdRequest request) {
        return Mono.fromCallable(() -> toKanban(request))
                .flatMap(project -> kanbanRepository.save(project));
    }

    private Kanban toKanban(GetKanbanWithProjectIdRequest request) {
        List<KanbanForm> kanbanForms = new ArrayList<>();
        List<TaskList> taskLists = Collections.emptyList();
        KanbanForm kanbanForm = new KanbanForm();
        kanbanForm.setKanbanId(UUID.randomUUID());
        kanbanForm.setName("To Do");
        kanbanForm.setSection(0);
        kanbanForm.setTaskLists(taskLists);
        kanbanForms.add(kanbanForm);
        Kanban kanban = Kanban.builder()
                .userId(request.getUserId())
                .projectId(request.getProjectId())
                .kanbanForms(kanbanForms)
                .build();
        BeanUtils.copyProperties(request, kanban);
        return kanban;
    }

    private GetKanbanWithProjectIdResponse toResponse(Kanban kanban) {
        GetKanbanWithProjectIdResponse response = new GetKanbanWithProjectIdResponse();
        response.setKanban(kanban);
        return response;
    }
}
