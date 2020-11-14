package in.dagoan.commandImpl.label;

import in.dagoan.command.label.UpdateLabelCommand;
import in.dagoan.entity.document.Label;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.LabelForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.label.UpdateLabelRequest;
import in.dagoan.model.response.label.UpdateLabelResponse;
import in.dagoan.repository.LabelRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UpdateLabelCommandImpl implements UpdateLabelCommand {
    private LabelRepository labelRepository;

    @Autowired
    public UpdateLabelCommandImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Mono<UpdateLabelResponse> execute(UpdateLabelRequest request) {
        return labelRepository.findFirstByProjectIdAndUserId(request.getProjectId(), request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Label not found!")))
                .map(label -> updateLabelForm(label, request))
                .flatMap(label -> labelRepository.save(label))
                .map(this::toResponse);
    }

    private Label updateLabelForm(Label label, UpdateLabelRequest request) {
        List<LabelForm> labelForms = new ArrayList<>();
        label.getLabelForm().forEach(labelForm ->
                labelForms.add(checkAndUpdateLabelForm(labelForm, request)));
        label.setLabelForm(labelForms);
        return label;
    }

    private LabelForm checkAndUpdateLabelForm(LabelForm labelForm, UpdateLabelRequest request) {
        if (labelForm.getLabelFormId().equals(request.getLabelId())) {
            labelForm.setLabelColor(request.getLabelColor());
            labelForm.setLabelName(request.getLabelName());
        }
        return labelForm;
    }

    private UpdateLabelResponse toResponse(Label label) {
        UpdateLabelResponse response = new UpdateLabelResponse();
        response.setLabelForm(label.getLabelForm());
        return response;
    }
}
