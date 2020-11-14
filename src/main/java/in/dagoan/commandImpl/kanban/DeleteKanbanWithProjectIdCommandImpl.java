package in.dagoan.commandImpl.kanban;

import in.dagoan.command.kanban.DeleteKanbanWithProjectIdCommand;
import in.dagoan.entity.document.Kanban;
import in.dagoan.entity.form.KanbanForm;
import in.dagoan.model.request.kanban.DeleteKanbanRequest;
import in.dagoan.model.response.kanban.DeleteKanbanResponse;
import in.dagoan.repository.KanbanRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeleteKanbanWithProjectIdCommandImpl implements DeleteKanbanWithProjectIdCommand {
    private KanbanRepository kanbanRepository;

    @Autowired
    public DeleteKanbanWithProjectIdCommandImpl(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public Mono<DeleteKanbanResponse> execute(DeleteKanbanRequest request) {
        return kanbanRepository.findFirstByUserIdAndProjectId(request.getUserId(), request.getProjectId())
                .switchIfEmpty(Mono.error(new NotFoundException("Kanban not found!")))
                .map(kanban -> deleteKanbanForm(kanban, request))
                .flatMap(kanban -> kanbanRepository.save(kanban))
                .map(this::toResponse);
    }

    private Kanban deleteKanbanForm(Kanban kanban, DeleteKanbanRequest request) {
        List<KanbanForm> kanbanForms = new ArrayList<>();
        kanban.getKanbanForms().forEach(kanbanForm -> {
            if (!checkKanbanToRemoved(kanbanForm, request))
                kanbanForms.add(kanbanForm);
        });
        kanban.setKanbanForms(kanbanForms);
        return kanban;
    }

    private boolean checkKanbanToRemoved(KanbanForm kanban, DeleteKanbanRequest request) {
        return kanban.getKanbanId().equals(request.getKanbanId());
    }

    private DeleteKanbanResponse toResponse(Kanban kanban) {
        DeleteKanbanResponse response = new DeleteKanbanResponse();
        response.setKanban(kanban);
        return response;
    }
}
