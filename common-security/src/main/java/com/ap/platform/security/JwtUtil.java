package com.ap.platform.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.List;

public class JwtUtil {
	private final Key key;
	private final long expiration;

  	public JwtUtil(String secret, long expiration){
		this.key = Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
		this.expiration = expiration;
	}
	
	//generate token
	public String generateToken(String username, List<String> roles){
	  return Jwts.builder()
			.setSubject(username)
			.claim("roles",roles)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
	//extract username
	public String extractUsername(String token){
	   return getClaims(token).getSubject();
	}
	
	//extract roles
	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token){
		Claims claims = getClaims(token);
		return (List<String>) claims.get("roles");
	}

	// validate token
	public boolean validateToken(String token){
	   try{
		getClaims(token);
		 return true;
	     	}
	   catch(JwtException | IllegalArgumentException e){
		 return false;
		}
	}

	//check expiration
	public boolean isTokenExpired(String token){
		return getClaims(token).getExpiration().before(new Date());
	}

	//parse claim
	private Claims getClaims(String token){
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.setAllowedClockSkewSeconds(30)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

}
