import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se1app.datatypes.ImageType;
import se1app.entities.Customer;
import se1app.entities.Order;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.persistency.DatabaseConfig;
import se1app.persistency.H2Database;
import se1app.repositories.CustomerRepository;
import se1app.repositories.RoomRepository;
import se1app.repositories.SpaceRepository;
import se1app.usecases.EditSpaceUseCase;
import se1app.usecases.OrderUseCase;


public class EditSpaceUseCaseTest {
    private H2Database _db;


    /** Setup the in-memory database. */
    @BeforeEach
    public void setupDatabase() {
        H2Database.configure(new DatabaseConfig() {{
            initInMemory = true;
            annotatedClasses = Arrays.asList(Customer.class, Order.class, Space.class, Room.class);
        }});
        _db = H2Database.getInstance();
        CustomerRepository.createCustomer("Donald", "Trump", "fuckingcunt@web.com","WallStreet", "666","11111", "New York", "USA",  true);
        var customer1 = CustomerRepository.getCustomerById(1);
        SpaceRepository.createSpace("Conspiracy Cave", customer1);

    }


    /** Tear down the in-memory database. */
    @AfterEach()
    public void dropDatabase() {
        _db.stop();
    }

    @Test
    public void addRoom(){
        EditSpaceUseCase.addRoom("imagePathTest.jpeg", 1);
        Room room = RoomRepository.getRoomById(1);
        String image = room.getWallCover().getImage();
        assertEquals("imagePathTest.jpeg",image );
        assertEquals(1, room.getSpace().getSpaceId());
    }

    @Test
    public void openSpace(){
        EditSpaceUseCase.openSpace(1);
        assertEquals(true, SpaceRepository.getSpaceById(1).getOpened());
    }

    @Test
    public void closeSpace(){

        EditSpaceUseCase.openSpace(1);
        EditSpaceUseCase.closeSpace(1);
        assertEquals(false, SpaceRepository.getSpaceById(1).getOpened());
    }

    @Test
    public void setEntryFee(){

        EditSpaceUseCase.setEntryFee(1,15);
        assertEquals(15, SpaceRepository.getSpaceById(1).getEntryFee());
    }

    @Test
    public void enableFaceControl(){
        EditSpaceUseCase.enableFaceControl(1);
        assertEquals(true, SpaceRepository.getSpaceById(1).getFaceControl());
    }

    @Test
    public void disableFaceControl(){
        EditSpaceUseCase.disableFaceControl(1);
        assertEquals(false, SpaceRepository.getSpaceById(1).getFaceControl());
    }




}
