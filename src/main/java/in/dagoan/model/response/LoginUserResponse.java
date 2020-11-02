package in.dagoan.model.response;

import in.dagoan.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {

    private UUID userId;
    private List<UserRole> userRoles;
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginUserResponse(UUID userId, List<UserRole> userRoles, String accessToken) {
        this.userId = userId;
        this.userRoles = userRoles;
        this.accessToken = accessToken;
    }
}
