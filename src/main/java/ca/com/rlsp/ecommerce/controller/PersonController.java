package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.enums.PersonType;
import ca.com.rlsp.ecommerce.enums.UserType;
import ca.com.rlsp.ecommerce.exception.EcommerceException;
import ca.com.rlsp.ecommerce.model.Address;
import ca.com.rlsp.ecommerce.model.LegalPerson;
import ca.com.rlsp.ecommerce.model.NaturalPerson;
import ca.com.rlsp.ecommerce.model.dto.PostalCodeDTO;
import ca.com.rlsp.ecommerce.model.dto.QueryBusinessNumberDTO;
import ca.com.rlsp.ecommerce.repository.AddressRepository;
import ca.com.rlsp.ecommerce.repository.LegalPersonRepository;
import ca.com.rlsp.ecommerce.repository.NaturalPersonRepository;
import ca.com.rlsp.ecommerce.service.UpdateCounterNumberAccessEndPointService;
import ca.com.rlsp.ecommerce.service.PersonUserSystemService;
import ca.com.rlsp.ecommerce.util.BusinessNumberValidator;
import ca.com.rlsp.ecommerce.util.SinNumberValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {

    private LegalPersonRepository legalPersonRepository;
    private NaturalPersonRepository naturalPersonRepository;
    private PersonUserSystemService personUserSystemService;
    private AddressRepository addressRepository;

    private UpdateCounterNumberAccessEndPointService updateCounterNumberAccessEndPointService;


    private static final String INFORM_LEGAL_PERSON_TYPE = "Inform the type Legal or Supplier";
    public static final String LEGAL_PERSON_CANT_BE_NULL = "Legal Person can't be NULL [RLSP]: ";
    public static final String EXIST_BUSINESS_NUMBER_INTO_DB = "BUSINESS NUMBER already exist on Database [RLSP]: ";
    public static final String EXIST_PROVINCE_REGISTRATION_NUMBER_INTO_DB  = "PROVINCIAL NUMBER already exist on Database [RLSP]: ";
    public static final String INVALID_BUSINESS_NUMBER  = "Business Number inserted is INVALID [RLSP]: ";
    public static final String NATURAL_PERSON_CANT_BE_NULL = "Natural Person can't be NULL [RLSP]: ";
    public static final String EXIST_SIN_NUMBER_INTO_DB = "SIN NUMBER already exist on Database [RLSP]: ";
    public static final String INVALID_SIN_NUMBER  = "Sin Number is INVALID [RLSP]: ";

    public PersonController(LegalPersonRepository legalPersonRepository,
                            NaturalPersonRepository naturalPersonRepository,
                            PersonUserSystemService personUserSystemService,
                            AddressRepository addressRepository,
                            UpdateCounterNumberAccessEndPointService updateCounterNumberAccessEndPointService) {
        this.legalPersonRepository = legalPersonRepository;
        this.naturalPersonRepository = naturalPersonRepository;
        this.personUserSystemService = personUserSystemService;
        this.addressRepository = addressRepository;
        this.updateCounterNumberAccessEndPointService = updateCounterNumberAccessEndPointService;

    }

    @ResponseBody
    @GetMapping(value="/queryLegalPersonByBusinessNumber/{businessNumber}")
    public ResponseEntity<List<LegalPerson>> queryLegalPersonByBusinessNumber(@PathVariable String businessNumber){
        List<LegalPerson> legalPerson = legalPersonRepository.queryByBusinessNumberRegistered(businessNumber);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryLegalPersonByBusinessNumber");
        return new ResponseEntity<>(legalPerson, HttpStatus.OK);
    }



    @ResponseBody
    @GetMapping(value="/queryLegalPersonByProvincialRegistration/{registration}")
    public ResponseEntity<List<LegalPerson>> queryLegalPersonByProvincialRegistration(@PathVariable String registration){
        List<LegalPerson> legalPerson = legalPersonRepository.queryByProvincialRegistration(registration);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryLegalPersonByProvincialRegistration");
        return new ResponseEntity<>(legalPerson, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value="/queryLegalPersonByName/{name}")
    public ResponseEntity<List<LegalPerson>> queryLegalPersonByName(@PathVariable String name){
        List<LegalPerson> legalPersonList = legalPersonRepository.queryLegalPersonByName(name.trim().toUpperCase());
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryLegalPersonByName");
        return new ResponseEntity<>(legalPersonList, HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping(value="/queryNaturalPersonBySinNumber/{sin}")
    public ResponseEntity<List<NaturalPerson>> queryNaturalPersonBySinNumber(@PathVariable String sin){
        List<NaturalPerson> naturalPerson = naturalPersonRepository.queryNaturalPersonBySinNumber(sin);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryNaturalPersonBySinNumber");
        return new ResponseEntity<>(naturalPerson, HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping(value="/queryNaturalPersonByName/{name}")
    public ResponseEntity<List<NaturalPerson>> queryNaturalPersonByName(@PathVariable String name){
        List<NaturalPerson> naturalPersonList = naturalPersonRepository.queryNaturalPersonByName(name.trim().toUpperCase());
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryNaturalPersonByName");
        return new ResponseEntity<>(naturalPersonList, HttpStatus.CREATED);
    }


    @ResponseBody
    @GetMapping(value = "/queryPostalCode/{postalCode}")
    public ResponseEntity<PostalCodeDTO> queryPostalCode(@PathVariable String postalCode){

        PostalCodeDTO postalCodeDTO = personUserSystemService.fetchPostalCode(postalCode);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryPostalCode");
        return new ResponseEntity<PostalCodeDTO>(postalCodeDTO, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value="/queryBusinessNumberReceitaFederal/{businessNumber}")
    public ResponseEntity<QueryBusinessNumberDTO> queryBusinessNumberReceitaFederal(@PathVariable String businessNumber){

        QueryBusinessNumberDTO businessNumberDTO = personUserSystemService.fetchBusinnesNumberReceitaFederalWS(businessNumber);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("queryBusinessNumberReceitaFederal");
        return new ResponseEntity<QueryBusinessNumberDTO>(businessNumberDTO, HttpStatus.OK);
    }


    /* end-point é microsservicos numa API*/
    @ResponseBody
    @PostMapping(value = "/saveLegalPerson")
    public ResponseEntity<LegalPerson> saveLegalPerson(@RequestBody @Valid LegalPerson legalPerson) throws EcommerceException, MessagingException {


        if (legalPerson == null) {
            throw new EcommerceException(LEGAL_PERSON_CANT_BE_NULL);
        }

        if (legalPerson.getPersonType() == null) {
            throw new EcommerceException(INFORM_LEGAL_PERSON_TYPE);
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o BUSINNES NUMBER ja esta cadastrada no DB
        if (legalPerson.getId() == null && legalPersonRepository.queryByBusinessNumberRegistered(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException(EXIST_BUSINESS_NUMBER_INTO_DB + legalPerson.getBusinessNumber());
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o PROVINCIAL REGISTRATION NUMBER ja esta cadastrada no DB
        if (legalPerson.getId() == null && legalPersonRepository.queryByProvincialRegistration(legalPerson.getBusinessNumber()) != null) {
            throw new EcommerceException(EXIST_PROVINCE_REGISTRATION_NUMBER_INTO_DB + legalPerson.getBusinessNumber());
        }

        // Veriica se Business Number is true/false
        // Se false lancara uma excecao
        if ( !BusinessNumberValidator.isCNPJ(legalPerson.getBusinessNumber())) {
            throw new EcommerceException(INVALID_BUSINESS_NUMBER + "[" + legalPerson.getBusinessNumber() + "]");
        }

        // Se um registro de LegalPerson for inserido no DB
        // Atraves do CEP/PostalCode completa automaticamente os valores do endereco da PJ
        if(legalPerson.getId() == null || legalPerson.getId() < 0 ) {
            for(int position=0; position < legalPerson.getAddresses().size(); position++) {
                populatePersonLegalAddress(legalPerson, position);
            }
        } else {
            // Alteracao dos dados quando LegalPerson ja existir no DB
            for(int position=0; position < legalPerson.getAddresses().size(); position++) {
                Address addressTemp = addressRepository.findById(legalPerson.getAddresses().get(position).getId()).get();

                // Quando dor inserido um PostalCode diferente do existente no DB ele fara a alteracao no DB
                if(addressTemp.getZipPostalCode().equals(legalPerson.getAddresses().get(position).getZipPostalCode())){
                    populatePersonLegalAddress(legalPerson, position);
                }
            }
        }

        legalPerson = personUserSystemService.saveLegalPerson(legalPerson);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("saveLegalPerson");
        return new ResponseEntity<LegalPerson>(legalPerson, HttpStatus.OK);
    }

    private void populatePersonLegalAddress(LegalPerson legalPerson, int position) {
        PostalCodeDTO postalCodeDTO = personUserSystemService.fetchPostalCode(legalPerson.getAddresses().get(position).getZipPostalCode());

        legalPerson.getAddresses().get(position).setAddressLine01(postalCodeDTO.getLogradouro());
        legalPerson.getAddresses().get(position).setAddressLine02(postalCodeDTO.getComplemento());
        legalPerson.getAddresses().get(position).setCity(postalCodeDTO.getLocalidade());
        legalPerson.getAddresses().get(position).setProvince(postalCodeDTO.getUf());
        legalPerson.getAddresses().get(position).setNeighborhood(postalCodeDTO.getBairro());
    }


    @ResponseBody
    @PostMapping(value = "/saveNaturalPerson")
    public ResponseEntity<NaturalPerson> saveNaturalPerson(@RequestBody @Valid NaturalPerson naturalPerson) throws EcommerceException, MessagingException {


        if (naturalPerson == null) {
            throw new EcommerceException(NATURAL_PERSON_CANT_BE_NULL);
        }

        if (naturalPerson.getPersonType() == null) {
            naturalPerson.setPersonType(PersonType.NATURALPERSON);
        }

        // Verifica se Legal Person é um nova pessoa (por ter id = null); OR
        // Se Legal Person com o BUSINNES NUMBER ja esta cadastrada no DB
        if (naturalPerson.getId() == null && naturalPersonRepository.queryNaturalPersonBySinNumber(naturalPerson.getSinNumber()) != null) {
            throw new EcommerceException(EXIST_SIN_NUMBER_INTO_DB + naturalPerson.getSinNumber());
        }

        // Veriica se Business Number is true/false
        // Se false lancara uma excecao
        if ( !SinNumberValidator.isCPF(naturalPerson.getSinNumber())) {
            throw new EcommerceException(INVALID_SIN_NUMBER + "[" + naturalPerson.getSinNumber() + "]");
        }

        naturalPerson = personUserSystemService.saveNaturalPerson(naturalPerson);
        updateCounterNumberAccessEndPointService.saveIntoDbEndPointAccess("saveNaturalPerson");
        return new ResponseEntity<NaturalPerson>(naturalPerson, HttpStatus.OK);
    }

}
