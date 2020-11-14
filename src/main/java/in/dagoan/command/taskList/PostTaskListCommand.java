package in.dagoan.command.taskList;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.taskList.PostTaskListRequest;
import in.dagoan.model.response.taskList.PostTaskListResponse;

public interface PostTaskListCommand extends Command<PostTaskListRequest, PostTaskListResponse> {
}
