package in.dagoan.model.request.taskList;

import in.dagoan.entity.form.TaskList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostTaskListRequest {
    private UUID userId;
    private UUID projectId;
    private UUID kanbanId;
    private TaskList taskList;
    private List<MultipartFile> images;
}
