package in.dagoan.model.response.invited;

import in.dagoan.entity.document.Invited;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitedResponse {
    private Invited invited;
}
