package in.dagoan.command.taskList;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.taskList.GetTaskListRequest;
import in.dagoan.model.response.taskList.GetTaskListResponse;

public interface GetTaskListCommand extends Command<GetTaskListRequest, GetTaskListResponse> {
}
