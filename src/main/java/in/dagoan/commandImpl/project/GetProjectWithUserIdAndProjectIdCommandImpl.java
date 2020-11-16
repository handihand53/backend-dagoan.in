package in.dagoan.commandImpl.project;

import in.dagoan.command.project.GetProjectWithUserIdAndProjectIdCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.model.request.project.GetProjectWithUserIdAndProjectIdRequest;
import in.dagoan.model.response.project.GetProjectWithUserIdAndProjectIdResponse;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProjectWithUserIdAndProjectIdCommandImpl implements GetProjectWithUserIdAndProjectIdCommand {

    private ProjectRepository projectRepository;

    @Autowired
    public GetProjectWithUserIdAndProjectIdCommandImpl(in.dagoan.repository.ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<GetProjectWithUserIdAndProjectIdResponse> execute(GetProjectWithUserIdAndProjectIdRequest request) {
        return projectRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(project -> toResponse(project, request));
    }

    private GetProjectWithUserIdAndProjectIdResponse toResponse(Project project, GetProjectWithUserIdAndProjectIdRequest request) {
        GetProjectWithUserIdAndProjectIdResponse response = new GetProjectWithUserIdAndProjectIdResponse();
//        ProjectForm projectForm = project.getProjects().stream()
//                .filter(projectForm1 -> projectForm1.getProjectId().equals(request.getProjectId()))
//                .findFirst()
//                .get();
//        response.setProjectForm(projectForm);
        return response;
    }

}
