package com.exam.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestToken = request.getHeader("Authorization"); 
		System.out.println(requestToken);
		String username = null;
		String jwtToken = null;

		if (requestToken != null && requestToken.startsWith("Bearer ")) {

			jwtToken = requestToken.substring(7);
			try {
				username = 	jwtUtil.extractUsername(jwtToken);

			} catch (ExpiredJwtException ex) {
				System.out.println("ExpiredJwtException : " + ex);
			} catch (Exception ex) {
				System.out.println("Exception : " + ex);
			}

		} else {
			System.out.println("INVALID TOKEN");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
			
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());	
			upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));			
				SecurityContextHolder.getContext().setAuthentication(upat);
			}
		}else {
			System.out.println("Token is not Valid ! ");
		}
		
		filterChain.doFilter(request, response);
	}

}
