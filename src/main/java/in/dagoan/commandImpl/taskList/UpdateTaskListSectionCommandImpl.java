package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.UpdateTaskListSectionCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.model.request.taskList.UpdateTaskListSectionRequest;
import in.dagoan.model.response.taskList.UpdateTaskListSectionResponse;
import in.dagoan.repository.KanbanRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UpdateTaskListSectionCommandImpl implements UpdateTaskListSectionCommand {
    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();

    @Override
    public Mono<UpdateTaskListSectionResponse> execute(UpdateTaskListSectionRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
            .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
            .map(kanban -> updateTaskList(kanban, request))
            .flatMap(kanban -> kanbanRepository.save(kanban))
            .map(this::toResponse);
    }

    @Autowired
    public UpdateTaskListSectionCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    private UpdateTaskListSectionResponse toResponse(Kanban kanban) {
        UpdateTaskListSectionResponse response = new UpdateTaskListSectionResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private Kanban updateTaskList(Kanban kanban, UpdateTaskListSectionRequest request) {
        List<KanbanForm> kanbanFormList = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm -> {
            if (kanbanForm.getKanbanId().equals(request.getKanbanId())){
                kanbanForms = kanbanForm;
            } else {
                kanbanFormList.add(kanbanForm);
            }
        });

        kanbanForms.setTaskLists(request.getTaskLists());
        kanbanFormList.add(kanbanForms);
        kanban.setKanbanForms(kanbanFormList);

        return kanban;
    }
}
