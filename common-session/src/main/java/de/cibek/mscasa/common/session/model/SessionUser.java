/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cibek.mscasa.common.session.model;

import java.util.Collection;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author DaSee
 */
@ToString(callSuper = true)
@Getter
public class SessionUser extends User implements Comparable<SessionUser> {

    private final String firstName;
    private final String lastName;

    public SessionUser(String email, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName) {
        super(email, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int compareTo(SessionUser other) {
        if (lastName.compareTo(other.lastName) == 0) {
            if (firstName.compareTo(other.firstName) == 0) {
                return getUsername().compareTo(other.getUsername());
            } else {
                return firstName.compareTo(other.firstName);
            }
        } else {
            return lastName.compareTo(other.lastName);
        }
    }
}
