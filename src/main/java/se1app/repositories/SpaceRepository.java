package se1app.repositories;

import se1app.entities.Customer;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.exceptions.InvalidEmailException;
import se1app.persistency.H2Database;

import java.util.List;

public class SpaceRepository {

    public static void createSpace(String _name, Customer customer) {
        try {
            var space = new Space( _name, customer);
            saveSpace(space);
            customer.getSpaces().add(space);
            CustomerRepository.saveCustomer(customer);
        } catch (InvalidEmailException ex) {
            System.err.println("Space creation failed: \n -> " + ex);
        }
    }

    public static void saveSpace(Space space) {
        var session = H2Database.getInstance().getSession();
        var transaction = session.beginTransaction();
        session.save(space);
        transaction.commit();
    }

    public static List<Space> getAllSpaces() {
        var session = H2Database.getInstance().getSession();
        var spaces = session.createQuery("FROM Space", Space.class).getResultList();
        return spaces;
    }

    public static Space getSpaceById(int spaceId) {
        var session = H2Database.getInstance().getSession();
        var space = session.get(Space.class, spaceId);
        return space;
    }

    public static void addRoom(Room room, int spaceId){
        var session = H2Database.getInstance().getSession();
        var space = session.get(Space.class, spaceId);
        space.addRoom(room);
        saveSpace(space);
    }

    public static void deleteSpaceById(int spaceId) {
        var session = H2Database.getInstance().getSession();
        var space = session.get(Space.class, spaceId);
        var transaction = session.beginTransaction();
        session.delete(space);
        transaction.commit();
    }

    public static void printSpaceTable() {
        var spaces = getAllSpaces();
        System.out.println("Spaces in database");
        System.out.println("---------------------------------------");
        for (var space : spaces) {
            System.out.println(space);
        }
        System.out.println("---------------------------------------");
    }
}
