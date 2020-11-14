package in.dagoan.command.label;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.label.GetLabelRequest;
import in.dagoan.model.response.label.GetLabelResponse;

public interface GetLabelCommand extends Command<GetLabelRequest, GetLabelResponse> {
}