package com.zbra.go.model;

import org.apache.commons.io.FilenameUtils;

import java.io.InputStream;
import java.util.UUID;

public class Image {

    private final String id;

    private String name;
    private InputStream inputStream;
    private Player owner;
    private GeoLocation location;

    public Image() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFilename() {
        return FilenameUtils.getName(name);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }
}
