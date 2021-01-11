package in.dagoan.command.member;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.member.PostListProjectWithUserIdRequest;
import in.dagoan.model.response.member.PostListProjectWithUserIdResponse;

public interface PostListProjectByUserIdCommand extends Command<PostListProjectWithUserIdRequest, PostListProjectWithUserIdResponse> {
}
