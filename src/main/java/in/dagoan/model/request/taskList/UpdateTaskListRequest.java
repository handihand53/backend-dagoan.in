package in.dagoan.model.request.taskList;

import in.dagoan.entity.form.AssignTo;
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
public class UpdateTaskListRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    @NotNull
    private UUID kanbanId;

    @NotNull
    private UUID taskId;

    @NotNull
    private String title;

    @NotNull
    private String taskEstimatedTime;

    @NotNull
    private String taskTimeLeft;

    @NotNull
    private Integer section;

    private UUID tagId;

    private AssignTo assignTo;

    private List<String> imageList;
}
