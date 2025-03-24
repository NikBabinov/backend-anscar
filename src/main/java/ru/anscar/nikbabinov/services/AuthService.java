package ru.anscar.nikbabinov.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.anscar.nikbabinov.dto.UserDTO;
import ru.anscar.nikbabinov.entities.Users;
import ru.anscar.nikbabinov.repositories.UsersRepositories;
import ru.anscar.nikbabinov.security.UserSecurity;

@Service
public class AuthService implements UserDetailsService {

    private final UsersRepositories usersRepositories;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Users loginUser(UserDTO userDTO) {
        Users users = usersRepositories.findUsersByEmail(userDTO.getEmail());
        if (users == null){
            throw new UsernameNotFoundException("Users not found: " + userDTO.getEmail());
        }
        if (passwordEncoder.matches(userDTO.getPassword(), users.getPassword())) {
            return users;
        } else {
            return null;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = getUserByEmail(email);
        if (users == null) {
            throw new UsernameNotFoundException("Users not found: " + email);
        }
        return new UserSecurity(users);
    }

    public Users getUserByEmail(String email) {
        return usersRepositories.findUsersByEmail(email);
    }

}
