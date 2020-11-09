package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.project.*;
import in.dagoan.commandImpl.project.GetProjectWithUserIdCommandImpl;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.request.project.GetProjectWithUserIdAndProjectIdRequest;
import in.dagoan.model.request.project.PostProjectRequest;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.response.project.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private CommandExecutor commandExecutor;

    @Autowired
    public ProjectController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.PROJECT)
    public Mono<Response<GetProjectWithUserIdResponse>> getProjectWithUserId(@RequestParam UUID userId) {
        return commandExecutor.execute(GetProjectWithUserIdCommand.class, userId)
                .log("#getProjectWithUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PROJECT_DETAIL)
    public Mono<Response<GetProjectWithUserIdAndProjectIdResponse>> getProjectWithUserIdAndProjectId(
            @RequestBody GetProjectWithUserIdAndProjectIdRequest request
    ) {
        return commandExecutor.execute(GetProjectWithUserIdAndProjectIdCommand.class, request)
                .log("#getProjectWithUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.PROJECT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostProjectResponse>> postNewProject(@RequestBody PostProjectRequest request) {
        return commandExecutor.execute(PostProjectCommand.class, request)
                .log("#postNewProject - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.PROJECT_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateProjectResponse>> updateProject(@RequestBody UpdateProjectRequest request) {
        return commandExecutor.execute(UpdateProjectCommand.class, request)
                .log("#updateCartProductAmount - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.PROJECT_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<DeleteProjectResponse>> deleteProject(@RequestBody DeleteProjectRequest request) {
        return commandExecutor.execute(DeleteProjectCommand.class, request)
                .log("#deleteCartProductAmount - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
