package com.infodev.sanimaotp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_credentials")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String username;

    private String password;

    private int userStatus;

    private boolean requiredPassChange = false;

    public boolean isRequiredPassChange() {
        return requiredPassChange;
    }

    public void setRequiredPassChange(boolean requiredPassChange) {
        this.requiredPassChange = requiredPassChange;
    }

    public User(int userStatus) {
        this.userStatus = userStatus;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;

    public User(String username, String password, int userStatus, List userRoles) {
        this.username = username;
        this.password = password;
        this.userStatus = userStatus;
        this.userRoles = userRoles;
    }

    public User(String username, String password, int userStatus, List userRoles, boolean requiredPassChange) {
        this.username = username;
        this.password = password;
        this.userStatus = userStatus;
        this.userRoles = userRoles;
        this.requiredPassChange = requiredPassChange;
    }

    public User() {
    }

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.userStatus = user.userStatus;
        this.userRoles = user.userRoles;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRole() {
        return userRoles;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRoles = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getUserRole().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public void removeRole(UserRole userRole) {
        this.userRoles.remove(userRole);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
