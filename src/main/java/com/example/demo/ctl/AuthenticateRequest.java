package com.example.demo.ctl;

import lombok.Data;

@Data
public class AuthenticateRequest {
	private String username;
	private String password;
}
