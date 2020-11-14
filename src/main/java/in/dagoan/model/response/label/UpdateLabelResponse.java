package in.dagoan.model.response.label;

import in.dagoan.entity.form.LabelForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLabelResponse {
    private List<LabelForm> labelForm;
}
