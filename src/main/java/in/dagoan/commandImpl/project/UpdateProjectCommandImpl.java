package in.dagoan.commandImpl.project;

import in.dagoan.command.project.UpdateProjectCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.project.UpdateProjectRequest;
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
public class UpdateProjectCommandImpl implements UpdateProjectCommand {
    private ProjectRepository projectRepository;
    private ProjectFormRepository projectFormRepository;

    @Autowired
    public UpdateProjectCommandImpl(ProjectRepository projectRepository, ProjectFormRepository projectFormRepository) {
        this.projectRepository = projectRepository;
        this.projectFormRepository = projectFormRepository;
    }

    @Override
    public Mono<UpdateProjectResponse> execute(UpdateProjectRequest request) {
        return projectRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(project -> updateProjectForm(project, request))
                .flatMap(project -> projectRepository.save(project))
                .map(this::toResponse);
    }

    private UpdateProjectResponse toResponse(Project project) {
        UpdateProjectResponse response = new UpdateProjectResponse();
        response.setProjects(project.getProjects());
        return response;
    }

    private Project updateProjectForm(Project project, UpdateProjectRequest request) {
        ProjectForm projectForm = project.getProjects();
        projectForm.setStatus(request.getProjectStatus());
        projectForm.setProjectName(request.getProjectName());
        projectForm.setDescription(request.getDescription());
        project.setProjects(projectForm);
        return project;
    }
}
