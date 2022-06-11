package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.model.dto.PostalCodeDTO;
import ca.com.rlsp.ecommerce.repository.LegalPersonRepository;
import ca.com.rlsp.ecommerce.service.PersonUserSystemService;
import ca.com.rlsp.ecommerce.util.BusinessNumberValidator;
import ca.com.rlsp.ecommerce.util.SinNumberValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class PersonController {

    private LegalPersonRepository personRepository;
    private PersonUserSystemService personUserSystemService;

    public static final String LEGAL_PERSON_CANT_BE_NULL = "Legal Person can't be NULL [RLSP]: ";
    public static final String EXIST_BUSINESS_NUMBER_INTO_DB = "BUSINESS NUMBER already exist on Database [RLSP]: ";
    public static final String EXIST_PROVINCE_REGISTRATION_NUMBER_INTO_DB  = "PROVINCIAL NUMBER already exist on Database [RLSP]: ";
    public static final String INVALID_BUSINESS_NUMBER  = "Business Number inserted is INVALID [RLSP]: ";
    public static final String NATURAL_PERSON_CANT_BE_NULL = "Natural Person can't be NULL [RLSP]: ";
    public static final String EXIST_SIN_NUMBER_INTO_DB = "SIN NUMBER already exist on Database [RLSP]: ";
    public static final String INVALID_SIN_NUMBER  = "Sin Number is INVALID [RLSP]: ";

    public PersonController(LegalPersonRepository personRepository, PersonUserSystemService personUserSystemService) {
        this.personRepository = personRepository;
        this.personUserSystemService = personUserSystemService;
    }

    @ResponseBody
    @GetMapping(value = "/getPostalCode/{postalCode}")
    public ResponseEntity<PostalCodeDTO> getPostalCode(@PathVariable String postalCode){

        PostalCodeDTO postalCodeDTO = personUserSystemService.fetchPostalCode(postalCode);

        return new ResponseEntity<PostalCodeDTO>(postalCodeDTO, HttpStatus.OK);
    }

    /* end-point é microsservicos numa API*/
    @ResponseBody
    @PostMapping(value = "/saveLegalPerson")
    public ResponseEntity<LegalPerson> saveLegalPerson(@RequestBody @Valid LegalPerson legalPerson) throws EcommerceException, MessagingException {


        if (legalPerson == null) {
            throw new EcommerceException(LEGAL_PERSON_CANT_BE_NULL);
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o BUSINNES NUMBER ja esta cadastrada no DB
        if (legalPerson.getId() == null && personRepository.existBusinessNumberRegistered(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException(EXIST_BUSINESS_NUMBER_INTO_DB + legalPerson.getBusinessNumber());
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o PROVINCIAL REGISTRATION NUMBER ja esta cadastrada no DB
        if (legalPerson.getId() == null && personRepository.existProvincialRegistration(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException(EXIST_PROVINCE_REGISTRATION_NUMBER_INTO_DB + legalPerson.getBusinessNumber());
        }

        // Veriica se Business Number is true/false
        // Se false lancara uma excecao
        if ( !BusinessNumberValidator.isCNPJ(legalPerson.getBusinessNumber())) {
            throw new EcommerceException(INVALID_BUSINESS_NUMBER + "[" + legalPerson.getBusinessNumber() + "]");
        }

        legalPerson = personUserSystemService.saveLegalPerson(legalPerson);

        return new ResponseEntity<LegalPerson>(legalPerson, HttpStatus.OK);
    }


    @ResponseBody
    @PostMapping(value = "/saveNaturalPerson")
    public ResponseEntity<NaturalPerson> saveNaturalPerson(@RequestBody @Valid NaturalPerson naturalPerson) throws EcommerceException, MessagingException {


        if (naturalPerson == null) {
            throw new EcommerceException(NATURAL_PERSON_CANT_BE_NULL);
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o BUSINNES NUMBER ja esta cadastrada no DB
        if (naturalPerson.getId() == null && personRepository.existBusinessNumberRegistered(naturalPerson.getSinNumber()) != null) {
            throw new EcommerceException(EXIST_SIN_NUMBER_INTO_DB + naturalPerson.getSinNumber());
        }

        // Veriica se Business Number is true/false
        // Se false lancara uma excecao
        if ( !SinNumberValidator.isCPF(naturalPerson.getSinNumber())) {
            throw new EcommerceException(INVALID_SIN_NUMBER + "[" + naturalPerson.getSinNumber() + "]");
        }

        naturalPerson = personUserSystemService.saveNaturalPerson(naturalPerson);

        return new ResponseEntity<NaturalPerson>(naturalPerson, HttpStatus.OK);
    }
}
