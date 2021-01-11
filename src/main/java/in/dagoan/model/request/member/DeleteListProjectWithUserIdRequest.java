package in.dagoan.model.request.member;

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
public class DeleteListProjectWithUserIdRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;
}
