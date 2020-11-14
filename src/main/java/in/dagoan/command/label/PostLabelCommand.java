package in.dagoan.command.label;
import com.blibli.oss.command.Command;
import in.dagoan.model.request.label.PostLabelRequest;
import in.dagoan.model.response.label.PostLabelResponse;

public interface PostLabelCommand extends Command<PostLabelRequest, PostLabelResponse> {
}
