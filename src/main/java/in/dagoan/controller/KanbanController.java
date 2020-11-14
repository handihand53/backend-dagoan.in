package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.kanban.*;
import in.dagoan.command.taskList.PostTaskListCommand;
import in.dagoan.model.request.kanban.*;
import in.dagoan.model.request.taskList.PostTaskListRequest;
import in.dagoan.model.response.kanban.*;
import in.dagoan.model.response.taskList.PostTaskListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class KanbanController {
    private CommandExecutor commandExecutor;

    @Autowired
    public KanbanController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.KANBAN)
    public Mono<Response<GetKanbanWithProjectIdResponse>> getKanbanByProjectId(@RequestBody GetKanbanWithProjectIdRequest request) {
        return commandExecutor.execute(GetKanbanWithProjectIdCommand.class, request)
                .log("#getKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.KANBAN_POST)
    public Mono<Response<PostKanbanWithProjectIdResponse>> postKanbanByProjectId(@RequestBody PostKanbanWithProjectIdRequest request) {
        return commandExecutor.execute(PostKanbanWithProjectIdCommand.class, request)
                .log("#postKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.KANBAN_UPDATE)
    public Mono<Response<UpdateKanbanWithProjectIdResponse>> updateKanbanByProjectId(@RequestBody UpdateKanbanWithProjectIdRequest request) {
        return commandExecutor.execute(UpdateKanbanWithProjectIdCommand.class, request)
                .log("#updateKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.KANBAN_DELETE)
    public Mono<Response<DeleteKanbanResponse>> deleteKanbanByProjectId(@RequestBody DeleteKanbanRequest request) {
        return commandExecutor.execute(DeleteKanbanWithProjectIdCommand.class, request)
                .log("#deleteKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
