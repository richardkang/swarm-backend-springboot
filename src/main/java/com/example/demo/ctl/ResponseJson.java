package com.example.demo.ctl;

import lombok.Data;

@Data
public class ResponseJson {
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String token;
}
