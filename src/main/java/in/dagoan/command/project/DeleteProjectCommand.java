package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.project.DeleteProjectRequest;
import in.dagoan.model.response.project.DeleteProjectResponse;

public interface DeleteProjectCommand extends Command<DeleteProjectRequest, DeleteProjectResponse> {
}
