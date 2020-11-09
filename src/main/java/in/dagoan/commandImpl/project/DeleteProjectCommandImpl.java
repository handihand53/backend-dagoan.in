package in.dagoan.commandImpl.project;

import in.dagoan.command.project.DeleteProjectCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.response.project.DeleteProjectResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;
import in.dagoan.repository.ProjectFormRepository;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeleteProjectCommandImpl implements DeleteProjectCommand {
    private ProjectRepository projectRepository;
    private ProjectFormRepository projectFormRepository;

    @Autowired
    public DeleteProjectCommandImpl(ProjectRepository projectRepository, ProjectFormRepository projectFormRepository) {
        this.projectRepository = projectRepository;
        this.projectFormRepository = projectFormRepository;
    }

    @Override
    public Mono<DeleteProjectResponse> execute(DeleteProjectRequest request) {
        return projectRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(project -> deleteProjectForm(project, request))
                .flatMap(project -> projectRepository.save(project))
                .map(this::toResponse);
    }

    private Project deleteProjectForm(Project project, DeleteProjectRequest request) {
        List<ProjectForm> projectForms = new ArrayList<>();
        project.getProjects().forEach(projectForm -> {
            if (!checkProjectToRemoved(projectForm, request))
                projectForms.add(projectForm);
        });
        project.setProjects(projectForms);
        return project;
    }

    private boolean checkProjectToRemoved(ProjectForm project, DeleteProjectRequest request) {
        return project.getProjectId().equals(request.getProjectId());
    }

    private DeleteProjectResponse toResponse(Project project) {
        DeleteProjectResponse response = new DeleteProjectResponse();
        BeanUtils.copyProperties(project, response);
        return response;
    }
}
