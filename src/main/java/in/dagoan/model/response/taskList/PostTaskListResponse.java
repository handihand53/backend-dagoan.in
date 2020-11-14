package in.dagoan.model.response.taskList;

import in.dagoan.entity.document.Kanban;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTaskListResponse {
    private Kanban kanban;
}
