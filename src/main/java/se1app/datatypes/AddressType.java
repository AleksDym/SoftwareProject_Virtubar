package se1app.datatypes;

import javax.persistence.Embeddable; //same as in EmailType

@Embeddable
public class AddressType {//Jede Adresse hat folgende Attributen:

    private String country;
    private String town;
    private String street;
    private String postalCode;
    private String houseNumber;

    public AddressType(String _street, String _houseNumber, String _postalCode, String town, String _country) { //constructor

        this.street = _street;
        this.houseNumber = _houseNumber;
        this.postalCode = _postalCode;
        this.town = town;
        this.country = _country;
    }

    //getters:

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    //ganze Adresse:

    public String getWholeAdress() {
        return String.join(", ", new String[]{street, houseNumber, postalCode, town, country});
    }

    /**
     * Empty constructor for Hibernate.
     */
    public AddressType() {
    }
}
