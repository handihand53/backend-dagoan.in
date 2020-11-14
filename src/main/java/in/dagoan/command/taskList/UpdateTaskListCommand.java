package in.dagoan.command.taskList;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.taskList.UpdateTaskListRequest;
import in.dagoan.model.response.taskList.UpdateTaskListResponse;

public interface UpdateTaskListCommand extends Command<UpdateTaskListRequest, UpdateTaskListResponse> {
}