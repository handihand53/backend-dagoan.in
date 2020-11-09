package in.dagoan.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskList {
    private String title;
    private String tag;
    private String tagColors;
    private String taskEstimatedTime;
    private String taskTimeLeft;
    private AssignTo assignTo;
    private List<String> imageList;
}
