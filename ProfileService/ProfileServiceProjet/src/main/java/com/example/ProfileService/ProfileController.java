package com.example.ProfileService;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
	
	private final AtomicLong counter = new AtomicLong();
	private final Map<Long, Profile> profiles = new HashMap<>();
	private final Set<String> emails = new HashSet<>();
	
	@GetMapping("/PS/profiles")
	public Collection<Profile>profiles(){
		return profiles.values();
	}
	
	//GET /PS/profiles/{id}
	//GET /PS/profiles/{id}/name
	

	@PutMapping("/PS/profiles")
	public Profile profiles_put(@RequestBody @Valid Profile profile) {
		if (emails.contains(profile.getEmail()))
			throw new EmailInUseException(profile.getEmail());
		String email = profile.getEmail();
		long new_id = counter.incrementAndGet();
		profile.setId(new_id);
		profiles.put(new_id, profile);
		emails.add(email);
		return profile;
	}
	
	@DeleteMapping("/PS/profiles/{id}")
	public void profiles_delete(@PathVariable(value = "id") Long id) {
		Profile profile = profiles.get(id);
		emails.remove(profile.getEmail());
		profiles.remove(id);
	}
	
	//PUT /PS/profiles/{id}/name
	@PutMapping("/PS/profiles/{id}/name")
	public void update_name(@PathVariable(value="id")Long id, @RequestBody @Valid String name) {
		Profile profile = profiles.get(id);
		profile.setName(name);
	}
	
	@GetMapping("/PS/profiles/{id}/name")
	public String profile_get_name(@PathVariable(value = "id") Long id){
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getName();
	}
	
	@GetMapping("/PS/profiles/{id}/email")
	public String profile_get_email(@PathVariable(value = "id") Long id){
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getEmail();
	}
	
	@GetMapping("/PS/profiles/{id}/description")
	public String profile_get_description(@PathVariable(value = "id") Long id){
		if (!profiles.containsKey(id))
			throw new ProfileNotFoundException(id);
		return profiles.get(id).getDescription();
	}
}
