package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.GetTaskListCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.taskList.GetTaskListRequest;
import in.dagoan.model.response.taskList.GetTaskListResponse;
import in.dagoan.repository.KanbanRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetTaskListCommandImpl implements GetTaskListCommand {
    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();
    TaskList taskLists = new TaskList();

    @Autowired
    public GetTaskListCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<GetTaskListResponse> execute(GetTaskListRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> toResponse(kanban, request));
    }

    private GetTaskListResponse toResponse(Kanban kanban, GetTaskListRequest request) {
        kanban.getKanbanForms().forEach(kanbanForm -> {
            if (kanbanForm.getKanbanId().equals(request.getKanbanId())){
                kanbanForms = kanbanForm;
            }
        });
        kanbanForms.getTaskLists().forEach(taskList -> {
            if (taskList.getTaskId().equals(request.getTaskId())){
                taskLists = taskList;
            }
        });
        GetTaskListResponse response = new GetTaskListResponse();
        response.setTaskList(taskLists);
        return response;
    }
}
