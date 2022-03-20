package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.repository.PersonRepository;
import ca.com.rlsp.ecommerce.service.PersonUserSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private PersonRepository personRepository;
    private PersonUserSystemService personUserSystemService;

    public static final String LEGAL_PERSON_CANT_BE_NULL = "Legal Person can't be NULL [RLSP]: ";
    public static final String EXIST_BUSINESS_NUMBER_INTO_DB = "BUSINESS NUMBER already exist on Database [RLSP]: ";

    public PersonController(PersonRepository personRepository, PersonUserSystemService personUserSystemService) {
        this.personRepository = personRepository;
        this.personUserSystemService = personUserSystemService;
    }





    /* end-point é microsservicos numa API*/
    @ResponseBody
    @PostMapping(value = "/saveLegalPerson")
    public ResponseEntity<LegalPerson> saveLegalPerson(@RequestBody LegalPerson legalPerson) throws EcommerceException{


        if (legalPerson == null) {
            throw new EcommerceException(LEGAL_PERSON_CANT_BE_NULL);
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o BUSINNES NUMBER ja esta cadastrada no DB
        if (legalPerson.getId() == null && personRepository.existBusinessNumberRegistered(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException(EXIST_BUSINESS_NUMBER_INTO_DB + legalPerson.getBusinessNumber());
        }

        legalPerson = personUserSystemService.saveLegalPerson(legalPerson);

        return new ResponseEntity<LegalPerson>(legalPerson, HttpStatus.OK);
    }
}
