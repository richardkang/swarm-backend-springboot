package com.example.demo.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service 
public class UserService {
	
	private static Map<String,User> users = new HashMap<String,User>();
	private static Map<Integer,String> mapping = new HashMap<Integer,String>();
	public static int id = 1;
	static {
		User u = new User();
		u.setId(id);
		u.setFirstName("test");
		u.setLastName("test");
		u.setUsername("test");
		u.setPassword("1234");
		users.put(u.getUsername(), u);
		mapping.put(id, "test");
		
	}
	public User addUser(User entity) {
		entity.setId(id++);
		users.put(entity.getUsername(), entity);
		mapping.put(entity.getId(), entity.getUsername());
		return entity;
	}
	
	public User getUser(String username) {
		return users.get(username);
	}
	
	public int getId() {
		return id++;
	}
	
	public List<User> getAll() {
		List<User> list = new ArrayList<User>(users.values());
		return list;
		
	}
	
	public String getUsername(int id) {
		return mapping.get(id);
	}
	
	public void deleteUser(int id) {
		
		String username =  mapping.get(id);
		if (StringUtils.isEmpty(username)) {
			return;
		}
		mapping.remove(id);
		users.remove(username);
	}
}
