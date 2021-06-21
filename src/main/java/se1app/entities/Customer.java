package se1app.entities;

import se1app.datatypes.AddressType;
import se1app.datatypes.EmailType;
import se1app.exceptions.InvalidEmailException;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;


/**
 * Representation of a customer.
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int _id;
    @Column(name = "first_name")
    private String _firstName;
    @Column(name = "last_name")
    private String _lastName;
    @AttributeOverride(name = "_email", column = @Column(name = "email_address"))
    private EmailType _emailAddress;
    private AddressType _address;


    @OneToMany(mappedBy = "_customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> _orders;

    @OneToMany(mappedBy = "_customer")
    private List<Space> _spaces;

    @Column(name = "is_owner")
    private boolean _isOwner;


    /**
     * Create a new customer.
     *
     * @param firstName    Customer first name.
     * @param lastName     Customer last name.
     * @param emailAddress E-mail address of the customer.
     * @@hrows InvalidEmailException Thrown if input is no valid e-mail.
     */

    public Customer(String firstName, String lastName, String emailAddress, String street, String houseNumber, String postalCode, String town, String country, boolean isOwner) throws InvalidEmailException {
        _firstName = firstName;
        _lastName = lastName;
        _emailAddress = new EmailType(emailAddress);
        _address = new AddressType(street, houseNumber, postalCode, town, country); //added adress in Constructor
        _orders = new ArrayList<Order>();
        _isOwner = isOwner;
        _spaces = new ArrayList<Space>();

    }


    /**
     * Empty constructor for Hibernate.
     */
    Customer() {
    }


    /**
     * Output the properties of the customer.
     *
     * @return The customer's attributes concatenated as string.
     */
    @Override
    public String toString() {
        var str = "[" + _id + "] " + _firstName + " " + _lastName + ", " + _emailAddress.getEmail() + ", " + _address.getWholeAdress(); //added adress in ToString
        if (_orders.size() > 0) {
            str += ", Orders: (";
            for (var i = 0; i < _orders.size(); i++) {
                str += "#" + _orders.get(i).getId();
                if (i < _orders.size() - 1) str += ", ";
            }
            str += ")";
        }
        return str;
    }

    /**
     * Get all oders of this customer.
     *
     * @return List of orders.
     */
    public List<Order> getOrders() {
        return _orders;
    }

    /**
     * Get the customer identifier.
     *
     * @return The customer ID.
     */
    public int getId() {
        return _id;
    }


    /**
     * Get the customer's first name.
     *
     * @return First name of the customer.
     */
    public String getFirstName() {
        return _firstName;
    }


    /**
     * Get the customer's last name.
     *
     * @return Last name of the customer.
     */
    public String getLastName() {
        return _lastName;
    }

    public List<Space> getSpaces() {
        return _spaces;
    }

    /**
     * Get the customer's e-mail address.
     *
     * @return E-mail address of the customer.
     */
    public EmailType getEmailAddress() {
        return _emailAddress;
    }

    //getters for Adress:

    public String getStreet() {
        return _address.getStreet();
    }

    public String getHouseNumber() {
        return _address.getHouseNumber();
    }

    public String getPostalCode() {
        return _address.getPostalCode();
    }

    public String getTown() {
        return _address.getTown();
    }

    public String getCountry() {
        return _address.getCountry();
    }

    //getting whole adress:

    public AddressType getAddress() {
        return _address;
    }


    public boolean getIsOwner() {
        return _isOwner;
    }

    public void setIsOwner(boolean _isOwner) {
        this._isOwner = _isOwner;
    }


    /**
     * Set a new first name.
     *
     * @param newFirstName The new first name.
     */
    public void setFirstName(String newFirstName) {
        _firstName = newFirstName;
    }


    /**
     * Set a new last name.
     *
     * @param newLastName The new last name.
     */
    public void setLastName(String newLastName) {
        _lastName = newLastName;
    }


    /**
     * Set a new e-mail address.
     *
     * @param newEmailAddress The new e-mail address.
     */
    public void setEmailAddress(EmailType newEmailAddress) {
        _emailAddress = newEmailAddress;
    }

    public void addSpace(Space space) {
        _spaces.add(space);
        space.setCustomer(this);
    }

    public void removeSpace(Space space) {
        _spaces.remove(space);
        space.setCustomer(null);
    }

}
