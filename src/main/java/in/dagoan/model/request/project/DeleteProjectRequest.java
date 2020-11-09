package in.dagoan.model.request.project;

import in.dagoan.entity.form.MemberForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProjectRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;
}
