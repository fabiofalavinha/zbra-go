package com.zbra.go.model;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ImageFile {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(nullable = false, unique = true)
    private String mediaId;

    @Column(nullable = false)
    private String path;

    @Column
    private String thumbnailPath;

    @Column(nullable = false)
    private Date createdOn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Player owner;

    @Column
    @Enumerated(value = EnumType.STRING)
    private LevelType levelType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GeoLocation location;

    public ImageFile() {
        id = UUID.randomUUID().toString();
        createdOn = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getExtension() {
        if (path == null || path.isEmpty()) {
            return null;
        }

        return FilenameUtils.getExtension(path);
    }

    public String getFilename() {
        return FilenameUtils.getName(path);
    }

    public String getThumbnailFilename() {
        return FilenameUtils.getName(thumbnailPath);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public GeoLocation getLocation() {
        return location;
    }
}
