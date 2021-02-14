package in.dagoan.commandImpl.project;

import in.dagoan.command.project.PostProjectChatCommand;
import in.dagoan.command.project.UpdateProjectMemberCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.ChatForm;
import in.dagoan.entity.form.MemberForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.project.PostProjectChatRequest;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.response.project.PostProjectChatResponse;
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
public class PostProjectChatCommandImpl implements PostProjectChatCommand {
    private ProjectRepository projectRepository;
    private ProjectFormRepository projectFormRepository;

    @Autowired
    public PostProjectChatCommandImpl(ProjectRepository projectRepository, ProjectFormRepository projectFormRepository) {
        this.projectRepository = projectRepository;
        this.projectFormRepository = projectFormRepository;
    }

    @Override
    public Mono<PostProjectChatResponse> execute(PostProjectChatRequest request) {
        return projectRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found!")))
                .map(project -> updateProjectForm(project, request))
                .flatMap(project -> projectRepository.save(project))
                .map(this::toResponse);
    }

    private PostProjectChatResponse toResponse(Project project) {
        PostProjectChatResponse response = new PostProjectChatResponse();
        response.setProjects(project.getProjects());
        return response;
    }

    private Project updateProjectForm(Project project, PostProjectChatRequest request) {
        ChatForm chatForm = new ChatForm();
        chatForm.setChatId(UUID.randomUUID());
        chatForm.setUserId(request.getUserId());
        chatForm.setSection(request.getSection());
        chatForm.setText(request.getText());
        chatForm.setUserName(request.getUserName());
        project.getChat().add(chatForm);
        return project;
    }
}
