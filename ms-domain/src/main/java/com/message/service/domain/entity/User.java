package com.message.service.domain.entity;

import com.fasterxml.jackson.annotation.*;
import com.message.service.domain.validation.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"}, name = "unique_login"),
        @UniqueConstraint(columnNames = {"email"}, name = "unique_email")
})
public class User extends BaseEntity {

    @Column(name = "fullname")
    private String fullname;
    @NotNull
    @NotEmpty
    @Column(name = "login", nullable = false)
    private String login;
    @ValidPassword //TODO: move to dto
    @Column(name = "password", nullable = false)
    private String password;
    @Transient
    @JsonIgnore
    private String confirmPassword;
    @ValidEmail
    @NotNull
    @NotEmpty
    @Column(name = "email", nullable = false)
    private String email;
    @JsonIgnoreProperties({"friends"})
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_to_user",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "friend_id", nullable = false, updatable = false))
    private Set<User> friends;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)}
    )
    private Set<Role> roles;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @JsonProperty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @JsonIgnore
    public Stream<User> getFriendsStream() {
        return friends == null ? Stream.empty() : friends.stream();
    }

    public void replaceFriends(Set<User> friends) {
        if (this.friends == null) {
            this.friends = friends;
        } else {
            this.friends.clear();
            this.friends.addAll(friends);
        }
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public Stream<String> getRolesStream() {
        return roles == null ? Stream.empty() : roles.stream().map(Role::getName);
    }

    public void replaceRoles(Set<Role> roles) {
        if (this.roles == null) {
            this.roles = roles;
        } else {
            this.roles.clear();
            this.roles.addAll(roles);
        }
    }

    @JsonIgnore
    public boolean hasRole(String role) {
        return roles.stream().filter(r -> role.equals(r.getName())).findAny().isPresent();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return login.equals(user.login);

    }

    public int hashCode() {
        return login.hashCode();
    }
}
