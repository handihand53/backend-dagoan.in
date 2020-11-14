package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.taskList.DeleteTaskListCommand;
import in.dagoan.command.taskList.GetTaskListCommand;
import in.dagoan.command.taskList.PostTaskListCommand;
import in.dagoan.command.taskList.UpdateTaskListCommand;
import in.dagoan.model.request.taskList.DeleteTaskListRequest;
import in.dagoan.model.request.taskList.GetTaskListRequest;
import in.dagoan.model.request.taskList.PostTaskListRequest;
import in.dagoan.model.request.taskList.UpdateTaskListRequest;
import in.dagoan.model.response.taskList.DeleteTaskListResponse;
import in.dagoan.model.response.taskList.GetTaskListResponse;
import in.dagoan.model.response.taskList.PostTaskListResponse;
import in.dagoan.model.response.taskList.UpdateTaskListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskListController {
    private CommandExecutor commandExecutor;

    @Autowired
    public TaskListController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.TASKLIST)
    public Mono<Response<GetTaskListResponse>> getTaskListKanbanByProjectId(@RequestBody GetTaskListRequest request) {
        return commandExecutor.execute(GetTaskListCommand.class, request)
                .log("#postTaskListKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.TASKLIST_POST)
    public Mono<Response<PostTaskListResponse>> postTaskListKanbanByProjectId(@RequestBody PostTaskListRequest request) {
        return commandExecutor.execute(PostTaskListCommand.class, request)
                .log("#postTaskListKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.TASKLIST_UPDATE)
    public Mono<Response<UpdateTaskListResponse>> updateTaskListKanbanByProjectId(@RequestBody UpdateTaskListRequest request) {
        return commandExecutor.execute(UpdateTaskListCommand.class, request)
                .log("#updateTaskListKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.TASKLIST_DELETE)
    public Mono<Response<DeleteTaskListResponse>> deleteTaskListKanbanByProjectId(@RequestBody DeleteTaskListRequest request) {
        return commandExecutor.execute(DeleteTaskListCommand.class, request)
                .log("#deleteTaskListKanban - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
