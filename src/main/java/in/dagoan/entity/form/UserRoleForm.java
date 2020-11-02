package in.dagoan.entity.form;

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
public class UserRoleForm {

    private UUID userId;
    private List<UserRole> userRoles;

}
