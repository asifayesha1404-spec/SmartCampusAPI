package com.ayesha.api.smartcampusapi;

import com.ayesha.api.smartcampusapi.Room;
import com.ayesha.api.smartcampusapi.RoomResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/sensors")
public class SensorResource {

    private static List<Sensor> sensors = new ArrayList<>();

    // ✅ GET all sensors (with optional filtering)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensors;
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor sensor : sensors) {
            if (sensor.getType().equalsIgnoreCase(type)) {
                filtered.add(sensor);
            }
        }

        return filtered;
    }

    // ✅ POST create sensor with validation
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor createSensor(Sensor sensor) {

        if (sensor.getType() == null || sensor.getType().isEmpty()) {
            throw new BadRequestException("Sensor type is required");
        }

        // ✅ CHECK if room exists
        boolean roomExists = false;

        for (Room room : RoomResource.getRoomsStatic()) {
            if (room.getId() == sensor.getRoomId()) {
                roomExists = true;
                break;
            }
        }

        // 🚨 UPDATED → 422 instead of 400
        if (!roomExists) {
            throw new LinkedResourceNotFoundException("Room does not exist");
        }

        sensors.add(sensor);
        return sensor;
    }

    // ✅ SUB-RESOURCE for readings
    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") int id) {

        for (Sensor sensor : sensors) {
            if (sensor.getId() == id) {
                return new SensorReadingResource(sensor);
            }
        }

        throw new NotFoundException("Sensor not found");
    }

    // ✅ Used by RoomResource
    public static List<Sensor> getSensorsStatic() {
        return sensors;
    }
}