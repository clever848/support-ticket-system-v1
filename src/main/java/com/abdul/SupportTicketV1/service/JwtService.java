package com.abdul.SupportTicketV1.service;

import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abdul.SupportTicketV1.dto.LoginRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	@Value("${jwt.secretkey}")
	private String jwtKeyString;
	public String generateToken(LoginRequest loginRequest)
	{
		return Jwts.builder()
				.subject(loginRequest.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+(1000*60*30)))
				.signWith(generateKey())
				.compact();
	}
	public SecretKey generateKey()
	{
		byte[] key = Decoders.BASE64.decode(jwtKeyString);
		return Keys.hmacShaKeyFor(key);
	}
	public Claims verifyAndExtractClaim(String token)
	{
		return Jwts
				.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
