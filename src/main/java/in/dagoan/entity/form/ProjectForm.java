package in.dagoan.entity.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import in.dagoan.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectForm {
    private UUID projectId;
    private ProjectStatus status;
    private String projectName;
    private String description;
    private List<MemberForm> members;
    private LocalDate createdAt;
}
