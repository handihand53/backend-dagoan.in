package in.dagoan.model.response.project;

import in.dagoan.entity.form.ChatForm;
import in.dagoan.entity.form.ProjectForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectWithProjectIdResponse {
    private UUID projectId;
    private UUID userId;
    private ProjectForm projects;
    private List<ChatForm> chatForm;
}
