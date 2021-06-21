package se1app.repositories;
import java.util.List;

import se1app.datatypes.ImageType;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.exceptions.InvalidImageException;
import se1app.persistency.H2Database;


public class RoomRepository {

    public static Room createRoom(ImageType _wallCover, Space _space) {
        try {
            var room = new Room(_wallCover,_space);
            saveRoom(room);
            return room;
        } catch (InvalidImageException ex) {
            System.err.println("Customer creation failed: \n -> " + ex);
            return null;
        }
    }


    public static void saveRoom(Room room) {
        var session = H2Database.getInstance().getSession();
        var transaction = session.beginTransaction();
        session.save(room);
        transaction.commit();
    }

    public static List<Room> getAllRooms() {
        var session = H2Database.getInstance().getSession();
        var rooms = session.createQuery("FROM Room", Room.class).getResultList();
        return rooms;
    }


    /**
     * Get a room by its identifier.
     *
     * @param roomId Customer identifier.
     * @return The customer or 'null' if not found.
     */
    public static Room getRoomById(int roomId) {
        var session = H2Database.getInstance().getSession();
        var room = session.get(Room.class, roomId);
        return room;
    }


    /**
     * Delete a room from the database.
     *
     * @param roomId Customer identifier.
     */
    public static void deleteRoomById(int roomId) {
        var session = H2Database.getInstance().getSession();
        var room = session.get(Room.class, roomId);
        var transaction = session.beginTransaction();
        session.delete(room);
        transaction.commit();
    }


    /**
     * Print a table of all customers to the console. This
     * method is not really a repository function, it's more
     * intended to be a helper during debugging and development.
     */
    public static void printRoomTable() {
        var rooms = getAllRooms();
        System.out.println("Rooms in database");
        System.out.println("---------------------------------------");
        for (var room : rooms) {
            System.out.println(rooms);
        }
        System.out.println("---------------------------------------");
    }


}
