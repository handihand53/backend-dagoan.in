package in.dagoan.model.request.project;

import in.dagoan.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    @NotBlank
    private String projectName;

    @NotBlank
    private String description;

    @NotNull
    private ProjectStatus projectStatus;
}
