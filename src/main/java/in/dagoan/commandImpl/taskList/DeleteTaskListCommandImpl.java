package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.DeleteTaskListCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.taskList.DeleteTaskListRequest;
import in.dagoan.model.response.taskList.DeleteTaskListResponse;
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
public class DeleteTaskListCommandImpl implements DeleteTaskListCommand {
    private KanbanRepository kanbanRepository;
    List<TaskList> taskList = new ArrayList<>();
    KanbanForm kanbanForm1 = new KanbanForm();

    @Autowired
    public DeleteTaskListCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<DeleteTaskListResponse> execute(DeleteTaskListRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> deleteTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban deleteTaskList(Kanban kanban, DeleteTaskListRequest request) {
        List<KanbanForm> kanbanFormList = new ArrayList<>();
        List<TaskList> newTaskList = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm -> {
            if (kanbanForm.getKanbanId().equals(request.getKanbanId())) {
                taskList = kanbanForm.getTaskLists();
                kanbanForm1 = kanbanForm;
            } else {
                kanbanFormList.add(kanbanForm);
            }
        });
        taskList.forEach(taskList1 -> {
            if (!checkTaskListToRemoved(taskList1, request))
                newTaskList.add(taskList1);
        });
        kanbanForm1.setTaskLists(newTaskList);
        kanbanFormList.add(kanbanForm1);
        kanban.setKanbanForms(kanbanFormList);
        return kanban;
    }

    private boolean checkTaskListToRemoved(TaskList taskList, DeleteTaskListRequest request) {
        return taskList.getTaskId().equals(request.getTaskId());
    }

    private DeleteTaskListResponse toResponse(Kanban kanban) {
        DeleteTaskListResponse response = new DeleteTaskListResponse();
        response.setKanban(kanban);
        return response;
    }
}
