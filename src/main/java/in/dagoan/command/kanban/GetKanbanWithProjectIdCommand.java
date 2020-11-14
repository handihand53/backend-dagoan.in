package in.dagoan.command.kanban;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.kanban.GetKanbanWithProjectIdRequest;
import in.dagoan.model.response.kanban.GetKanbanWithProjectIdResponse;

public interface GetKanbanWithProjectIdCommand extends Command<GetKanbanWithProjectIdRequest, GetKanbanWithProjectIdResponse> {
}

