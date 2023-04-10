package ca.com.rlsp.ecommerce.model.dto;

import java.io.Serializable;

public class QsaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String qual;
    private String originCountry;
    private String LegalManagerName;
    private String LegalManagerQual;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getLegalManagerName() {
        return LegalManagerName;
    }

    public void setLegalManagerName(String legalManagerName) {
        LegalManagerName = legalManagerName;
    }

    public String getLegalManagerQual() {
        return LegalManagerQual;
    }

    public void setLegalManagerQual(String legalManagerQual) {
        LegalManagerQual = legalManagerQual;
    }
}
