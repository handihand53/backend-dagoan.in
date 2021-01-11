package in.dagoan.commandImpl.comment;

import in.dagoan.command.comment.PutCommentCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.CommentForm;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.comment.AddCommentRequest;
import in.dagoan.model.request.comment.PutCommentRequest;
import in.dagoan.model.response.comment.AddCommentResponse;
import in.dagoan.model.response.comment.PutCommentResponse;
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
public class PutCommentCommandImpl implements PutCommentCommand {
    private KanbanRepository kanbanRepository;
    KanbanForm kanbanForms = new KanbanForm();

    @Autowired
    public PutCommentCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<PutCommentResponse> execute(PutCommentRequest request) {
        return kanbanRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> updateCommentTaskList(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban updateCommentTaskList(Kanban kanban, PutCommentRequest request) {
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

    private PutCommentResponse toResponse(Kanban kanban) {
        PutCommentResponse response = new PutCommentResponse();
        response.setKanbanForm(kanban.getKanbanForms());
        return response;
    }

    private TaskList checkAndUpdateCommentTaskList(TaskList taskLists, PutCommentRequest request) {
        List<CommentForm> commentFormList = new ArrayList<>();
        List<CommentForm> tempComment = new ArrayList<>();

        if (taskLists.getTaskId().equals(request.getTaskListId())){
            commentFormList = taskLists.getCommentForms();
        }

        commentFormList.forEach(commentForm -> {
            if (commentForm.getCommentId().equals(request.getCommentId())){
                CommentForm commentForm1 = commentForm;
                commentForm1.setComment(request.getComment());
                tempComment.add(commentForm1);
            } else {
                tempComment.add(commentForm);
            }
        });
        taskLists.setCommentForms(tempComment);
        return taskLists;
    }
}
