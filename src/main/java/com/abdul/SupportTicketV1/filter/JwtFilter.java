package com.abdul.SupportTicketV1.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abdul.SupportTicketV1.security.MyUserDetail;
import com.abdul.SupportTicketV1.service.JwtService;
import com.abdul.SupportTicketV1.service.MyUserDetailService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	@Autowired
	JwtService jwtService;
	@Autowired
	MyUserDetailService myUserDetailService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		String path = request.getServletPath();
		if(path.equals("/auth/signup")||path.equals("/auth/login"))
		{
			filterChain.doFilter(request, response);
			return;
		}
		String header = request.getHeader("Authorization");
		if(header==null || !header.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		token = header.substring(7);
		Claims claims = jwtService.verifyAndExtractClaim(token);
		if(!claims.getExpiration().before(new Date(System.currentTimeMillis()))
				&& SecurityContextHolder.getContext().getAuthentication() == null)
		{
			MyUserDetail userDetails = (MyUserDetail)myUserDetailService.loadUserByUsername(claims.getSubject());
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}

}
