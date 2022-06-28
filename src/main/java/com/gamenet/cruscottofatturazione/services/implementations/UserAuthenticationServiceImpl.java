package com.gamenet.cruscottofatturazione.services.implementations;


import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.context.JWTService;
import com.gamenet.cruscottofatturazione.context.MD5_Hash;
import com.gamenet.cruscottofatturazione.context.TokenVerificationException;
import com.gamenet.cruscottofatturazione.entities.User;
import com.gamenet.cruscottofatturazione.models.RoleUser;
import com.gamenet.cruscottofatturazione.models.response.VerifyTokenResponse;
import com.gamenet.cruscottofatturazione.repositories.BusinessRepository;
import com.gamenet.cruscottofatturazione.repositories.UserRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.UserAuthenticationService;
import com.gamenet.cruscottofatturazione.services.interfaces.UserService;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    
    private final UserRepository userRepo;
    
    private final BusinessRepository businessRepo;
	
    private final ApplicationLogsService appService;
    
    private final UserService userService;

    private final JWTService jwtService;
    
    private Logger log = LoggerFactory.getLogger(getClass());

	private ObjectMapper jsonMapper = new ObjectMapper();
    
    private final Environment env;

    @Override
    public com.gamenet.cruscottofatturazione.models.User login(String username, String password) throws BadCredentialsException
    {
    	this.log.info("UserAuthenticationService: login -> [username: " + username + " - password: " + password + "]");
    	appService.insertLog("info", "UserAuthenticationService", "login", "[username: " + username + " - password: " + password + "]", "", "login");
    	
    	try
    	{
	    	String passEncrypt = MD5_Hash.encrypt(password);
	    	
	    	User userFind = userRepo.findByUsername(username);
	    	
	    	if(userFind != null)
	    	{
	        	this.log.info("UserAuthenticationService: login -> USER FIND ]");
	        	appService.insertLog("info", "UserAuthenticationService", "login", "USER FIND", "", "login");
	        	
		    	if(userFind.getPassword().equals(passEncrypt))
		    	{
		    		if(userFind.getValidTo().after(new Date()))
		    		{
			        	this.log.info("UserAuthenticationService: login -> PASSWORD MATCH ]");
			        	appService.insertLog("info", "UserAuthenticationService", "login", "PASSWORD MATCH", "", "login");
			        	
			    		String token = jwtService.create(username);
			    		userFind.setToken(token);
			    		
			    		userRepo.save(userFind);
			    		
			    		com.gamenet.cruscottofatturazione.models.User mod_user = new com.gamenet.cruscottofatturazione.models.User();
			    		
			    		mod_user.setId(userFind.getId());
			    		
			    		RoleUser mod_role = new RoleUser();
			    		BeanUtils.copyProperties(userFind.getRuoloUtente(), mod_role);
			    		mod_user.setRuoloUtente(mod_role);
		
			    		mod_user.setName(userFind.getName());
			    		mod_user.setEmail(userFind.getEmail());
			    		mod_user.setName(userFind.getName());
			    		mod_user.setUsername(userFind.getUsername());
			    		mod_user.setToken(userFind.getToken());
			    		mod_user.setIsNew(userFind.getIsNew());
			    		mod_user.setCreateUser(userFind.getCreateUser());
			    		mod_user.setCreateDate(userFind.getCreateDate());
			    		mod_user.setLastModUser(userFind.getLastModUser());
			    		mod_user.setLastModDate(userFind.getLastModDate());
		
			    		mod_user.setValidFrom(userFind.getValidFrom());
			    		mod_user.setValidTo(userFind.getValidTo());
			    		
			    		if(mod_user.getRuoloUtente() != null)
			    		{
			    			Integer idUser = userFind.getId();
			    			Boolean isAdmin = mod_role.getIsAdmin();			    			
			    			//mod_user.setAvaiableBusiness(businessRepo.getAvaiableBusinessByUser(idUser, isAdmin));

			    			Integer idRole = mod_user.getRuoloUtente().getId();
			    			mod_user.setVociUtente(userService.getVociMenuByRoleId(idRole, isAdmin));
			    		}
			    		
			    		/*if(mod_user.getRuoloUtente() != null)
			    		{
			    			RoleUser role
				    		Optional<Role> optRole = roleRepo.findById(mod_user.getRole_id());
				    		if(optRole.isPresent())
				    		{
				    			Role role = optRole.get();
				    			mod_user.setRole_name(role.getName());
				    		}
			    		}*/
	
			    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
						{
					    	String requestPrint = jsonMapper.writeValueAsString(mod_user);
					    	// String requestPrint = new Gson().toJson(userFind);
					    	this.log.debug("UserAuthenticationService: login -> USER RESULT: " + requestPrint);
					    	appService.insertLog("debug", "UserAuthenticationService", "login", "USER RESULT: " + requestPrint, "", "login");
						}
				    	
			        	this.log.info("UserAuthenticationService: login -> SUCCESSFULLY END");
			        	appService.insertLog("info", "UserAuthenticationService", "login", "SUCCESSFULLY END", "", "login");
			    		
			    		return mod_user;
		    		}
		    		else
		    		{
			    		this.log.info("UserAuthenticationService: login -> User expired.");
			        	appService.insertLog("info", "UserAuthenticationService", "login", "User expired", "", "login");
			            throw new BadCredentialsException("User expired.");
		    		}
		    	}
		    	else
		    	{
		    		this.log.info("UserAuthenticationService: login -> Password incorrect.");
		        	appService.insertLog("info", "UserAuthenticationService", "login", "Password incorrect", "", "login");
		            throw new BadCredentialsException("Invalid username or password.");
		    	}
	    	}
	    	else
	    	{
	    		this.log.info("UserAuthenticationService: login -> User incorrect.");
	        	appService.insertLog("info", "UserAuthenticationService", "login", "User incorrect", "", "login");
	            throw new BadCredentialsException("Invalid username or password.");
	    	}
    	}
    	catch(Exception e)
    	{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserAuthenticationService: login -> " + stackTrace);
			appService.insertLog("error", "UserAuthenticationService", "login", "Exception", stackTrace, "login");
    		throw new RuntimeException(e);
    	}
    }
    
    @Override
    public VerifyTokenResponse verifyToken(String token, String username_passed) throws BadCredentialsException
    {
    	this.log.info("UserAuthenticationService: verifyToken -> START");
    	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "START", "", "verifyToken");
    	try
    	{
	    	User userFind = userRepo.findByToken(token);
	    	VerifyTokenResponse badResponse = new VerifyTokenResponse();
	    	badResponse.setIsValid(false);
	    	badResponse.setIsRefresh(false);
	    	badResponse.setToken("");
	    	badResponse.setMessage("Invalid username or password.");
	    	
	    	if(userFind != null)
	    	{
	        	this.log.info("UserAuthenticationService: verifyToken -> USER FIND ]");
	        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "USER FIND", "", "verifyToken");
	        	
				if(userFind.getUsername() != null)
				{
					if(userFind.getUsername() != "")
					{
						if(userFind.getUsername() != username_passed)
						{
							if(jwtService.verifyTokenExipired(token))
							{
					        	this.log.info("UserAuthenticationService: verifyToken -> TOKEN VALID ]");
					        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "TOKEN VALID", "", "verifyToken");
					        	
								String new_token = jwtService.create(userFind.getUsername());
					    		userFind.setToken(new_token);
					    		
					    		userRepo.save(userFind);
			
					    		VerifyTokenResponse newTokenResponse = new VerifyTokenResponse();
					    		newTokenResponse.setIsValid(true);
					    		newTokenResponse.setIsRefresh(true);
					    		newTokenResponse.setToken(new_token);

					    		this.log.info("UserAuthenticationService: verifyToken -> TOKEN EXPIRED.");
					        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "TOKEN EXPIRED", "", "verifyToken");
					    		
					            return newTokenResponse;
							}
							else
							{
								VerifyTokenResponse tokenResponse = new VerifyTokenResponse();
								tokenResponse.setIsValid(true);
								tokenResponse.setIsRefresh(false);
								tokenResponse.setToken(token);

					    		this.log.info("UserAuthenticationService: verifyToken -> TOKEN EXPIRED.");
					        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "TOKEN EXPIRED", "", "verifyToken");
					            return tokenResponse;
							}
						}
				    	else
				    	{
				    		this.log.info("UserAuthenticationService: verifyToken -> USERNAME INCORRECT.");
				        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "USERNAME INCORRECT", "", "verifyToken");
				            return badResponse;
				    	}
					}
			    	else
			    	{
			    		this.log.info("UserAuthenticationService: verifyToken -> USERNAME EMPTY.");
			        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "USERNAME EMPTY", "", "verifyToken");
			            return badResponse;
			    	}
				}
		    	else
		    	{
		    		this.log.info("UserAuthenticationService: verifyToken -> USERNAME NULL.");
		        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "USERNAME NULL", "", "verifyToken");
		            return badResponse;
		    	}
	    	}
	    	else
	    	{
	    		this.log.info("UserAuthenticationService: verifyToken -> USER NOT FOUND.");
	        	appService.insertLog("info", "UserAuthenticationService", "verifyToken", "USER NOT FOUND", "", "verifyToken");
	            return badResponse;
	    	}
    	}
    	catch(Exception e)
    	{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserAuthenticationService: verifyToken -> " + stackTrace);
			appService.insertLog("error", "UserAuthenticationService", "verifyToken", "Exception", stackTrace, "verifyToken");
    		throw new RuntimeException(e);
    	}
    }

    @Override
    public User authenticateByToken(String token) throws BadCredentialsException
    {
    	this.log.info("UserAuthenticationService: authenticateByToken -> [token: " + token + "]");
    	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "[token: " + token + "]", "", "authenticateByToken");
    	
        try
        {
        	User userFind = userRepo.findByToken(token);
        	
        	if(userFind != null)
        	{
	        	this.log.info("UserAuthenticationService: authenticateByToken -> USER BY TOKEN FOUND ]");
	        	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "USER BY TOKEN FOUND", "", "authenticateByToken");
	        	
        		if(jwtService.verifyTokenExipired(token))
        		{
    	        	this.log.info("UserAuthenticationService: authenticateByToken -> EXIRED TOKEN ]");
    	        	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "EXIRED TOKEN", "", "authenticateByToken");
    	        	
                	throw new BadCredentialsException("Exipred token.");
        		}
        		else
        		{
        			Object username = jwtService.verify(token).get("username");
                    
                    if(username != null)
                    {
                    	userFind = userRepo.findByUsername(String.valueOf(username));
                    	
                    	if(userFind != null)
                    	{
                    		return userFind;
                    	}
                    	else
                    	{
            	        	this.log.info("UserAuthenticationService: authenticateByToken -> USER NOT FOUND ]");
            	        	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "USER NOT FOUND", "", "authenticateByToken");
            	        	
                        	throw new UsernameNotFoundException("User '" + String.valueOf(username) + "' not found.");
                    	}
                    }
                    else
                    {
        	        	this.log.info("UserAuthenticationService: authenticateByToken -> USER NULL ]");
        	        	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "USER NULL", "", "authenticateByToken");
        	        	
                    	throw new BadCredentialsException("Invalid Authentication token.");
                    }
        		}
        	}
            else
            {
	        	this.log.info("UserAuthenticationService: authenticateByToken -> USER BY TOKEN NOT FOUND ]");
	        	appService.insertLog("info", "UserAuthenticationService", "authenticateByToken", "USER BY TOKEN NOT FOUND", "", "authenticateByToken");
	        	
            	throw new BadCredentialsException("Invalid Authentication token.");
            }
        }
        catch (TokenVerificationException e)
        {
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserAuthenticationService: authenticateByToken -> " + stackTrace);
			appService.insertLog("error", "UserAuthenticationService", "authenticateByToken", "Exception", stackTrace, "authenticateByToken");
            throw new BadCredentialsException("Invalid JWT token.", e);
        }
    }

    @Override
    public Boolean logout(Integer userId)
    {
    	this.log.info("UserAuthenticationService: logout -> [userId: " + userId.toString() + "]");
    	appService.insertLog("info", "UserAuthenticationService", "logout", "[userId: " + userId.toString() + "]", "", "login");
    	    	
    	try
    	{
    		userRepo.logoutUser(userId);
    		this.log.info("UserAuthenticationService: logout -> SUCCESSFULLY LOGOUT");
        	appService.insertLog("info", "UserAuthenticationService", "logout", "SUCCESSFULLY LOGOUT", "", "logout");
    		
    		
        	return true;
    	}
    	catch(Exception e)
    	{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserAuthenticationService: logout -> " + stackTrace);
			appService.insertLog("error", "UserAuthenticationService", "logout", "Exception", stackTrace, "verifyToken");
    		throw new RuntimeException(e);
    	}
    }

}
