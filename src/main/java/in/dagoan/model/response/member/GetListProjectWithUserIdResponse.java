package in.dagoan.model.response.member;

import in.dagoan.entity.document.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetListProjectWithUserIdResponse {
    private Member member;
}
