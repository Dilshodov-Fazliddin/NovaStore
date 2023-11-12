package uz.nova.novastore.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.nova.novastore.entity.BaseEntity;
import uz.nova.novastore.entity.product.Favourites;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    private List<UserRole> role;
    @Enumerated(value = EnumType.STRING)
    private UserState state;
    private Integer attempts;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    private List<Favourites>favourites;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities=new ArrayList<>();
        role.forEach((role)->authorities.add(new SimpleGrantedAuthority(role.getRole())));
        List<PermissionEntity>userPermission=new ArrayList<>();
        role.forEach((roleEntity)-> userPermission.addAll(roleEntity.getPermissions()));
        userPermission.forEach((permissionEntity -> authorities.add(new SimpleGrantedAuthority(permissionEntity.getPermission()))));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
