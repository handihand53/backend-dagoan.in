package in.dagoan.model.response.project;

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
public class GetProjectWithUserIdResponse {
    private UUID projectId;
    private UUID userId;
    private List<ProjectForm> projects;
}
