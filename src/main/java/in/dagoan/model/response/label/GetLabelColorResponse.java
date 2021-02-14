package in.dagoan.model.response.label;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLabelColorResponse {
    private UUID labelFormId;
    private String labelName;
    private String labelColor;
}
