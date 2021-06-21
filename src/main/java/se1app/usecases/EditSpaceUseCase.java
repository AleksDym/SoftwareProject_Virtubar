package se1app.usecases;


import se1app.datatypes.ImageType;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.repositories.RoomRepository;
import se1app.repositories.SpaceRepository;


public class EditSpaceUseCase {


    /**
     * Creates a new room in a given space. The Background picture must be given.
     * @param imagePath The Path to the Background picture, must be a picture file.
     * @param spaceId The Id of the space where the new room will be created.
     * @return True if the operation was successfull, false if not.
     */


    public static boolean addRoom(String imagePath, int spaceId){
        ImageType image = new ImageType(imagePath);
        Space space = SpaceRepository.getSpaceById(spaceId);
        Room room  = RoomRepository.createRoom(image, space);
        System.out.println("der Raum den ich hinuzufügte ist" + room);
        if(imagePath!=null && space!=null){
            SpaceRepository.addRoom(room, spaceId);
            return true;
        }
        else return false;

    }

    /**
     * Opens a given space to the customers.
     * @param spaceId The Id of the space that will be opened
     * @return True if the operation was successfull, false if not.
     */
    public static boolean openSpace(int spaceId) {
        Space space = SpaceRepository.getSpaceById(spaceId);
        if (space != null) {
            space.setOpened(true);
            SpaceRepository.saveSpace(space);
            return true;
        } else return false;
    }

    /**
     * Closes a given space for the customers.
     * @param spaceId The Id of the space that will be closed
     * @return True if the operation was successfull, false if not.
     */
    public static boolean closeSpace(int spaceId) {
        Space space = SpaceRepository.getSpaceById(spaceId);
        if (space != null) {
            space.setOpened(false);
            SpaceRepository.saveSpace(space);
            return true;
        } else return false;
    }

    /**
     * Sets an entry fee for a given space.
     * @param spaceId The ID of the space where the entry fee will be set.
     * @param fee The amount of money that will be charged (set to 0 if no entry fee)
     * @return True if the operation was successfull, false if not.
     */
    public static boolean setEntryFee(int spaceId, int fee) {
        Space space = SpaceRepository.getSpaceById(spaceId);
        if (space != null) {
            space.setEntryFee(fee);
            SpaceRepository.saveSpace(space);
            return true;
        }
        else return false;
    }

    /**
     * The function is enabling a face control restriction in a given space. After the facecontrol is enabled, any user wil be proved by the moderator of the space
     * @param spaceId -  the id of the space where the new room is beeing created
     * @return - false, which means that the face control is switched off
     */
    public static boolean enableFaceControl(int spaceId) {
        Space space = SpaceRepository.getSpaceById(spaceId);
        if (space != null) {
            space.setFaceControl(true);
            SpaceRepository.saveSpace(space);
            return true;
        } else return false;
    }

    /**
     * The function is disabling a face control restriction in a given space. After the facecontrol is disabled, any user can enter the space
     * @param spaceId -  the id of the space where the new room is beeing created
     * @return - false, which means that the face control is switched off
     */
    public static boolean disableFaceControl(int spaceId) {
        Space space = SpaceRepository.getSpaceById(spaceId);
        if (space != null) {
            space.setFaceControl(false);
            SpaceRepository.saveSpace(space);
            return true;
        } else return false;
    }


}
