package com.zbra.go.controller;

import com.zbra.go.controller.dto.ImageFileConverter;
import com.zbra.go.controller.dto.ImageFileDTO;
import com.zbra.go.controller.util.ImageUrlFactory;
import com.zbra.go.controller.util.RequestUtils;
import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;
import com.zbra.go.model.Player;
import com.zbra.go.service.ImageService;
import com.zbra.go.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
public class LevelController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ImageFileDTO uploadImage(
        @RequestParam("file") MultipartFile file,
        HttpServletRequest request) throws IOException {

        final String playerKey = RequestUtils.getPlayerKey(request);
        if (playerKey == null || playerKey.isEmpty()) {
            throw new IllegalArgumentException("Request header [Player-Key] is missing");
        }

        final Optional<Player> playerMaybe = playerService.findByKey(playerKey);
        if (!playerMaybe.isPresent()) {
            throw new IllegalArgumentException(String.format("Could not find player by key [%s]", playerKey));
        }

        final Image newImage = new Image();
        newImage.setInputStream(file.getInputStream());
        newImage.setName(file.getName());
        newImage.setOwner(playerMaybe.get());

        ImageFile imageFile = imageService.store(newImage);

        return ImageFileConverter.toImageFile(imageFile, ImageUrlFactory.newImageUrlFactory(request));
    }

}
