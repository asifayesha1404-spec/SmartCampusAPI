package com.ayesha.api.smartcampusapi;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class SensorReadingResource {

    private Sensor sensor;

    public SensorReadingResource(Sensor sensor) {
        this.sensor = sensor;
    }

    // ✅ GET all readings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Double> getReadings() {
        return sensor.getReadings();
    }

    // ✅ POST new reading (FIXED VERSION)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(Double value) {

        // 🚨 403 check (maintenance)
        if (sensor.getStatus() != null &&
            sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {

            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"Sensor is under maintenance\"}")
                    .build();
        }

        // 🚨 validation
        if (value == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Reading value is required\"}")
                    .build();
        }

        // ✅ add reading
        sensor.getReadings().add(value);

        // ✅ update current value
        sensor.setCurrentValue(value);

        return Response.ok().build();
    }
}