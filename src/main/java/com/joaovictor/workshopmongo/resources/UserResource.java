package com.joaovictor.workshopmongo.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaovictor.workshopmongo.domain.User;
import com.joaovictor.workshopmongo.domain.dto.UserDTO;
import com.joaovictor.workshopmongo.service.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = new ArrayList<>();
		
		list.addAll(this.userService.findAll());
		
		List<UserDTO> listDTO = list.stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User user = this.userService.findById(id);
		
		UserDTO userDTO = new UserDTO(user);
		
		return ResponseEntity.ok().body(userDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
		User user = this.userService.fromDTO(userDTO);
		user = this.userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.userService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
