package in.dagoan.command.comment;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.comment.AddCommentRequest;
import in.dagoan.model.response.comment.AddCommentResponse;

public interface PostCommentCommand extends Command<AddCommentRequest, AddCommentResponse> {
}
