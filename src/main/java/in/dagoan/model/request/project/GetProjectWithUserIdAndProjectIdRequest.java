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
public class GetProjectWithUserIdAndProjectIdRequest {
    @NotNull
    private UUID userId;
    
    @NotNull
    private UUID projectId;
}
