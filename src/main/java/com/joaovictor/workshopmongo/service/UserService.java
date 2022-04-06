package com.joaovictor.workshopmongo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaovictor.workshopmongo.domain.User;
import com.joaovictor.workshopmongo.domain.dto.UserDTO;
import com.joaovictor.workshopmongo.repository.UserRepository;
import com.joaovictor.workshopmongo.service.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return this.userRepository.findAll();
	}
	
	public User findById(String id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User user) {
		return this.userRepository.insert(user);
	}
	
	public void deleteById(String id) {
		findById(id);
		this.userRepository.deleteById(id);
		
	}
	
	public User fromDTO(UserDTO userDTO) {
		return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
	}
 
}
