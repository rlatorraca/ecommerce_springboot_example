package ca.com.rlsp.shoponline.model;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "natural_person")
@Table(name="natural_person")
@SequenceGenerator(name = "seq_natural_person", sequenceName = "seq_natural_person", allocationSize = 1, initialValue = 1)
public class NaturalPerson extends Person{
}
