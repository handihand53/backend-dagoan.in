package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.project.DeleteProjectMemberRequest;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.response.project.DeleteProjectMemberResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;

public interface DeleteProjectMemberCommand extends Command<DeleteProjectMemberRequest, DeleteProjectMemberResponse> {
}
