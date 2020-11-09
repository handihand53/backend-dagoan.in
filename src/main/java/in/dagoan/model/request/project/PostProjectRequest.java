package in.dagoan.model.request.project;

import com.mongodb.lang.Nullable;
import in.dagoan.entity.form.MemberForm;
import in.dagoan.entity.form.ProjectForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String projectName;

    @NotBlank
    private String description;

    private List<MemberForm> memberForm;
}
