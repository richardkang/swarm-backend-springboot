package com.example.demo.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CORSFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		
		Enumeration<String> headers = req.getHeaderNames();
		while(headers.hasMoreElements()) {
			String key = headers.nextElement();
			log.info("### CORSFilter header: {}, {}", key, req.getHeader(key));
		}

		String path = req.getServletPath();
		log.info("doFilterInternal() for adding CORS Headers. path: " + path);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, PATCH, PUT, OPTIONS, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		res.setHeader("Access-Control-Max-Age", "3600");
		chain.doFilter(req, res);
	}

}
