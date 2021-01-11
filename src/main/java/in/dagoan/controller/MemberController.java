package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.label.PostLabelCommand;
import in.dagoan.command.member.DeleteListProjectByUserIdCommad;
import in.dagoan.command.member.GetListProjectByUserIdCommand;
import in.dagoan.command.member.PostListProjectByUserIdCommand;
import in.dagoan.model.request.label.PostLabelRequest;
import in.dagoan.model.request.member.DeleteListProjectWithUserIdRequest;
import in.dagoan.model.request.member.GetListProjectWithUserIdRequest;
import in.dagoan.model.request.member.PostListProjectWithUserIdRequest;
import in.dagoan.model.response.label.PostLabelResponse;
import in.dagoan.model.response.member.DeleteListProjectWithUserIdResponse;
import in.dagoan.model.response.member.GetListProjectWithUserIdResponse;
import in.dagoan.model.response.member.PostListProjectWithUserIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {
    private CommandExecutor commandExecutor;

    @Autowired
    public MemberController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.MEMBER)
    public Mono<Response<GetListProjectWithUserIdResponse>> getMemberByProjectId(@RequestBody GetListProjectWithUserIdRequest request) {
        return commandExecutor.execute(GetListProjectByUserIdCommand.class, request)
                .log("#postMember - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.MEMBER_POST)
    public Mono<Response<PostListProjectWithUserIdResponse>> postMemberByProjectId(@RequestBody PostListProjectWithUserIdRequest request) {
        return commandExecutor.execute(PostListProjectByUserIdCommand.class, request)
                .log("#postMember - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.MEMBER_DELETE)
    public Mono<Response<DeleteListProjectWithUserIdResponse>> postMemberByProjectId(@RequestBody DeleteListProjectWithUserIdRequest request) {
        return commandExecutor.execute(DeleteListProjectByUserIdCommad.class, request)
                .log("#postMember - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
