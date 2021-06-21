package se1app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        System.out.println("he");
        H2Database _db;
        H2Database.configure(new DatabaseConfig() {{
            initInMemory = true;
            annotatedClasses = Arrays.asList(Customer.class, Order.class, Space.class, Room.class);
        }});
        _db = H2Database.getInstance();

        CustomerRepository.createCustomer("Donald", "Trump", "fuckingcunt@web.com","WallStreet", "666","11111", "New York", "USA",  true);
        var customer1 = CustomerRepository.getCustomerById(1);
        SpaceRepository.createSpace("Conspiracy Cave", customer1);


        System.out.println(customer1.getAddress().getWholeAdress());

        System.out.println(SpaceRepository.getSpaceById(1).getName());

        EditSpaceUseCase.addRoom("imagePathTest.jpeg", 1);
        System.out.println(RoomRepository.getRoomById(1));

//        var jsonNode = new ObjectMapper().readTree(customer1.toString());
//        System.out.println(jsonNode);
//        System.out.println(customer1.toString());

        //System.out.println(wholeAdress);
        //assertEquals("Berliner Tor\n3\n20099\nHamburg\nGermany", customer1.getAddress().getWholeAdress());

        _db.stop();

    }
}
