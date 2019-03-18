package com.example.demo.util;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JWTUtils {
	private static String LOGIN_HMAC256_SECRET = "9njS4qox2TYBbo3EeKDrsD32Grhu8mHM";
	public String getToken(String userId) {
		String token = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(LOGIN_HMAC256_SECRET);
			token = JWT.create().withClaim("key", userId).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			// UTF-8 encoding not supported
			log.error("getToken() UnsupportedEncodingException msg: " , e);
		} catch (JWTCreationException e) {
			// Invalid Signing configuration / Couldn't convert Claims.
			log.error("getToken() JWTCreationException msg: ", e);
		} catch (Exception e) {
			log.error("getToken() Exception: " + e.getClass().getName() + ", msg: " + e.getMessage(), e);
		}
		return token;
	}
	
	public String getUserId(String token) {
		String key = null;
		try {
			if (null != token) {
				if (token.endsWith("_APP")) {
					token = token.substring(0, token.length() - 4);
				}
			    DecodedJWT jwt = JWT.decode(token);
			    key = jwt.getClaim("key").asString();
			    
			    Algorithm algorithm = Algorithm.HMAC256(LOGIN_HMAC256_SECRET);
				JWTVerifier verifier = JWT.require(algorithm).withClaim("key", key).build(); // Reusable verifier instance
				verifier.verify(token);
			}
		} catch (JWTDecodeException e) {
		    //Invalid token
			log.error("isValidToken() JWTDecodeException msg: " + e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			log.error("isValidToken() IllegalArgumentException msg: " + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			log.error("isValidToken() UnsupportedEncodingException msg: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error("isValidToken() Exception: " + e.getClass().getName() + ", msg: " + e.getMessage(), e);
		}
		return key;
	}
}
