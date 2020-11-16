package in.dagoan.command.project;

import com.blibli.oss.command.Command;
import in.dagoan.model.response.project.GetProjectWithProjectIdResponse;

import java.util.UUID;

public interface GetProjectWithProjectIdCommand extends Command<UUID, GetProjectWithProjectIdResponse> {
}
