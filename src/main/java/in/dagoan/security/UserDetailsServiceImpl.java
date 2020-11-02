package in.dagoan.security;

import in.dagoan.entity.document.User;
import in.dagoan.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail)
            throws UsernameNotFoundException {
        User user = userRepository.findFirstByUserEmail(userEmail)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Email or Password are incorrect")))
                .block();
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        User user = userRepository.findFirstByUserId(id)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .block();
        return UserPrincipal.create(user);
    }

}

