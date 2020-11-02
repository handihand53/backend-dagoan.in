package in.dagoan.security;

import in.dagoan.entity.document.User;
import in.dagoan.entity.form.UserRoleForm;
import in.dagoan.enums.UserRole;
import in.dagoan.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataProvider {

    private UserRepository userRepository;

    @Autowired
    public UserDataProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRoleForm provideUserData(String userEmail) {
        UserRoleForm dataForm = new UserRoleForm();
        User user = userRepository.findFirstByUserEmail(userEmail).block();
        if(user != null) {
            BeanUtils.copyProperties(user, dataForm);
            return dataForm;
        }
        else {
            throw new RuntimeException("User not found.");
        }
    }

}
