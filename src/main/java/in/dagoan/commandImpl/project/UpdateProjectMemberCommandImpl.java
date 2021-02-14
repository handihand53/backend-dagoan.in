package in.dagoan.commandImpl.project;

import in.dagoan.command.project.UpdateProjectCommand;
import in.dagoan.command.project.UpdateProjectMemberCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.MemberForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.response.project.UpdateProjectResponse;
import in.dagoan.repository.ProjectFormRepository;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Slf4j
@Service
public class UpdateProjectMemberCommandImpl implements UpdateProjectMemberCommand {
    private ProjectRepository projectRepository;
    private ProjectFormRepository projectFormRepository;

    @Autowired
    public UpdateProjectMemberCommandImpl(ProjectRepository projectRepository, ProjectFormRepository projectFormRepository) {
        this.projectRepository = projectRepository;
        this.projectFormRepository = projectFormRepository;
    }

    @Override
    public Mono<UpdateProjectResponse> execute(UpdateProjectMemberRequest request) {
        return projectRepository.findFirstByProjectId(request.getProjectId())
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

    private Project updateProjectForm(Project project, UpdateProjectMemberRequest request) {
        ProjectForm projectForm = project.getProjects();
        MemberForm memberForm = request.getMemberForm();
        memberForm.setMemberId(UUID.randomUUID());
        projectForm.getMembers().add(memberForm);
        project.setProjects(projectForm);
        return project;
    }
}
