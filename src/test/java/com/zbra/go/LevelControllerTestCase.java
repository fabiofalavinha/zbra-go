package com.zbra.go;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zbra.go.controller.dto.ImageFileDTO;
import com.zbra.go.controller.dto.PlayerDTO;
import com.zbra.go.controller.dto.TeamDTO;
import com.zbra.go.controller.dto.TeamEnrollmentRequest;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelControllerTestCase {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
    private MockMultipartFile mockMultipartFile;
    private MockMultipartHttpServletRequestBuilder requestBuilder;

    @Before
    public void before() throws IOException {
        final File file = new File("resources/image-test.png");
        final byte[] fileData = FileUtils.readFileToByteArray(file);
        mockMultipartFile = new MockMultipartFile("file", file.getName(), "image/png", fileData);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        requestBuilder = MockMvcRequestBuilders.fileUpload("/image");
    }

    @Test
    public void testUploadImageWithoutPlayer() throws Exception {
        mockMvc.perform(
                requestBuilder.file(mockMultipartFile))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

	@Test
	public void testUploadImageWithUnknownPlayer() throws Exception {
        final String playerKey = "P1";
        mockMvc.perform(
                requestBuilder.file(mockMultipartFile).header("Player-Key", playerKey))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

    @Test
    public void testUploadImageWithoutFile() throws Exception {
        final TeamDTO team = addTestTeam();
        mockMvc.perform(
                requestBuilder.header("Player-Key", team.getPlayers().iterator().next().getKey()))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void testUploadDownloadImage() throws Exception {
        final TeamDTO team = addTestTeam();
        final String playerKey = team.getPlayers().iterator().next().getKey();

        final MvcResult result = mockMvc.perform(
                requestBuilder.file(mockMultipartFile).header("Player-Key", playerKey))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Assert.isTrue(json.length() > 0);
        new JsonContentAssert(ImageFileDTO.class, json).hasJsonPathValue("mediaId");

        ImageFileDTO returnedImageFileDIO = new ObjectMapper().readValue(json, ImageFileDTO.class);
        String mediaId = returnedImageFileDIO.getMediaId();
        Assert.hasText(mediaId);

        json = mockMvc.perform(
                MockMvcRequestBuilders.get("/image/" + mediaId))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

        Assert.isTrue(json.length() > 0);
        ImageFileDTO imageFileDIO = new ObjectMapper().readValue(json, ImageFileDTO.class);
        Assert.isTrue(imageFileDIO.getMediaId().equals(returnedImageFileDIO.getMediaId()));
        Assert.isTrue(imageFileDIO.getThumbnailUrl().equals(returnedImageFileDIO.getThumbnailUrl()));
        Assert.isTrue(imageFileDIO.getUrl().equals(returnedImageFileDIO.getUrl()));
    }

	private TeamDTO addTestTeam() throws Exception {
        List<PlayerDTO> players = new ArrayList<>();
        PlayerDTO player1 = new PlayerDTO();
        player1.setKey(UUID.randomUUID().toString());
        player1.setName("TestPlayer001");
        players.add(player1);

        List<TeamDTO> teams = new ArrayList<>();

        TeamDTO team = new TeamDTO();
        team.setKey(UUID.randomUUID().toString());
        team.setName("TestTeam001");
        team.setPlayers(players);
        teams.add(team);

        TeamEnrollmentRequest request = new TeamEnrollmentRequest();
        request.setTeams(teams);

        ObjectMapper o = new ObjectMapper();
        String json = o.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/teams").contentType("application/json").content(json)).andExpect(status().is(HttpStatus.OK.value()));

        return team;
    }
}
