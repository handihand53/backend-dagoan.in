package in.dagoan.commandImpl.label;

import in.dagoan.command.label.GetLabelCommand;
import in.dagoan.command.label.PostLabelCommand;
import in.dagoan.entity.document.Label;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.LabelForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.enums.ProjectStatus;
import in.dagoan.model.request.label.PostLabelRequest;
import in.dagoan.model.request.project.PostProjectRequest;
import in.dagoan.model.response.label.PostLabelResponse;
import in.dagoan.model.response.project.PostProjectResponse;
import in.dagoan.repository.LabelRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostLabelCommandImpl implements PostLabelCommand {
    private LabelRepository labelRepository;

    @Autowired
    public PostLabelCommandImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Mono<PostLabelResponse> execute(PostLabelRequest request) {
        return labelRepository.findFirstByProjectIdAndUserId(request.getProjectId(), request.getUserId())
                .map(label -> addLabelForm(label, request))
                .flatMap(label -> labelRepository.save(label))
                .map(this::toResponse);
    }

    private Label addLabelForm(Label label, PostLabelRequest request) {
        List<LabelForm> labelForms = label.getLabelForm();
        LabelForm labelForm = LabelForm.builder()
                .labelFormId(UUID.randomUUID())
                .labelColor(request.getLabelColor())
                .labelName(request.getLabelName())
                .build();
        labelForms.add(labelForm);
        label.setLabelForm(labelForms);
        return label;
    }

    private PostLabelResponse toResponse(Label label) {
        PostLabelResponse response = new PostLabelResponse();
        response.setLabelForm(label.getLabelForm());
        return response;
    }
}
