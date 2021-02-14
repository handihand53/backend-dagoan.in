package in.dagoan.commandImpl.label;

import in.dagoan.command.label.GetLabelColorCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.document.Label;
import in.dagoan.entity.form.LabelForm;
import in.dagoan.model.request.label.GetLabelColorRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;
import in.dagoan.model.response.label.GetLabelColorResponse;
import in.dagoan.repository.LabelRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetLabelColorCommandImpl  implements GetLabelColorCommand {
    private LabelRepository labelRepository;
    LabelForm labelForm = new LabelForm();

    @Autowired
    public GetLabelColorCommandImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public Mono<GetLabelColorResponse> execute(GetLabelColorRequest request) {
        return labelRepository.findFirstByProjectId(request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Label not found!")))
                .map(label -> toResponse(label, request));
    }

    private GetLabelColorResponse toResponse(Label label, GetLabelColorRequest request) {
        GetLabelColorResponse response = new GetLabelColorResponse();
        label.getLabelForm().forEach((labelForm1) -> {
            if (labelForm1.getLabelFormId().equals(request.getLabelId())) {
                labelForm = labelForm1;
            }
        });
        response.setLabelColor(labelForm.getLabelColor());
        response.setLabelFormId(labelForm.getLabelFormId());
        response.setLabelName(labelForm.getLabelName());
        return response;
    }
}
