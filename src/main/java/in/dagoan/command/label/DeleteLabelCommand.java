package in.dagoan.command.label;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.label.DeleteLabelRequest;
import in.dagoan.model.response.label.DeleteLabelResponse;

public interface DeleteLabelCommand extends Command<DeleteLabelRequest, DeleteLabelResponse> {
}