package de.cibek.mscasa.common.security.service;

import de.cibek.mscasa.common.client.UserManagementApiClient;
import de.cibek.mscasa.common.session.dto.SessionUserDto;
import de.cibek.mscasa.common.session.model.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author DaSee
 */
@Service
public class AuthenticatedUserService implements UserDetailsService {

    @Autowired
    private UserManagementApiClient userManagementApiClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Lookin for: " + username);

        final SessionUserDto user = userManagementApiClient.getSessionUserByUsername(username);
        System.out.println(user);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        final SessionUser sessionUser = new SessionUser(
                user.getEmail(),
                user.getPasswordHash(),
                AuthorityUtils.createAuthorityList(user.getAuthorities()),
                user.getFirstName(),
                user.getLastName());
        return sessionUser;
    }
}
