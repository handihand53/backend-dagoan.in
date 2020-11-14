package in.dagoan.commandImpl.label;

import in.dagoan.command.label.DeleteLabelCommand;
import in.dagoan.entity.document.Label;
import in.dagoan.entity.document.Project;
import in.dagoan.entity.form.LabelForm;
import in.dagoan.entity.form.ProjectForm;
import in.dagoan.model.request.label.DeleteLabelRequest;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.response.label.DeleteLabelResponse;
import in.dagoan.model.response.project.DeleteProjectResponse;
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
public class DeleteLabelCommandImpl implements DeleteLabelCommand {
    private LabelRepository labelRepository;

    @Autowired
    public DeleteLabelCommandImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Mono<DeleteLabelResponse> execute(DeleteLabelRequest request) {
        return labelRepository.findFirstByProjectIdAndUserId(request.getProjectId(), request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("Label not found!")))
                .map(label -> deleteLabelForm(label, request))
                .flatMap(label -> labelRepository.save(label))
                .map(this::toResponse);
    }

    private Label deleteLabelForm(Label label, DeleteLabelRequest request) {
        List<LabelForm> labelForms = new ArrayList<>();
        label.getLabelForm().forEach(labelForm -> {
            if (!checkLabelToRemoved(labelForm, request))
                labelForms.add(labelForm);
        });
        label.setLabelForm(labelForms);
        return label;
    }

    private boolean checkLabelToRemoved(LabelForm labelForm, DeleteLabelRequest request) {
        return labelForm.getLabelFormId().equals(request.getLabelId());
    }

    private DeleteLabelResponse toResponse(Label label) {
        DeleteLabelResponse response = new DeleteLabelResponse();
        response.setLabelForm(label.getLabelForm());
        return response;
    }
}
