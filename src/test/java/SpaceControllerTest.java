import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se1app.datatypes.ImageType;
import se1app.entities.Customer;
import se1app.entities.Order;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.facade.Webserver;
import se1app.persistency.DatabaseConfig;
import se1app.persistency.H2Database;
import se1app.repositories.CustomerRepository;
import se1app.repositories.SpaceRepository;
import se1app.usecases.EditSpaceUseCase;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class SpaceControllerTest {

    private H2Database _db;
    private Webserver _webserver;


    /** Setup the in-memory database and webserver. */
    @BeforeEach
    public void setup() {
        H2Database.configure(new DatabaseConfig() {
            {
                initInMemory = true;
                annotatedClasses = Arrays.asList(Customer.class, Order.class, Space.class, Room.class);
            }
        });
        _db = H2Database.getInstance();
        CustomerRepository.createCustomer("Max", "Mustermann", "max-mustermann123@web.de","Berliner Tor", "3","20099", "Hamburg", "Germany", true);
        SpaceRepository.createSpace("TheHive",CustomerRepository.getCustomerById(1));
        ImageType image1 = new ImageType("standardImage1.jpeg");
        EditSpaceUseCase.addRoom("standardImage1.jpeg", 1);
        EditSpaceUseCase.addRoom("standardImage2.jpeg", 1);

        _webserver = new Webserver(7001, null);
        _webserver.start();
        RestAssured.port = 7001;
        RestAssured.basePath = "api/";
    }


    /** Tear down the in-memory database and webserver. */
    @AfterEach()
    public void teardown() {
        _db.stop();
        _webserver.stop();
    }

    @Test
    void createRoomTest() {
        given().
                contentType(ContentType.JSON).
                body("{\"space_id\":\"1\", \"image\":\"bild.jpeg\"}").
                when().
                post("/spaces/create_room/").
                then().
                statusCode(201);
        given().
                when().
                get("/spaces/show_rooms/1").
                then().
                statusCode(200).
                body("get(2).wallCover.image", equalTo("bild.jpeg"));
    }


    @Test
    void openSpaceTest(){
        given().
                when().put("spaces/open_space/1").then().statusCode(201);
        given().
                when().get("spaces/1").then().body("opened", equalTo(true));
    }

    @Test
    void closeSpaceTest(){
        given().
                when().put("spaces/close_space/1").then().statusCode(201);
        given().
                when().get("spaces/1").then().body("opened", equalTo(false));
    }

    @Test
    void setEntryFeeTest(){
        given().
                contentType(ContentType.JSON).
                body("{\"fee\":\"77\"}").
                when().put("spaces/set_entry_fee/1").then().statusCode(201);
        given().
                when().get("spaces/1").then().body("entryFee", equalTo(77));
    }

    @Test
    void enableFaceControl(){
        given().
                when().put("spaces/enable_face_control/1").then().statusCode(201);
        given().
                when().get("spaces/1").then().body("faceControl", equalTo(true));
    }

    @Test
    void disableFaceControl(){
        given().
                when().put("spaces/disable_face_control/1").then().statusCode(201);
        given().
                when().get("spaces/1").then().body("faceControl", equalTo(false));
    }


}
