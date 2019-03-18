package com.example.demo.ctl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class ApiController {

	@Autowired
	UserService userService;
	
	@PostMapping(value = "/users/authenticate")
	@ApiOperation(value = "authenticate")
	@ApiImplicitParams({
		//@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataType = "string"),
		@ApiImplicitParam(name = "request", value = "request", required = true, paramType = "body", dataType = "AuthenticateRequest") })
	@ApiResponses(value = {
			@ApiResponse(code=200,message="Success"),
			@ApiResponse(code=401,message="UNAUTHORIZED"),
			@ApiResponse(code=503,message="System exception")
	})
	public ResponseEntity<Object> authenticate(
			@RequestBody AuthenticateRequest request) {
		try {
			log.info("authenticate. username:[{}], password:[{}]",
					request.getUsername(),request.getPassword());
			User entity = userService.getUser(request.getUsername());
			log.info("authenticate entity : {}",entity);
			if (entity == null) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
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
	
	@PostMapping(value = "/users/register")
	@ApiOperation(value = "register")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user", value = "user", required = true, paramType = "body", dataType = "User") })
	@ApiResponses(value = {
			@ApiResponse(code=200,message="Success"),
			@ApiResponse(code=401,message="UNAUTHORIZED"),
			@ApiResponse(code=503,message="System exception")
	})
	public ResponseEntity<Object> register(
			@RequestBody User user) {
		try {
			log.info("register. user:{}",
					user);
			User result = userService.getUser(user.getUsername());

			if (result != null) {
				return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
			}
			userService.addUser(user);
			ResponseJson resp = new ResponseJson();
			resp.setId(user.getId());
			resp.setUsername(user.getUsername());
			resp.setFirstName(user.getFirstName());
			resp.setLastName(user.getLastName());
			resp.setToken("fake-jwt-token");
			log.info("authenticate finish : {}", "Success");
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	@GetMapping(value = "/users")
	@ResponseStatus(HttpStatus.OK)
	public  ResponseEntity<Object> getAll(
			@RequestHeader(value="Authorization") String authorization) {
		try {
			log.info("Authorization : {}",authorization);
			List<User> list = userService.getAll();
			log.info("list : {}",list);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@ApiOperation(value = "刪除資料", notes = "刪除資料")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataType = "string"),
			@ApiImplicitParam(name = "id", value = "dd", required = true, paramType = "path", dataType = "int") })
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteId(@RequestHeader(value = "Authorization") String authorization,
			@PathVariable("id") Integer id) {
		try {
			log.info("delete Id : [{}]",id);
			String username = userService.getUsername(id);
			if (StringUtils.isEmpty(username)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			userService.deleteUser(id);
			List<User> list = userService.getAll();
			log.info("list : {}",list);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

}
