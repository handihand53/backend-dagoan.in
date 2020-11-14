package in.dagoan.model.response.taskList;

import in.dagoan.entity.form.KanbanForm;
import in.dagoan.entity.form.TaskList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskListResponse {
    private List<KanbanForm> kanbanForm;
}
