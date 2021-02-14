package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.UpdateAssignToCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.AssignTo;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.taskList.UpdateAssignToRequest;
import in.dagoan.model.request.taskList.UpdateTaskListRequest;
import in.dagoan.model.response.taskList.UpdateAssignToResponse;
import in.dagoan.model.response.taskList.UpdateTaskListResponse;
import in.dagoan.repository.KanbanRepository;
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
public class UpdateAssignToCommandImpl implements UpdateAssignToCommand {
    private KanbanRepository kanbanRepository;

    KanbanForm kanbanForms = new KanbanForm();
    @Autowired
    public UpdateAssignToCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<UpdateAssignToResponse> execute(UpdateAssignToRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> updateTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private UpdateAssignToResponse toResponse(Kanban kanban) {
        UpdateAssignToResponse response = new UpdateAssignToResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private Kanban updateTaskList(Kanban kanban, UpdateAssignToRequest request) {
        List<TaskList> taskLists = new ArrayList<>();
        List<KanbanForm> kanbanFormList = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm -> {
            if (kanbanForm.getKanbanId().equals(request.getKanbanId())){
                kanbanForms = kanbanForm;
            } else {
                kanbanFormList.add(kanbanForm);
            }
        });

        kanbanForms.getTaskLists().forEach(taskList -> {
            taskLists.add(checkAndUpdateTaskList(taskList, request));
        });

        kanbanForms.setTaskLists(taskLists);
        kanbanFormList.add(kanbanForms);
        kanban.setKanbanForms(kanbanFormList);
        return kanban;
    }

    private TaskList checkAndUpdateTaskList(TaskList taskLists, UpdateAssignToRequest request) {
        if (taskLists.getTaskId().equals(request.getTaskId())){
            AssignTo assignTo = new AssignTo();
            assignTo.setAssignId(UUID.randomUUID());
            assignTo.setUserId(request.getUserId());
            assignTo.setUserName(request.getAssignTo().getUserName());
            taskLists.setAssignTo(assignTo);
        }
        return taskLists;
    }
}
