package ca.com.rlsp.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_system")
@SequenceGenerator(name = "seq_user_system", sequenceName = "seq_user_system", initialValue = 1, allocationSize = 1)
public class UserSystem implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_system")
    private Long id;

    private String login;
    private String password;

    @Temporal(TemporalType.DATE)
    private Date lastPasswordDate;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_fk"))
    private Person person;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_access",

               uniqueConstraints = @UniqueConstraint(
                       columnNames = {"user_system_id", "role_access_id"},
                       name = "unique_access_user"),

               joinColumns = @JoinColumn(
                       name = "user_system_id",
                       referencedColumnName = "id" ,
                       table = "user_system",
                       unique = false,
                       foreignKey = @ForeignKey(
                                name = "user_system_fk",
                                value = ConstraintMode.CONSTRAINT)),

               inverseJoinColumns = @JoinColumn(
                       name = "role_access_id",
                       referencedColumnName = "id",
                       table = "role_access",
                       unique = false,
                       foreignKey = @ForeignKey(
                               name = "role_access_fk",
                               value = ConstraintMode.CONSTRAINT))
    )
    private List<RoleAccess> accessList;

    /* AUTHORITES => are the ROLES , ex: ROLE_ADMIN; ROLE_*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
