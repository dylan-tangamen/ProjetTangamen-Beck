package com.example.ProfileService;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProfileController {
	
	private final AtomicLong counter = new AtomicLong();
	private final Map<Long, Profile> profiles = new HashMap<>();
	private final Set<String> emails = new HashSet<>();
	
	private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@GetMapping("/PS/profiles")
	@CrossOrigin
	public Collection<Profile>profiles(){
		logger.trace("GET /PS/profiles");
		return profiles.values();
	}
	
	//GET /PS/profiles/{id}
	//GET /PS/profiles/{id}/name
	

	@PutMapping("/PS/profiles")
	@CrossOrigin
	public Profile profiles_put(@RequestBody @Valid Profile profile) {
		logger.trace("PUT /PS/profiles");
		if (emails.contains(profile.getEmail()))
			throw new EmailInUseException(profile.getEmail());
		String email = profile.getEmail();
		long new_id = counter.incrementAndGet();
		profile.setId(new_id);
		
		AuthServiceUser auth_service_user = new AuthServiceUser(new_id);
		RestTemplate restTemplate = new RestTemplate();
		Long check_id = restTemplate.postForObject(auth_service_url + "/AS/users", auth_service_user, Long.class);
		logger.info(String.format("Auth service created for %d: [%d].", new_id, check_id));
        if (!check_id.equals(new_id))
			throw new RuntimeException();
		
		profiles.put(new_id, profile);
		emails.add(email);
		logger.info(String.format("Profile created : [%d] %s.", new_id, profile.getEmail()));
		return profile;
	}
	
	@DeleteMapping("/PS/profiles/{id}")
	@CrossOrigin
	public void profiles_delete(@PathVariable(value = "id") Long id, @RequestHeader(value = "X-Token") String token) {
		logger.trace("DELETE /PS/profiles/{id}");
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.add("X-Token", token);
		HttpEntity<String> entity = new HttpEntity<String>("",header);
		ResponseEntity<Long> response = restTemplate.exchange(auth_service_url + "/AS/token", HttpMethod.GET, entity, Long.class);
		Long token_user = response.getBody();
		if (!Objects.equals(token_user, id))
			throw new RuntimeException();
		
		Profile profile = profiles.get(id);
		emails.remove(profile.getEmail());
		profiles.remove(id);
		logger.info(String.format("Profile deleted : [%d] %s.", profile.getId(),profile.getName(), profile.getEmail()));
	}
	
	//PUT /PS/profiles/{id}/name
	@PutMapping("/PS/profiles/{id}/name")
	@CrossOrigin
	public void update_name(@PathVariable(value="id")Long id, @RequestBody @Valid String name) {
		logger.trace("PUT /PS/profiles/{id}/name");
		Profile profile = profiles.get(id);
		profile.setName(name);
	}
	
	@GetMapping("/PS/profiles/{id}/name")
	@CrossOrigin
	public String profile_get_name(@PathVariable(value = "id") Long id){
		logger.trace("GET /PS/profiles/{id}/name");
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getName();
	}
	
	@GetMapping("/PS/profiles/{id}/email")
	@CrossOrigin
	public String profile_get_email(@PathVariable(value = "id") Long id){
		logger.trace("GET /PS/profiles/{id}/email");
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getEmail();
	}
	
	@GetMapping("/PS/profiles/{id}/description")
	@CrossOrigin
	public String profile_get_description(@PathVariable(value = "id") Long id){
		logger.trace("GET /PS/profiles/{id}/description");
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getDescription();
	}
	
	@Value("${service.authentification}")
	private String auth_service_url;
}
