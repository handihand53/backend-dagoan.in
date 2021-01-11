package in.dagoan.commandImpl.comment;

import in.dagoan.command.comment.DeleteCommentCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.CommentForm;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.comment.DeleteCommentRequest;
import in.dagoan.model.response.comment.DeleteCommentResponse;
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
public class DeleteCommentCommandImpl implements DeleteCommentCommand {
    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();

    @Autowired
    public DeleteCommentCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<DeleteCommentResponse> execute(DeleteCommentRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> deleteCommentTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban deleteCommentTaskList(Kanban kanban, DeleteCommentRequest request) {
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
            taskLists.add(checkAndDeleteCommentTaskList(taskList, request));
        });

        kanbanForms.setTaskLists(taskLists);
        kanbanFormList.add(kanbanForms);
        kanban.setKanbanForms(kanbanFormList);
        return kanban;
    }

    private DeleteCommentResponse toResponse(Kanban kanban) {
        DeleteCommentResponse response = new DeleteCommentResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private TaskList checkAndDeleteCommentTaskList(TaskList taskLists, DeleteCommentRequest request) {
        List<CommentForm> commentFormList = new ArrayList<>();
        List<CommentForm> tempComment = new ArrayList<>();

        if (taskLists.getTaskId().equals(request.getTaskListId())){
            commentFormList = taskLists.getCommentForms();
        }

        commentFormList.forEach(commentForm -> {
            if (!commentForm.getCommentId().equals(request.getCommentId()) && commentForm.getUserId().equals(request.getUserId())){
                tempComment.add(commentForm);
            }
        });
        taskLists.setCommentForms(tempComment);
        return taskLists;
    }
}
