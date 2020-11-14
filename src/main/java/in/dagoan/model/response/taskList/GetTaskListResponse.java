package in.dagoan.model.response.taskList;

import in.dagoan.entity.form.TaskList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskListResponse {
    private TaskList taskList;
}
