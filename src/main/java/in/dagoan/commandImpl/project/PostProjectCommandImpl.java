package in.dagoan.commandImpl.project;

import in.dagoan.command.project.PostProjectCommand;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.ChatForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.enums.ProjectStatus;
import in.dagoan.model.request.project.PostProjectRequest;
import in.dagoan.model.response.project.PostProjectResponse;
import in.dagoan.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostProjectCommandImpl implements PostProjectCommand {

    private ProjectRepository projectRepository;
    @Autowired
    public PostProjectCommandImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Mono<PostProjectResponse> execute(PostProjectRequest request) {
        return createProject(request)
                .flatMap(project -> projectRepository.save(project))
                .map(this::toResponse);
    }

    private Mono<Project> createProject(PostProjectRequest request) {
        log.info("#PostProjectCommand - Create new project for user {}", request.getUserId());
        return Mono.fromCallable(() -> toProject(request))
                .flatMap(project -> projectRepository.save(project));
    }

    private Project toProject(PostProjectRequest request) {
        ProjectForm projectForm = ProjectForm.builder()
                .projectId(UUID.randomUUID())
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .status(ProjectStatus.STATUS_ON_GOING)
                .members(request.getMemberForm())
                .createdAt(LocalDate.now())
                .build();

        List<ChatForm> chatForm = new ArrayList<>();

        Project project = Project.builder()
                .projectId(UUID.randomUUID())
                .userId(request.getUserId())
                .chat(chatForm)
                .projects(projectForm)
                .createdAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, project);
        return project;
    }

    private PostProjectResponse toResponse(Project project) {
        PostProjectResponse response = new PostProjectResponse();
        BeanUtils.copyProperties(project, response);
        response.setProjectForm(project.getProjects());
        response.setLastUpdated(project.getCreatedAt());
        return response;
    }
}
