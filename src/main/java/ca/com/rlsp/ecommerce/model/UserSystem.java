package ca.com.rlsp.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
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

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastPasswordDate;

    /* PERSON TYPE - Natuarl or Legal */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "person_fk"))
    private Person person;

    /* COMPANY | EMPRESA */
    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "ecommerce_company_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "ecommerce_company_fk"))
    private Person ecommerceCompany;

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
        //return null;
        return Collections.emptyList();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPasswordDate() {
        return lastPasswordDate;
    }

    public void setLastPasswordDate(Date lastPasswordDate) {
        this.lastPasswordDate = lastPasswordDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<RoleAccess> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<RoleAccess> accessList) {
        this.accessList = accessList;
    }

    public Person getEcommerceCompany() {
        return ecommerceCompany;
    }

    public void setEcommerceCompany(Person ecommerceCompany) {
        this.ecommerceCompany = ecommerceCompany;
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
