package in.dagoan.model.response.project;

import in.dagoan.entity.form.ProjectForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectResponse {
    private ProjectForm projects;
}
