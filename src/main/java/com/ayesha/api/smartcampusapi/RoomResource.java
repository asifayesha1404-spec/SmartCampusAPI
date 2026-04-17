package com.ayesha.api.smartcampusapi;

import com.ayesha.api.smartcampusapi.Sensor;
import com.ayesha.api.smartcampusapi.SensorResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
public class RoomResource {

    // ✅ In-memory storage
    private static List<Room> rooms = new ArrayList<>();

    // ✅ Initial data
    static {
        rooms.add(new Room(1, "Lecture Hall A", 100));
        rooms.add(new Room(2, "Computer Lab 1", 50));
        rooms.add(new Room(3, "Seminar Room", 30));
    }

    // ✅ GET all rooms
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return rooms;
    }

    // ✅ GET room by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("id") int id) {

        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }

        throw new NotFoundException("Room not found");
    }

    // ✅ POST (create new room with validation)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room createRoom(Room room) {

        if (room.getName() == null || room.getName().isEmpty()) {
            throw new BadRequestException("Room name is required");
        }

        if (room.getCapacity() <= 0) {
            throw new BadRequestException("Capacity must be greater than 0");
        }

        rooms.add(room);
        return room;
    }

    // 🚨 UPDATED DELETE room with business rule
    @DELETE
    @Path("/{id}")
    public void deleteRoom(@PathParam("id") int id) {

        Room roomToRemove = null;

        // find room
        for (Room room : rooms) {
            if (room.getId() == id) {
                roomToRemove = room;
                break;
            }
        }

        if (roomToRemove == null) {
            throw new NotFoundException("Room not found");
        }

        // 🚨 CHECK: does this room have sensors?
        for (Sensor sensor : SensorResource.getSensorsStatic()) {
            if (sensor.getRoomId() == id) {
                throw new RoomNotEmptyException("Room has sensors and cannot be deleted");
            }
        }

        // delete if safe
        rooms.remove(roomToRemove);
    }

    public static List<Room> getRoomsStatic() {
        return rooms;
    }
}