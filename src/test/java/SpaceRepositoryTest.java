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
import se1app.repositories.SpaceRepository;

import javax.xml.parsers.SAXParser;


public class SpaceRepositoryTest {

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

    @AfterEach()
    public void dropDatabase() {
        _db.stop();
    }

    private static void createTestCustomer() {
        CustomerRepository.createCustomer("Frau", "Wendholt", "harCorePM2@mail.com", "Berliner Tor", "7","20099", "Hamburg", "Germany", true);
      }

    private static void createTestSpace(){

        createTestCustomer();
        var customer1 = CustomerRepository.getCustomerById(1);

        SpaceRepository.createSpace("pm2_bar", customer1);

    }

    @Test
    public void SpaceNameTest() {

        createTestSpace();
        var name = SpaceRepository.getSpaceById(1).getName();

        assertEquals("pm2_bar", name);

    }

    @Test
    public void spaceCreationTest() {
        assertEquals(0, SpaceRepository.getAllSpaces().size());
        createTestSpace();
        assertEquals(1, SpaceRepository.getAllSpaces().size());
    }

    @Test
    public void spaceQueryTest() {
        createTestSpace();
        var space1 = SpaceRepository.getSpaceById(1);
        assertEquals("pm2_bar", space1.getName());
    }

    @Test
    public void spaceDeletionTest() {
        createTestSpace();
        SpaceRepository.deleteSpaceById(1);
        assertEquals(0, SpaceRepository.getAllSpaces().size());
        var space1 = SpaceRepository.getSpaceById(1);
        assertEquals(null, space1);
    }


}
