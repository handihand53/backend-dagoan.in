package in.dagoan.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskList {
    private UUID taskId;
    private String title;
    private UUID tagId;
    private String taskEstimatedTime;
    private String taskTimeLeft;
    private Integer section;
    private String description;
    private AssignTo assignTo;
    private List<String> imageList;
    private List<CommentForm> commentForms;
}
