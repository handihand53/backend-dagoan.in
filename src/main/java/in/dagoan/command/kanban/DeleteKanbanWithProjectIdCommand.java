package in.dagoan.command.kanban;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.kanban.DeleteKanbanRequest;
import in.dagoan.model.response.kanban.DeleteKanbanResponse;

public interface DeleteKanbanWithProjectIdCommand extends Command<DeleteKanbanRequest, DeleteKanbanResponse> {
}
