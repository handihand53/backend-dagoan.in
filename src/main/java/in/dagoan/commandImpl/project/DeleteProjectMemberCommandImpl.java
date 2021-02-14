package in.dagoan.commandImpl.project;

import in.dagoan.command.project.DeleteProjectCommand;
import in.dagoan.command.project.DeleteProjectMemberCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.MemberForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.project.DeleteProjectMemberRequest;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.response.project.DeleteProjectMemberResponse;
import in.dagoan.model.response.project.DeleteProjectResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;
import in.dagoan.repository.ProjectRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DeleteProjectMemberCommandImpl implements DeleteProjectMemberCommand {
    private ProjectRepository projectRepository;

    @Autowired
    public DeleteProjectMemberCommandImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<DeleteProjectMemberResponse> execute(DeleteProjectMemberRequest request) {
        return projectRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(project -> deleteMember(project, request))
                .flatMap(project -> projectRepository.save(project))
                .map(this::toResponse);
    }

    private DeleteProjectMemberResponse toResponse(Project project) {
        DeleteProjectMemberResponse response = new DeleteProjectMemberResponse();
        response.setProjects(project.getProjects());
        return response;
    }

    private Project deleteMember(Project project, DeleteProjectMemberRequest request) {
        ProjectForm projectForm = project.getProjects();
        List<MemberForm> memberFormList = new ArrayList<>();
        projectForm.getMembers().forEach(memberForm -> {
            if(!memberForm.getMemberId().equals(request.getMemberId())){
                memberFormList.add(memberForm);
            }
        });
        projectForm.setMembers(memberFormList);
        project.setProjects(projectForm);
        return project;
    }
}
 