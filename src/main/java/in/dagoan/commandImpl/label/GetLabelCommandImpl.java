package in.dagoan.commandImpl.label;

import in.dagoan.command.label.GetLabelCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Label;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.LabelForm;
import in.dagoan.entity.form.TaskList;
import in.dagoan.model.request.kanban.GetKanbanWithProjectIdRequest;
import in.dagoan.model.request.label.GetLabelRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;
import in.dagoan.model.response.label.GetLabelResponse;
import in.dagoan.repository.LabelRepository;
import in.dagoan.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetLabelCommandImpl implements GetLabelCommand {

    private LabelRepository labelRepository;
    private String[] defaultLabel = {"Front End", "Back End", "Major", "Minor", "Priority"};
    private String[] defaultColor = {"#FF9F1A", "#F2D600", "#C377E0", "#00C2E0", "#EB5A46"};

    @Autowired
    public GetLabelCommandImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Mono<GetLabelResponse> execute(GetLabelRequest request) {
        return labelRepository.findFirstByProjectIdAndUserId(request.getProjectId(), request.getUserId())
                .switchIfEmpty(createLabel(request))
                .flatMap(label -> labelRepository.save(label))
                .map(this::toResponse);
    }

    private Mono<Label> createLabel(GetLabelRequest request) {
        return Mono.fromCallable(() -> toLabel(request))
                .flatMap(label -> labelRepository.save(label));
    }

    private Label toLabel(GetLabelRequest request) {
        List<LabelForm> labelForms = new ArrayList<>();
        for (int i =0; i < defaultLabel.length; i++) {
            LabelForm labelForm = new LabelForm();
            labelForm.setLabelFormId(UUID.randomUUID());
            labelForm.setLabelName(defaultLabel[i]);
            labelForm.setLabelColor(defaultColor[i]);
            labelForms.add(labelForm);
        }
        Label label = Label.builder()
                .labelId(UUID.randomUUID())
                .userId(request.getUserId())
                .projectId(request.getProjectId())
                .labelForm(labelForms)
                .build();
        BeanUtils.copyProperties(request, label);
        return label;
    }

    private GetLabelResponse toResponse(Label label) {
        GetLabelResponse response = new GetLabelResponse();
        response.setLabelForm(label.getLabelForm());
        return response;
    }
}
