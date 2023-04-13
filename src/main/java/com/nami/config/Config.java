package com.nami.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;

public record Config(WindowConfig window, UserConfig user, ControlConfig controls) {

    public static Config load(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(Paths.get(path).toFile(), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
