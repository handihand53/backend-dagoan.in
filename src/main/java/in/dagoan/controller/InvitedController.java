package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.comment.PostCommentCommand;
import in.dagoan.command.invited.DeleteInvitedCommand;
import in.dagoan.command.invited.GetInvitedCommand;
import in.dagoan.command.invited.PostInvitedCommand;
import in.dagoan.model.request.comment.AddCommentRequest;
import in.dagoan.model.request.invited.DeleteInvitedRequest;
import in.dagoan.model.request.invited.GetInvitedRequest;
import in.dagoan.model.request.invited.PostInvitedRequest;
import in.dagoan.model.response.comment.AddCommentResponse;
import in.dagoan.model.response.invited.InvitedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class InvitedController {
    private CommandExecutor commandExecutor;

    @Autowired
    public InvitedController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.INVITED)
    public Mono<Response<InvitedResponse>> getInvitedByProjectId(@RequestBody GetInvitedRequest request) {
        return commandExecutor.execute(GetInvitedCommand.class, request)
                .log("#getInvited - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.INVITED_POST)
    public Mono<Response<InvitedResponse>> postInvitedByProjectId(@RequestBody PostInvitedRequest request) {
        return commandExecutor.execute(PostInvitedCommand.class, request)
                .log("#postInvited - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.INVITED_DELETE)
    public Mono<Response<InvitedResponse>> deleteInvitedByProjectId(@RequestBody DeleteInvitedRequest request) {
        return commandExecutor.execute(DeleteInvitedCommand.class, request)
                .log("#deleteInvited - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
