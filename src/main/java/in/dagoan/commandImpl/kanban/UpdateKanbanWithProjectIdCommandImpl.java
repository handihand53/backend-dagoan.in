package in.dagoan.commandImpl.kanban;

import in.dagoan.command.kanban.PostKanbanWithProjectIdCommand;
import in.dagoan.command.kanban.UpdateKanbanWithProjectIdCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.kanban.UpdateKanbanWithProjectIdRequest;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.response.kanban.UpdateKanbanWithProjectIdResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;
import in.dagoan.repository.KanbanRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UpdateKanbanWithProjectIdCommandImpl implements UpdateKanbanWithProjectIdCommand {
    private KanbanRepository kanbanRepository;

    @Autowired
    public UpdateKanbanWithProjectIdCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }
    @Override
    public Mono<UpdateKanbanWithProjectIdResponse> execute(UpdateKanbanWithProjectIdRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> updateKanbanForm(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban updateKanbanForm(Kanban kanban, UpdateKanbanWithProjectIdRequest request) {
        List<KanbanForm> kanbanForms = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm ->
                kanbanForms.add(checkAndUpdateKanbanForm(kanbanForm, request)));
        kanban.setKanbanForms(kanbanForms);
        return kanban;
    }

    private KanbanForm checkAndUpdateKanbanForm(KanbanForm kanbanForm, UpdateKanbanWithProjectIdRequest request) {
        if (kanbanForm.getKanbanId().equals(request.getKanbanId())) {
            kanbanForm.setName(request.getKanbanName());
        }
        return kanbanForm;
    }

    private UpdateKanbanWithProjectIdResponse toResponse(Kanban kanban) {
        UpdateKanbanWithProjectIdResponse response = new UpdateKanbanWithProjectIdResponse();
        response.setKanban(kanban);
        return response;
    }
}
