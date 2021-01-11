package in.dagoan.model.request.taskList;

import in.dagoan.entity.form.TaskList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskListSectionRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID kanbanId;

    @NotNull
    private List<TaskList> taskLists;
}
