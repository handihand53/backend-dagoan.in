package in.dagoan.commandImpl.project;

import in.dagoan.command.project.DeleteProjectCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.response.project.DeleteProjectResponse;
import in.dagoan.repository.ProjectFormRepository;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DeleteProjectCommandImpl implements DeleteProjectCommand {
    private ProjectRepository projectRepository;

    @Autowired
    public DeleteProjectCommandImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<DeleteProjectResponse> execute(DeleteProjectRequest request) {
        return projectRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .flatMap(project -> projectRepository.delete(project))
                .map(project -> toResponse());
    }

    private DeleteProjectResponse toResponse() {
        DeleteProjectResponse response = new DeleteProjectResponse();
        return response;
    }
}
 