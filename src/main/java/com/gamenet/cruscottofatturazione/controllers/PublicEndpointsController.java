package com.gamenet.cruscottofatturazione.controllers;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamenet.cruscottofatturazione.models.LoginRequest;
import com.gamenet.cruscottofatturazione.models.request.VerifyTokenRequest;
import com.gamenet.cruscottofatturazione.models.response.VerifyTokenResponse;
import com.gamenet.cruscottofatturazione.services.interfaces.UserAuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PublicEndpointsController {
    
    private final UserAuthenticationService authenticationService;

    // private final EmailService emailService;
    

    @PostMapping("/login")
    public Object login(
    		@RequestBody LoginRequest logReq) {
        try 
        {        	
            return authenticationService
                    .login(logReq.getUsername(), logReq.getPassword());
        } catch (BadCredentialsException e)
        {
            return ResponseEntity.status(UNAUTHORIZED).body(e.getMessage());
        }
    }
    
    @PostMapping("/verifyToken")
    public VerifyTokenResponse verifyToken(
    		@RequestBody VerifyTokenRequest verTokReq) {
        try 
        {        	
            return authenticationService
                    .verifyToken(verTokReq.getToken(), verTokReq.getUsername());
        }
        catch (BadCredentialsException e)
        {
        	VerifyTokenResponse badResponse = new VerifyTokenResponse();
        	badResponse.setIsValid(false);
        	badResponse.setIsRefresh(false);
        	badResponse.setToken("");
        	badResponse.setMessage("Not valid authentication token.");
            return badResponse;
        }
    }
    
    
    @GetMapping("/hello")
    public String hello()
    {
    	// emailService.getAllegatiContratto("AWP");
        return "Hello World!";
    }

}
