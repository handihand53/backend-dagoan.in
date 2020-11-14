package in.dagoan.model.request.taskList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskListRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID kanbanId;

    @NotNull
    private UUID taskId;
}
