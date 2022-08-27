package ca.com.rlsp.ecommerce.model.dto;

public class TrackingStatusDTO {

    private String distributionHub;

    private String city;

    private String province;

    private String country;

    private String status;

    public String getDistributionHub() {
        return distributionHub;
    }

    public void setDistributionHub(String distributionHub) {
        this.distributionHub = distributionHub;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
