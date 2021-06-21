package se1app.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import se1app.entities.Room;
import se1app.entities.Space;
import se1app.repositories.RoomRepository;
import se1app.repositories.SpaceRepository;
import se1app.usecases.EditSpaceUseCase;


public class SpaceController {

    public SpaceController(Javalin server) {
        server.get("/api/spaces", SpaceController::getAllSpaces);
        server.get("/api/spaces/:space-id", SpaceController::getSpaceById);
        server.delete("/api/spaces/delete/:space-id", SpaceController::deleteSpaceById);
        server.post("/api/spaces/create_room/", SpaceController::createRoom);
        server.get("/api/spaces/show_rooms/:space-id", SpaceController::getRooms);
        server.put("/api/spaces/close_space/:space-id", SpaceController::closeSpace);
        server.put("/api/spaces/open_space/:space-id", SpaceController::openSpace);
        server.put("/api/spaces/enable_face_control/:space-id", SpaceController::enableFaceControl);
        server.put("/api/spaces/disable_face_control/:space-id", SpaceController::disableFaceControl);
        server.put("/api/spaces/set_entry_fee/:space-id", SpaceController::setEntryFee);
    }

    private static void setEntryFee(Context ctx) throws IOException {
        var space = fetchSpace(ctx, "openSpace");
        assert space != null;
        int spaceId = space.getSpaceId();
        try {
            var jsonNode = new ObjectMapper().readTree(ctx.body());
            int fee = jsonNode.get("fee").asInt();
            EditSpaceUseCase.setEntryFee(spaceId, fee);
            ctx.res.setStatus(201);
        } catch (JsonProcessingException ex) {
            var msg = "JSON parser exception: " + ex;
            System.err.println("[SpaceController] setEntryFee: " + msg);
            ctx.res.sendError(400, msg);
        }
    }

    private static void openSpace(Context ctx) {
        var space = fetchSpace(ctx, "openSpace");
        assert space != null;
        int spaceId = space.getSpaceId();
        EditSpaceUseCase.openSpace(spaceId);
        ctx.res.setStatus(201);
    }

    private static void closeSpace(Context ctx) {
        var space = fetchSpace(ctx, "closeSpace");
        assert space != null;
        int spaceId = space.getSpaceId();
        EditSpaceUseCase.closeSpace(spaceId);
        ctx.res.setStatus(201);
    }

    private static void enableFaceControl(Context ctx) {
        var space = fetchSpace(ctx, "enableFacecontrol");
        assert space != null;
        int spaceId = space.getSpaceId();
        EditSpaceUseCase.enableFaceControl(spaceId);
        ctx.res.setStatus(201);
    }

    private static void disableFaceControl(Context ctx) {
        var space = fetchSpace(ctx, "disableFacecontrol");
        assert space != null;
        int spaceId = space.getSpaceId();
        EditSpaceUseCase.disableFaceControl(spaceId);
        ctx.res.setStatus(201);
    }

    private static void getAllSpaces(Context ctx) {
        var spaceList = SpaceRepository.getAllSpaces();
        ctx.json(spaceList);
        ctx.res.setStatus(200);
    }

    private static void getSpaceById(Context ctx) {
        var space = fetchSpace(ctx, "getSpace");
        if (space != null) {
            ctx.json(space);
        }
    }

    private static void getRooms(Context ctx) {
        var space = fetchSpace(ctx, "getRooms");
        if (space != null) {
            var roomList = space.getRooms();
            ctx.json(roomList);
        }
    }

    private static void deleteSpaceById(Context ctx) {
        var space = fetchSpace(ctx, "deleteSpace");
        var rooms = space.getRooms();
        if (rooms != null) {
            for (Room room : rooms) {
                RoomRepository.deleteRoomById(room.getRoomId());
            }
            SpaceRepository.deleteSpaceById(space.getSpaceId());
        }
    }

    private static void createRoom(Context ctx) throws IOException {
        try {
            var jsonNode = new ObjectMapper().readTree(ctx.body());
            int spaceId = jsonNode.get("space_id").asInt();
            String imagePath = jsonNode.get("image").asText();
            boolean created = EditSpaceUseCase.addRoom(imagePath, spaceId);
            if (created) ctx.res.setStatus(201); // 201 - Created (POST success)
            else ctx.res.setStatus(500);
        } catch (JsonProcessingException ex) {
            var msg = "JSON parser exception: " + ex;
            System.err.println("[SpaceController] createRoom: " + msg);
            ctx.res.sendError(400, msg);
        }

    }

    private static Space fetchSpace(Context ctx, String endpointDesc) {
        try {
            try {
                var spaceId = Integer.parseInt(ctx.pathParam("space-id"));
                var space = SpaceRepository.getSpaceById(spaceId);
                if (space != null) return space;
                else {
                    var msg = "Space #" + spaceId + " not found!";
                    if (endpointDesc != null) System.err.println("[SpaceController] " + endpointDesc + ": " + msg);
                    ctx.res.sendError(404, msg);
                }
            } catch (NumberFormatException ex) {
                var msg = "Failed to parse space identifier!";
                if (endpointDesc != null) System.err.println("[SpaceController] " + endpointDesc + ": " + msg);
                ctx.res.sendError(400, msg);
            }
        } catch (IOException ex) {
            System.err.println("[SpaceController] fetchCustomer: IOException: " + ex);
        }
        return null;
    }


}
