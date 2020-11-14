package in.dagoan.command.kanban;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.kanban.PostKanbanWithProjectIdRequest;
import in.dagoan.model.response.kanban.PostKanbanWithProjectIdResponse;

public interface PostKanbanWithProjectIdCommand extends Command<PostKanbanWithProjectIdRequest, PostKanbanWithProjectIdResponse> {
}
