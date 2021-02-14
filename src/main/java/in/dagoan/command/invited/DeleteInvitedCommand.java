package in.dagoan.command.invited;

import com.blibli.oss.command.Command;
import in.dagoan.model.request.invited.DeleteInvitedRequest;
import in.dagoan.model.request.invited.PostInvitedRequest;
import in.dagoan.model.response.invited.InvitedResponse;

public interface DeleteInvitedCommand extends Command<DeleteInvitedRequest, InvitedResponse> {
}
