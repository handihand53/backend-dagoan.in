package in.dagoan.command.label;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.label.GetLabelColorRequest;
import in.dagoan.model.response.label.GetLabelColorResponse;

public interface GetLabelColorCommand extends Command<GetLabelColorRequest, GetLabelColorResponse> {
}