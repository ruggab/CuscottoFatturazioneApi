package com.gamenet.cruscottofatturazione.services.interfaces;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import com.gamenet.cruscottofatturazione.entities.User;
import com.gamenet.cruscottofatturazione.models.response.VerifyTokenResponse;

public interface UserAuthenticationService
{
	com.gamenet.cruscottofatturazione.models.User login(String username, String password) throws BadCredentialsException;
	
	VerifyTokenResponse verifyToken(String token, String username) throws BadCredentialsException;

    User authenticateByToken(String token) throws AuthenticationException;

	Boolean logout(Integer userId);

}
