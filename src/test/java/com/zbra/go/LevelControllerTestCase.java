package com.zbra.go;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelControllerTestCase {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Test
	public void testUploadImage() throws Exception {
        final String playerKey = "P1";
        final File file = new File("resources/image-test.png");
        final byte[] fileData = FileUtils.readFileToByteArray(file);
        final MockMultipartFile mockMultipartFile = new MockMultipartFile("file", file.getName(), "image/png", fileData);
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(
                MockMvcRequestBuilders.fileUpload("/image").file(mockMultipartFile).header("Player-Key", playerKey))
                .andExpect(status().is(HttpStatus.OK.value()));
	}

}
