package in.dagoan.model.response.taskList;

import in.dagoan.entity.form.KanbanForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskListSectionResponse {
    private List<KanbanForm> kanbanForm;
}
