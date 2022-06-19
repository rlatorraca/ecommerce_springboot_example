package ca.com.rlsp.ecommerce.model;

import ca.com.rlsp.ecommerce.enums.PersonType;
import ca.com.rlsp.ecommerce.enums.UserType;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "natural_person")
@Table(name="natural_person")
@PrimaryKeyJoinColumn(name = "id")
public class NaturalPerson extends Person{

    private static final long serialVersionUID = 1L;
    @CPF(message = "Sin Number is not valid")
    @Column(name = "sin_number", nullable = false)
    private String sinNumber;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    public String getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(String sinNumber) {
        this.sinNumber = sinNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
