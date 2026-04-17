package com.ayesha.api.smartcampusapi;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

public class SmartCampusAPI {

    public static final String BASE_URI = "http://localhost:8081/";

    public static HttpServer startServer() {

        // ✅ AUTO-SCAN ALL RESOURCES + MAPPERS
        ResourceConfig config = new ResourceConfig()
                .packages("com.ayesha.api.smartcampusapi")
                .register(JacksonFeature.class);

        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI), config
        );
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI);

        System.in.read(); // keeps server running
        server.shutdownNow();
    }
}