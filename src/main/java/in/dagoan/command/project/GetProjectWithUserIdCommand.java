package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.response.project.GetProjectWithUserIdResponse;

import java.util.UUID;

public interface GetProjectWithUserIdCommand extends Command<UUID, GetProjectWithUserIdResponse> {
}
