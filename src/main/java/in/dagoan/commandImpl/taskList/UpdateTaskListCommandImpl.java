package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.UpdateTaskListCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.request.taskList.GetTaskListRequest;
import in.dagoan.model.request.taskList.UpdateTaskListRequest;
import in.dagoan.model.response.project.UpdateProjectResponse;
import in.dagoan.model.response.taskList.GetTaskListResponse;
import in.dagoan.model.response.taskList.UpdateTaskListResponse;
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
public class UpdateTaskListCommandImpl implements UpdateTaskListCommand {
    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();

    @Autowired
    public UpdateTaskListCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<UpdateTaskListResponse> execute(UpdateTaskListRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> updateTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }
    private UpdateTaskListResponse toResponse(Kanban kanban) {
        UpdateTaskListResponse response = new UpdateTaskListResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private Kanban updateTaskList(Kanban kanban, UpdateTaskListRequest request) {
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

    private TaskList checkAndUpdateTaskList(TaskList taskLists, UpdateTaskListRequest request) {
        if (taskLists.getTaskId().equals(request.getTaskId())){
            taskLists.setImageList(request.getImageList());
            taskLists.setAssignTo(request.getAssignTo());
            taskLists.setSection(request.getSection());
            taskLists.setTagId(request.getTagId());
            taskLists.setTitle(request.getTitle());
            taskLists.setTaskEstimatedTime(request.getTaskEstimatedTime());
            taskLists.setTaskTimeLeft(request.getTaskTimeLeft());
        }
        return taskLists;
    }
}
