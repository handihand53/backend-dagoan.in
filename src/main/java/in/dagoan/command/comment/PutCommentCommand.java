package in.dagoan.command.comment;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.comment.PutCommentRequest;
import in.dagoan.model.response.comment.PutCommentResponse;

public interface PutCommentCommand extends Command<PutCommentRequest, PutCommentResponse> {
}
