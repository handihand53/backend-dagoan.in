package in.dagoan.model.request.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProjectMemberRequest {
    @NotNull
    private UUID memberId;

    @NotNull
    private UUID projectId;
}
