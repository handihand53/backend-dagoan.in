package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import in.dagoan.ApiPath;
import in.dagoan.command.kanban.GetKanbanWithProjectIdCommand;
import in.dagoan.command.label.DeleteLabelCommand;
import in.dagoan.command.label.GetLabelCommand;
import in.dagoan.command.label.PostLabelCommand;
import in.dagoan.command.label.UpdateLabelCommand;
import in.dagoan.model.request.kanban.GetKanbanWithProjectIdRequest;
import in.dagoan.model.request.label.DeleteLabelRequest;
import in.dagoan.model.request.label.GetLabelRequest;
import in.dagoan.model.request.label.PostLabelRequest;
import in.dagoan.model.request.label.UpdateLabelRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;
import in.dagoan.model.response.label.DeleteLabelResponse;
import in.dagoan.model.response.label.GetLabelResponse;
import in.dagoan.model.response.label.PostLabelResponse;
import in.dagoan.model.response.label.UpdateLabelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class LabelController {
    private CommandExecutor commandExecutor;

    @Autowired
    public LabelController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.LABEL)
    public Mono<Response<GetLabelResponse>> getLabelByProjectId(@RequestBody GetLabelRequest request) {
        return commandExecutor.execute(GetLabelCommand.class, request)
                .log("#getLabel - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.LABEL_POST)
    public Mono<Response<PostLabelResponse>> postLabelByProjectId(@RequestBody PostLabelRequest request) {
        return commandExecutor.execute(PostLabelCommand.class, request)
                .log("#postLabel - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.LABEL_UPDATE)
    public Mono<Response<UpdateLabelResponse>> updateLabelByProjectId(@RequestBody UpdateLabelRequest request) {
        return commandExecutor.execute(UpdateLabelCommand.class, request)
                .log("#updateLabel - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @DeleteMapping(value = ApiPath.LABEL_DELETE)
    public Mono<Response<DeleteLabelResponse>> deleteLabelByProjectId(@RequestBody DeleteLabelRequest request) {
        return commandExecutor.execute(DeleteLabelCommand.class, request)
                .log("#deleteLabel - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}