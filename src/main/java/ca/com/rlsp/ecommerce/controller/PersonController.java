package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.UserSystem;
import ca.com.rlsp.ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    public static final String LEGAL_PERSON_CANT_BE_NULL = "Legal Person can't be NULL";

    @Autowired
    private PersonRepository personRepository;


    @Autowired
    private UserSystem


    /*end-point é microsservicos é um API*/
    @ResponseBody
    @PostMapping(value = "/saveLegalPerson")
    public ResponseEntity<LegalPerson> saveLegalPerson(@RequestBody LegalPerson legalPerson) throws EcommerceException{

        if (legalPerson == null) {
            throw new EcommerceException(LEGAL_PERSON_CANT_BE_NULL);
        }

        if (legalPerson.getId() == null && personRepository.existBusinessNumberRegistered(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException("Já existe CNPJ cadastrado com o número: " + legalPerson.getBusinessNumber());
        }

        legalPerson = pessoaUserService.salvarPessoaJuridica(legalPerson);

        return new ResponseEntity<LegalPerson>(legalPerson, HttpStatus.OK);
    }
}
