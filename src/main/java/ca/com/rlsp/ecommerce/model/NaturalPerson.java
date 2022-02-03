package ca.com.rlsp.ecommerce.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "natural_person")
@Table(name="natural_person")
@PrimaryKeyJoinColumn(name = "id")
public class NaturalPerson extends Person{

    @Column(name = "sin_number")
    private String sinNumber;

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
}
