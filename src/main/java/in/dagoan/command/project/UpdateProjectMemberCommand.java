package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.request.project.UpdateProjectRequest;
import in.dagoan.model.response.project.UpdateProjectResponse;

public interface UpdateProjectMemberCommand extends Command<UpdateProjectMemberRequest, UpdateProjectResponse> {
}
