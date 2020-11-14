package in.dagoan.command.taskList;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.taskList.DeleteTaskListRequest;
import in.dagoan.model.response.taskList.DeleteTaskListResponse;

public interface DeleteTaskListCommand extends Command<DeleteTaskListRequest, DeleteTaskListResponse> {
}
