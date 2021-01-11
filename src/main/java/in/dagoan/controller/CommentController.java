package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.comment.DeleteCommentCommand;
import in.dagoan.command.comment.PostCommentCommand;
import in.dagoan.command.comment.PutCommentCommand;
import in.dagoan.model.request.comment.AddCommentRequest;
import in.dagoan.model.request.comment.DeleteCommentRequest;
import in.dagoan.model.request.comment.PutCommentRequest;
import in.dagoan.model.response.comment.AddCommentResponse;
import in.dagoan.model.response.comment.DeleteCommentResponse;
import in.dagoan.model.response.comment.PutCommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private CommandExecutor commandExecutor;

    @Autowired
    public CommentController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @PostMapping(value = ApiPath.COMMENT_POST)
    public Mono<Response<AddCommentResponse>> postCommentByProjectId(@RequestBody AddCommentRequest request) {
        return commandExecutor.execute(PostCommentCommand.class, request)
                .log("#postComment - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.COMMENT_PUT)
    public Mono<Response<PutCommentResponse>> putCommentByProjectId(@RequestBody PutCommentRequest request) {
        return commandExecutor.execute(PutCommentCommand.class, request)
                .log("#putComment - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.COMMENT_DELETE)
    public Mono<Response<DeleteCommentResponse>> putCommentByProjectId(@RequestBody DeleteCommentRequest request) {
        return commandExecutor.execute(DeleteCommentCommand.class, request)
                .log("#DeleteComment - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
