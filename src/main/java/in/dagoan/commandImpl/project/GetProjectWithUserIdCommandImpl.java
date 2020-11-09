package in.dagoan.commandImpl.project;

import in.dagoan.command.project.GetProjectWithUserIdCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.model.response.project.GetProjectWithUserIdResponse;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProjectWithUserIdCommandImpl implements GetProjectWithUserIdCommand {

    private ProjectRepository projectRepository;

    @Autowired
    public GetProjectWithUserIdCommandImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<GetProjectWithUserIdResponse> execute(UUID request) {
        return projectRepository.findFirstByUserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(this::toResponse);
    }

    private GetProjectWithUserIdResponse toResponse(Project project) {
        GetProjectWithUserIdResponse response = new GetProjectWithUserIdResponse();
        BeanUtils.copyProperties(project, response);
        return response;
    }
}
