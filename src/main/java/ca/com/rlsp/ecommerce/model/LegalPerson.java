package ca.com.rlsp.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "legal_person")
@Table(name = "legal_person")
@SequenceGenerator(name = "seq_legal_person", sequenceName = "seq_legal_person", allocationSize = 1, initialValue = 1)
public class LegalPerson extends Person{
}
