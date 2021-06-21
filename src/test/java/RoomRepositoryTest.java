import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se1app.datatypes.AddressType;
import se1app.datatypes.ImageType;
import se1app.datatypes.PlaylistType;
import se1app.entities.Customer;
import se1app.entities.Order;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.persistency.DatabaseConfig;
import se1app.persistency.H2Database;
import se1app.repositories.CustomerRepository;
import se1app.repositories.RoomRepository;
import se1app.repositories.SpaceRepository;

import javax.xml.parsers.SAXParser;



public class RoomRepositoryTest {

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

    private static void createTestRoom(){

        createTestSpace();

        var space1 = SpaceRepository.getSpaceById(1);
        RoomRepository.createRoom(new ImageType("testImage.jpeg"), space1);
    }

    @Test
    public void RoomImageTest() {

        createTestRoom();

        var image = RoomRepository.getRoomById(1).getWallCover().getImage();
        assertEquals("testImage.jpeg", image);
    }

    @Test
    public void RoomPlaylistTest() {

        createTestRoom();

        PlaylistType string = new  PlaylistType(Arrays.asList("american anthem", "Gorillaz - Every planet we reach is dead", "Little Big - Rave on"));

         RoomRepository.getRoomById(1).setPlaylist(string);
        var playlist = RoomRepository.getRoomById(1).getPlaylist();

        assertEquals("american anthem", playlist.getPlaylist().get(0));
    }


    @Test
    public void roomCreationTest() {
        assertEquals(0, RoomRepository.getAllRooms().size());
        createTestRoom();
        assertEquals(1, RoomRepository.getAllRooms().size());
    }

    @Test
    public void roomDeletionTest() {
        createTestRoom();
       RoomRepository.deleteRoomById(1);
        assertEquals(0, RoomRepository.getAllRooms().size());
        var room1 = RoomRepository.getRoomById(1);
        assertEquals(null, room1);
    }
}
