import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se1app.datatypes.AddressType;
import se1app.entities.Customer;
import se1app.entities.Order;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.persistency.DatabaseConfig;
import se1app.persistency.H2Database;
import se1app.repositories.CustomerRepository;

import javax.xml.parsers.SAXParser;


public class CustomerRepositoryTest {

  private H2Database _db;


  /** Setup the in-memory database. */
  @BeforeEach
  public void setupDatabase() {
    H2Database.configure(new DatabaseConfig() {{
      initInMemory = true;
      annotatedClasses = Arrays.asList(Customer.class, Order.class, Space.class, Room.class);
    }});
    _db = H2Database.getInstance();
  }


  /** Tear down the in-memory database. */
  @AfterEach()
  public void dropDatabase() {
    _db.stop();
  }

//String street,String houseNumber, String postalCode,String town,String country
  /** Create three entries. */
  private static void createTestCustomers() {
    CustomerRepository.createCustomer("Antonia", "Aalglatt", "aa@mail.com", "Berliner Tor", "3","20099", "Hamburg", "Germany", true);
    CustomerRepository.createCustomer("Bernd", "Bratwurst", "bb@mail.com", "Nernstweg", "26", "20993","Hamburg","Germany", true);
    CustomerRepository.createCustomer("Charlie", "Chaos", "cc@mail.com","Test", "test", "20000","Hamburg","Germany", true);
  }




  @Test
  public void addressTest(){

    createTestCustomers();

    var customer1 = CustomerRepository.getCustomerById(1);

    String address = customer1.getAddress().getWholeAdress();

    assertEquals("Berliner Tor, 3, 20099, Hamburg, Germany", address );


  }



  /** Test customer creation. */
  @Test
  public void customerCreationTest() {
    assertEquals(0, CustomerRepository.getAllCustomers().size());
    createTestCustomers();
    assertEquals(3, CustomerRepository.getAllCustomers().size());
  }


  /** Test customer query by ID. */
  @Test
  public void customerQueryTest() {
    createTestCustomers();
    var customer2 = CustomerRepository.getCustomerById(2);
    assertEquals("Bernd", customer2.getFirstName());
  }


  /** Test customer deletion. */
  @Test
  public void customerDeletionTest() {
    createTestCustomers();
    CustomerRepository.deleteCustomerById(3);
    assertEquals(2, CustomerRepository.getAllCustomers().size());
    var customer3 = CustomerRepository.getCustomerById(3);
    assertEquals(null, customer3);
  }
}
