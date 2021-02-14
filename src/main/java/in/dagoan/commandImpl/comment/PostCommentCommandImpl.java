package in.dagoan.commandImpl.comment;

import in.dagoan.command.comment.PostCommentCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.CommentForm;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.comment.AddCommentRequest;
import in.dagoan.model.response.comment.AddCommentResponse;
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
public class PostCommentCommandImpl implements PostCommentCommand {

    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();

    @Autowired
    public PostCommentCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<AddCommentResponse> execute(AddCommentRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> updateCommentTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban updateCommentTaskList(Kanban kanban, AddCommentRequest request) {
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
            taskLists.add(checkAndUpdateCommentTaskList(taskList, request));
        });

        kanbanForms.setTaskLists(taskLists);
        kanbanFormList.add(kanbanForms);
        kanban.setKanbanForms(kanbanFormList);
        return kanban;
    }

    private AddCommentResponse toResponse(Kanban kanban) {
        AddCommentResponse response = new AddCommentResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private TaskList checkAndUpdateCommentTaskList(TaskList taskLists, AddCommentRequest request) {
        if (taskLists.getTaskId().equals(request.getTaskListId())){
            CommentForm commentForm = new CommentForm();
            commentForm.setCommentId(UUID.randomUUID());
            commentForm.setComment(request.getComment());
            commentForm.setUserId(request.getUserId());
            commentForm.setUserName(request.getUserName());
            List<CommentForm> commentFormList = taskLists.getCommentForms();
            commentFormList.add(commentForm);
            taskLists.setCommentForms(commentFormList);
        }
        return taskLists;
    }
}
