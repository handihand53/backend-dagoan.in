package in.dagoan.command.comment;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.comment.DeleteCommentRequest;
import in.dagoan.model.response.comment.DeleteCommentResponse;

public interface DeleteCommentCommand extends Command<DeleteCommentRequest, DeleteCommentResponse> {
}
