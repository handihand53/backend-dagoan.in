package in.dagoan.commandImpl.taskList;

import in.dagoan.command.taskList.PostTaskListCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.CommentForm;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.taskList.PostTaskListRequest;
import in.dagoan.model.response.taskList.PostTaskListResponse;
import in.dagoan.repository.KanbanRepository;
import in.dagoan.util.FileUploadUtil;
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
public class PostTaskListCommandImpl implements PostTaskListCommand {
    private KanbanRepository kanbanRepository;

    @Autowired
    public PostTaskListCommandImpl(KanbanRepository kanbanRepository, FileUploadUtil fileUploadUtil) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<PostTaskListResponse> execute(PostTaskListRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> addNewTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban addNewTaskList(Kanban kanban, PostTaskListRequest request) {
        List<KanbanForm> kanbanForms = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm ->
                kanbanForms.add(checkAndUpdateTaskListKanbanForm(kanbanForm, request)));
        kanban.setKanbanForms(kanbanForms);
        return kanban;
    }

    private KanbanForm checkAndUpdateTaskListKanbanForm(KanbanForm kanbanForm, PostTaskListRequest request) {
        if (kanbanForm.getKanbanId().equals(request.getKanbanId())) {
            List<CommentForm> commentFormList = new ArrayList<>();
            List<TaskList> taskLists = kanbanForm.getTaskLists();
            request.getTaskList().setTaskId(UUID.randomUUID());
            request.getTaskList().setCommentForms(commentFormList);
            taskLists.add(request.getTaskList());
            kanbanForm.setTaskLists(taskLists);
        }
        return kanbanForm;
    }

    private PostTaskListResponse toResponse(Kanban kanban) {
        PostTaskListResponse response = new PostTaskListResponse();
        response.setKanban(kanban);
        return response;
    }
}
