package com.example.ProfileService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProfileServiceApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MockRestServiceServer server;
	
	@Test
	public void getProfilesShouldReturnEmptyArray() throws Exception {
		this.mockMvc.perform(get("/PS/profiles")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("[]"));
	}
	
	@Test
	public void putProfileShouldSucceed() throws Exception {
		Profile profile = new Profile(1, "Remi", "test@example.com", "ok");
			ObjectMapper objectMapper = new ObjectMapper();
		String profile_json = objectMapper.writeValueAsString(profile);
		
		this.mockMvc.perform(put("/PS/profiles").content(profile_json).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void sameEmailShouldReturnError() throws Exception {
		Profile profile1 = new Profile(1, "Remi", "test@example.com", "c'est cool");
		Profile profile2 = new Profile(2, "Dylan", "test@example.com", "ok");
			ObjectMapper objectMapper = new ObjectMapper();
		String profile_json1 = objectMapper.writeValueAsString(profile1);
		String profile_json2 = objectMapper.writeValueAsString(profile2);
		this.mockMvc.perform(put("/PS/profiles").content(profile_json1).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(put("/PS/profiles").content(profile_json2).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isConflict());
	}
	
	@Test
	public void profileShouldNotBeNull() throws Exception {
		String profile_json = "{\"id\" : \"1\"}";
		this.mockMvc.perform(put("/PS/profiles").content(profile_json).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
	}
	
		
	/*@Test
	public void
	putNameShouldChangeName() throws Exception {
		Profile profile = new Profile(1, "Remi", "test@example.com", "ok");
		String new_name = "Dylan";
		Profile profile2 = new Profile(1, "Dylan", "test@example.com", "ok");
			ObjectMapper objectMapper = new ObjectMapper();
		String profile_json = objectMapper.writeValueAsString(profile);
		String profile_json2 = objectMapper.writeValueAsString(profile2);
		this.mockMvc.perform(put("/PS/profiles").content(profile_json).contentType(MediaType.APPLICATION_JSON));
		this.mockMvc.perform(put("/PS/profiles/1/name").content(new_name).contentType(MediaType.TEXT_PLAIN));
		this.mockMvc.perform(put("/PS/profiles/1/name").andExpect(status().isOk()).andExpect(content().string)
	}
*/
}
