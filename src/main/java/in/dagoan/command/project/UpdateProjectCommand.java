package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.response.project.PostProjectResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;

public interface UpdateProjectCommand extends Command<UpdateProjectRequest, UpdateProjectResponse> {
}
