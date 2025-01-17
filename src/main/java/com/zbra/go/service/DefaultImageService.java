package com.zbra.go.service;

import com.zbra.go.model.*;
import com.zbra.go.persistence.ImageFileRepository;
import com.zbra.go.service.imaging.ImageFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
class DefaultImageService implements ImageService {

    @Autowired
    private ImageFileProcessor imageFileProcessor;

    @Autowired
    private ImageFileRepository imageFileRepository;

    @Autowired
    private StorageSettings storageSettings;

    @Autowired
    private GameEngineService gameEngineService;

    @PostConstruct
    public void setupDirectory() throws FileSystemException, FileNotFoundException {
        File imagePath = Paths.get(storageSettings.getBasePath(), storageSettings.getImagePath()).toAbsolutePath().toFile();
        if (!imagePath.exists()) {
            boolean created = imagePath.mkdirs();
            if (!created) {
                throw new FileSystemException(String.format("Can't create images path directory [%s]. Are we have permission to create directory? Check server path permissions.", imagePath.toString()));
            }
        } else {
            if (!imagePath.isDirectory()) {
                throw new FileNotFoundException(String.format("Images path isn't a directory [%s]. Check 'storage' settings into application properties file.", imagePath.toString()));
            }
        }
    }

    @Transactional
    @Override
    public ImageFile store(Image image) {
        try {
            ImageFile imageFile = imageFileProcessor.process(image);
            imageFile.setOwner(image.getOwner());
            imageFile.setLocation(image.getLocation());

            Team team = image.getOwner().getTeam();
            Optional<GameSession> gameSessionTeamMaybe = gameEngineService.findGameSessionByTeam(team);
            if (!gameSessionTeamMaybe.isPresent()) {
                throw new IllegalStateException(String.format("Game session for team [%s] wasn't start yet", team.getName()));
            }

            imageFile.setLevelType(gameSessionTeamMaybe.get().getCurrentLevelType());
            imageFileRepository.save(imageFile);
            return imageFile;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error processing image [%s]", image.getName()));
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<ImageFile> findByMediaId(String mediaId) {
        return Optional.ofNullable(imageFileRepository.findByMediaId(mediaId));
    }
}
