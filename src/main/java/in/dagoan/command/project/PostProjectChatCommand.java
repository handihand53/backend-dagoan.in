package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.project.PostProjectChatRequest;
import in.dagoan.model.request.project.UpdateProjectMemberRequest;
import in.dagoan.model.response.project.PostProjectChatResponse;
import in.dagoan.model.response.project.UpdateProjectResponse;

public interface PostProjectChatCommand extends Command<PostProjectChatRequest, PostProjectChatResponse> {
}
