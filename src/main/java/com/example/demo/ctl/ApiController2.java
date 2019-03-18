package com.example.demo.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class ApiController2 {

	@Autowired
	UserService userService;
	
	@PostMapping(value = "/authenticate")
	@ApiOperation(value = "authenticate")
	@ApiResponses(value = {
			@ApiResponse(code=200,message="Success"),
			@ApiResponse(code=401,message="UNAUTHORIZED"),
			@ApiResponse(code=503,message="System exception")
	})
	public ResponseEntity<Object> authenticate(
			@ApiParam(name = "username", value = "username", defaultValue = "")
			@RequestParam(value = "username") String username,
			@ApiParam(name = "password", value = "password", defaultValue = "")
			@RequestParam(value = "password") String password
			) {
		try {
			log.info("authenticate. username:[{}], password:[{}]",
					username,password);
			User entity = userService.getUser(username);
			log.info("authenticate entity : {}",entity);

			ResponseJson resp = new ResponseJson();
			resp.setId(entity.getId());
			resp.setUsername(entity.getUsername());
			resp.setFirstName(entity.getFirstName());
			resp.setLastName(entity.getLastName());
			resp.setToken("fake-jwt-token");
			log.info("authenticate finish : {}", "Success");
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
}
