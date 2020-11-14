package in.dagoan.model.request.label;

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
public class PostLabelRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID projectId;

    @NotNull
    private String labelName;

    @NotNull
    private String labelColor;
}
