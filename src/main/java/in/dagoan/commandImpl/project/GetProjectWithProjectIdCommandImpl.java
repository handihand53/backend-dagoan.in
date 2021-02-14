package in.dagoan.commandImpl.project;

import in.dagoan.command.project.GetProjectWithProjectIdCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.model.response.project.GetProjectWithProjectIdResponse;
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
public class GetProjectWithProjectIdCommandImpl implements GetProjectWithProjectIdCommand {

    private ProjectRepository projectRepository;

    @Autowired
    public GetProjectWithProjectIdCommandImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<GetProjectWithProjectIdResponse> execute(UUID request) {
        return projectRepository.findFirstByProjectId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(this::toResponse);
    }

    private GetProjectWithProjectIdResponse toResponse(Project project) {
        GetProjectWithProjectIdResponse response = new GetProjectWithProjectIdResponse();
        BeanUtils.copyProperties(project, response);
        response.setChatForm(project.getChat());
        return response;
    }
}
