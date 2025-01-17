package com.zbra.go.controller;

import com.zbra.go.controller.dto.ImageFileConverter;
import com.zbra.go.controller.dto.ImageFileDTO;
import com.zbra.go.controller.util.ImageUrlFactory;
import com.zbra.go.controller.util.JsonUtils;
import com.zbra.go.controller.util.RequestUtils;
import com.zbra.go.model.GeoLocation;
import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;
import com.zbra.go.model.Player;
import com.zbra.go.service.ImageService;
import com.zbra.go.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
public class LevelController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageUrlFactory imageUrlFactory;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ImageFileDTO uploadImage(
        @RequestParam("file") MultipartFile file,
        @RequestParam("location") String locationJson,
        HttpServletRequest request) throws IOException {

        final String playerKey = RequestUtils.getPlayerKey(request);
        if (playerKey == null || playerKey.isEmpty()) {
            throw new IllegalArgumentException("Request header [Player-Key] is missing");
        }

        if (locationJson == null || locationJson.isEmpty()) {
            throw new IllegalArgumentException("Geo location is required");
        }

        final GeoLocation location = JsonUtils.fromJson(locationJson, GeoLocation.class);

        final Optional<Player> playerMaybe = playerService.findByKey(playerKey);
        if (!playerMaybe.isPresent()) {
            throw new IllegalArgumentException(String.format("Could not find player by key [%s]", playerKey));
        }

        final Image newImage = new Image();
        newImage.setInputStream(file.getInputStream());
        newImage.setName(UUID.randomUUID().toString());
        newImage.setOwner(playerMaybe.get());
        newImage.setLocation(location);

        ImageFile imageFile = imageService.store(newImage);

        return ImageFileConverter.toImageFile(imageFile, imageUrlFactory, request);
    }

    @RequestMapping(value = "/image/{mediaId}", method = RequestMethod.GET)
    public ImageFileDTO getImageFile(@PathVariable("mediaId") String mediaId, HttpServletRequest request) {
        final Optional<ImageFile> imageFile = imageService.findByMediaId(mediaId);
        return imageFile.isPresent()
            ? ImageFileConverter.toImageFile(imageFile.get(), imageUrlFactory, request)
            : new ImageFileDTO();
    }
}
