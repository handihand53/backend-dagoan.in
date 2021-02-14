package in.dagoan.controller;

import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.dagoan.ApiPath;
import in.dagoan.command.user.GetUserDetailByEmailCommand;
import in.dagoan.command.user.GetUserDetailCommand;
import in.dagoan.model.request.user.GetUserEmailRequest;
import in.dagoan.model.request.user.GetUserRequest;
import in.dagoan.model.response.GetUserDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private CommandExecutor commandExecutor;
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(CommandExecutor commandExecutor, ObjectMapper objectMapper) {
        this.commandExecutor = commandExecutor;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = ApiPath.GETUSER)
    public Mono<Response<GetUserDetailResponse>> userDetail(@RequestBody GetUserRequest request) {
        return commandExecutor.execute(GetUserDetailCommand.class, request)
                .log("#getUserDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.GETUSERBYEMAIL)
    public Mono<Response<GetUserDetailResponse>> userDetail(@RequestBody GetUserEmailRequest request) {
        return commandExecutor.execute(GetUserDetailByEmailCommand.class, request)
                .log("#getUserDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
