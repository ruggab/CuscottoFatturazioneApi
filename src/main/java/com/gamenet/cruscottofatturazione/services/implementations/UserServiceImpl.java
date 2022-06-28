package com.gamenet.cruscottofatturazione.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamenet.cruscottofatturazione.context.MD5_Hash;
import com.gamenet.cruscottofatturazione.context.SortUtils;
import com.gamenet.cruscottofatturazione.models.Business;
import com.gamenet.cruscottofatturazione.models.GruppoUtenti;
import com.gamenet.cruscottofatturazione.models.ListSort;
import com.gamenet.cruscottofatturazione.models.PagedListFilterAndSort;
import com.gamenet.cruscottofatturazione.models.RoleUser;
import com.gamenet.cruscottofatturazione.models.RoleVoceMenu;
import com.gamenet.cruscottofatturazione.models.User;
import com.gamenet.cruscottofatturazione.models.VoceMenu;
import com.gamenet.cruscottofatturazione.models.response.RoleVoceMenuOverview;
import com.gamenet.cruscottofatturazione.models.response.RuoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.UpdateGenericResponse;
import com.gamenet.cruscottofatturazione.models.response.UtentiListOverview;
import com.gamenet.cruscottofatturazione.repositories.BusinessRepository;
import com.gamenet.cruscottofatturazione.repositories.GruppiRepository;
import com.gamenet.cruscottofatturazione.repositories.RoleRepository;
import com.gamenet.cruscottofatturazione.repositories.RoleVoceMenuRepository;
import com.gamenet.cruscottofatturazione.repositories.UserRepository;
import com.gamenet.cruscottofatturazione.repositories.VociMenuRepository;
import com.gamenet.cruscottofatturazione.services.interfaces.ApplicationLogsService;
import com.gamenet.cruscottofatturazione.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepo;
	
	private final BusinessRepository businessRepo;
    
    private final GruppiRepository gruppoRepo;
    
    private final VociMenuRepository vociMenuRepo;
    
    private final RoleVoceMenuRepository roleVociMenuRepo;
	
    private final ApplicationLogsService appService;
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    private final Environment env;

	private ObjectMapper jsonMapper = new ObjectMapper();

    public User save(User user)
    {
		com.gamenet.cruscottofatturazione.entities.User ent_user = new com.gamenet.cruscottofatturazione.entities.User();
		
		BeanUtils.copyProperties(user, ent_user);
		userRepository.save(ent_user);
		
		return user;
		
		//return userRepository.save(entity) (com.gamenet.portalecredito.entities.User, ent_user); //ent_user);
    }

    public User getByToken(String token) {
		com.gamenet.cruscottofatturazione.models.User mod_user = new User();
        BeanUtils.copyProperties(userRepository.findByToken(token), mod_user);
        
        return mod_user;
    }

    public User getByUsername(String username) {
		com.gamenet.cruscottofatturazione.models.User mod_user = new User();
        BeanUtils.copyProperties(userRepository.findByUsername(username), mod_user);
        
        return mod_user;
    }
	
	@Override
	public List<User> getUsers()
	{
    	this.log.info("UserService: getUsers -> START");
    	appService.insertLog("info", "UserService", "getUsers", "START", "", "getUsers");
    	
		try 
		{
			List<com.gamenet.cruscottofatturazione.entities.User> entity_users = new ArrayList<com.gamenet.cruscottofatturazione.entities.User>();
			List<com.gamenet.cruscottofatturazione.models.User> users = new ArrayList<com.gamenet.cruscottofatturazione.models.User>();
			
			entity_users = (List<com.gamenet.cruscottofatturazione.entities.User>) this.userRepository.findAll();
			
			for (com.gamenet.cruscottofatturazione.entities.User ent_user : entity_users) {
				com.gamenet.cruscottofatturazione.models.User mod_user = convertUserModel(ent_user);
				
				//BeanUtils.copyProperties(ent_user, mod_user);
				users.add(mod_user);
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(users);
		    	this.log.debug("UserService: getUsers -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "getUsers", "PROCESS END WITH: " + responsePrint, "", "getUsers");
			}
	    	
			return users;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: getUsers -> " + stackTrace);
			appService.insertLog("error", "UserService", "getUsers", "Exception", stackTrace, "getUsers");
			
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public User getUserById(Integer userId)
	{
    	this.log.info("UserService: getUserById -> [userId: " + userId.toString() + "]");
    	appService.insertLog("info", "UserService", "getUserById", "[userId: " + userId.toString() + "]", "", "getUserById");
    	
		User mod_user = new User(); 
		
		try
		{
			Optional<com.gamenet.cruscottofatturazione.entities.User> opt_ent_user = userRepository.findById(userId);
			if(opt_ent_user.isPresent())
			{
				com.gamenet.cruscottofatturazione.entities.User ent_user = opt_ent_user.get();
				mod_user = convertUserModel(ent_user);
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(mod_user);
		    	this.log.debug("UserService: getUsers -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "getUsers", "PROCESS END WITH: " + responsePrint, "", "getUsers");
			}
		}
		catch(Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: getUsers -> " + stackTrace);
			appService.insertLog("error", "UserService", "getUsers", "Exception", stackTrace, "getUsers");
			
			throw new RuntimeException(e);
		}
		
		return mod_user;
	}
	
	public String loginUser(String username , String password)
	{
		com.gamenet.cruscottofatturazione.models.User mod_user = new User();
		BeanUtils.copyProperties(userRepository.findByUsernameAndPassword(username, password), mod_user);
		
		return "";
	}
	
	public Boolean loginUserByToken(String token)
	{
		com.gamenet.cruscottofatturazione.models.User mod_user = new User();
		BeanUtils.copyProperties(this.userRepository.findByToken(token), mod_user);
		
		return true;
	}
	
	private com.gamenet.cruscottofatturazione.models.User convertUserModel(com.gamenet.cruscottofatturazione.entities.User ent_user)
	{
    	this.log.info("UserService: convertUserModel -> START");
    	appService.insertLog("info", "UserService", "convertUserModel", "START", "", "convertUserModel");

		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(ent_user);
		    	this.log.debug("UserService: convertUserModel -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertUserModel", "Object: " + requestPrint, "", "convertUserModel");
			}
	    	
			com.gamenet.cruscottofatturazione.models.User mod_user = new com.gamenet.cruscottofatturazione.models.User();
			
			mod_user.setId(ent_user.getId());
			
			RoleUser mod_role = new RoleUser();
			BeanUtils.copyProperties(ent_user.getRuoloUtente(), mod_role);
			mod_user.setRuoloUtente(mod_role);
	
			mod_user.setName(ent_user.getName());
			mod_user.setEmail(ent_user.getEmail());
			mod_user.setName(ent_user.getName());
			mod_user.setUsername(ent_user.getUsername());
			mod_user.setToken(ent_user.getToken());
    		mod_user.setIsNew(ent_user.getIsNew());
			mod_user.setCreateUser(ent_user.getCreateUser());
			mod_user.setCreateDate(ent_user.getCreateDate());
			mod_user.setLastModUser(ent_user.getLastModUser());
			mod_user.setLastModDate(ent_user.getLastModDate());
			mod_user.setPassword("");
	
			mod_user.setValidFrom(ent_user.getValidFrom());
			mod_user.setValidTo(ent_user.getValidTo());
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(mod_user);
		    	this.log.debug("UserService: convertUserModel -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertUserModel", "PROCESS END WITH: " + responsePrint, "", "convertUserModel");
			}
		
			return mod_user;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertUserModel -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertUserModel", "Exception", stackTrace, "convertUserModel");
			
			throw new RuntimeException(e);
		}
	}
	
	// Salvataggio
	@Override
	public Boolean saveUser(User utente, String utenteUpdate)
	{
    	this.log.info("UserService: saveUser -> START");
    	appService.insertLog("info", "UserService", "saveUser", "START", "", "saveUser");

		String passOut = "";
		if(!utente.getPassword().equals(""))
		{
			passOut = MD5_Hash.encrypt(utente.getPassword());
		}
		
    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(utente);
		    	this.log.debug("ProspectService: saveUser -> Object: " + requestPrint);
		    	appService.insertLog("debug", "ProspectService", "saveUser", "Object: " + requestPrint, "", "saveUser");
			}
        	
    		userRepository.saveUser(utente.getId(),
    								utente.getRuoloUtente().getId(),
    								utente.getName(),
    								utente.getEmail(),
    								utente.getUsername(),
    								passOut,
    								utente.getValidFrom(),
    								utente.getValidTo(),
    								utenteUpdate
    							);
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: saveUser -> " + stackTrace);
			appService.insertLog("error", "UserService", "saveUser", "Exception", stackTrace, "saveUser");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("UserService: saveUser -> SUCCESSFULLY END");
    	appService.insertLog("info", "UserService", "saveUser", "SUCCESSFULLY END", "", "saveUser");
    	return true;
	}
	
	// Cambio Password
	@Override
    public UpdateGenericResponse changeUserPassword(Integer idUser, String passwordPrecedente, String passwordNuova, String utenteUpdate) throws BadCredentialsException
    {
    	this.log.info("UserService: changeUserPassword -> [idUser: " + idUser.toString() + "]");
    	appService.insertLog("info", "UserService", "changeUserPassword", "[idUser: " + idUser.toString() + "]", "", utenteUpdate);
    	
    	try
		{
	    	String passwordPrecedenteEncrypt = MD5_Hash.encrypt(passwordPrecedente);
	    	String passwordNuovaEncrypt = MD5_Hash.encrypt(passwordNuova);
	    	
    		List<Integer> resultList = userRepository.changePasswordUser(idUser, passwordPrecedenteEncrypt, passwordNuovaEncrypt, utenteUpdate);
    		
    		if(resultList.size() > 0)
    		{
    			Integer result = resultList.get(0);
    			
    			if(result < 0)
    			{
    				String messaggio = "";
    				String logMessage = "";
    				switch (result) {
					case -1:
						messaggio = "Impossibile modificare la password al momento.";
						logMessage = "Impossibile modificare la password al momento.";
						break;
					case -2:
						messaggio = "La password precedente non è corretta!";
						logMessage = "Password precedente errata.";
						break;

					}

		    		this.log.info("UserService: changeUserPassword -> " + logMessage);
		        	appService.insertLog("info", "UserService", "changeUserPassword", logMessage, "", utenteUpdate);
			        return new UpdateGenericResponse(false, messaggio);
    			}
    		}
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: changeUserPassword -> " + stackTrace);
			appService.insertLog("error", "UserService", "changeUserPassword", "Exception", stackTrace, utenteUpdate);
			
	        e.printStackTrace();
	        return new UpdateGenericResponse(false, "Impossibile modificare la password al momento.");
		}
    	
    	this.log.info("UserService: changeUserPassword -> SUCCESSFULLY END");
    	appService.insertLog("info", "UserService", "changeUserPassword", "SUCCESSFULLY END", "", utenteUpdate);
        return new UpdateGenericResponse(true, "Impossibile modificare la password al momento.");
    }
	
	// Cancellazione
	@Override
	public Boolean deleteUser(Integer userId, String utenteUpdate)
	{
    	this.log.info("UserService: deleteUser -> [userId: " + userId.toString() + "]");
    	appService.insertLog("info", "UserService", "deleteUser", "[userId: " + userId.toString() + "]", "", "deleteUser");
    	
    	try
		{	
    		userRepository.deleteUser(userId, utenteUpdate);
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: deleteUser -> " + stackTrace);
			appService.insertLog("error", "UserService", "deleteUser", "Exception", stackTrace, "deleteUser");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("UserService: deleteUser -> SUCCESSFULLY END");
    	appService.insertLog("info", "UserService", "deleteUser", "SUCCESSFULLY END", "", "deleteUser");
    	return true;
	}
	
	/***** RUOLI *****/
	@Override
	public List<RoleUser> getRuoliUtente()
	{
		return convertRuoliModelList(roleRepo.findAll());
	}

	private List<RoleUser> convertRuoliModelList(Iterable<com.gamenet.cruscottofatturazione.entities.RoleUser> entity_rol)
	{
    	this.log.info("UserService: convertRuoliModelList -> START");
    	appService.insertLog("info", "UserService", "convertRuoliModelList", "START", "", "convertRuoliModelList");

		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(entity_rol);
		    	this.log.debug("UserService: convertRuoliModelList -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertRuoliModelList", "Object: " + requestPrint, "", "convertRuoliModelList");
			}
	    	
			List<RoleUser> ruoli = new ArrayList<com.gamenet.cruscottofatturazione.models.RoleUser>();
			
			for (com.gamenet.cruscottofatturazione.entities.RoleUser ent_ruolo : entity_rol) {
				
				if(ent_ruolo.getDeleted() == null || ent_ruolo.getDeleted() == false)
				{
					com.gamenet.cruscottofatturazione.models.RoleUser mod_ruolo = new com.gamenet.cruscottofatturazione.models.RoleUser();
					BeanUtils.copyProperties(ent_ruolo, mod_ruolo);
					ruoli.add(mod_ruolo);					
				}
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(ruoli);
		    	this.log.debug("UserService: convertRuoliModelList -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertRuoliModelList", "PROCESS END WITH: " + responsePrint, "", "convertRuoliModelList");
			}
		
			return ruoli;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertRuoliModelList -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertRuoliModelList", "Exception", stackTrace, "convertRuoliModelList");
			
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public RoleUser getRoleById(Integer roleId)
	{
		RoleUser mod_ruolo = new RoleUser(); 
				
		Optional<com.gamenet.cruscottofatturazione.entities.RoleUser> opt_ent_role = roleRepo.findById(roleId);
		if(opt_ent_role.isPresent())
		{
			com.gamenet.cruscottofatturazione.entities.RoleUser ent_ruolo = opt_ent_role.get();
			BeanUtils.copyProperties(ent_ruolo, mod_ruolo);
		}
		
		return mod_ruolo;
	}
	
	// Salvataggio
	@Override
	public Boolean saveRole(RoleUser ruolo, String utenteUpdate)
	{
    	this.log.info("UserService: saveRole -> START");
    	appService.insertLog("info", "UserService", "saveRole", "START", "", "saveRole");

    	try
		{	
    		if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(ruolo);
		    	this.log.debug("UserService: saveRole -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "saveRole", "Object: " + requestPrint, "", "saveRole");
			}
        	
    		roleRepo.saveRole(ruolo.getId(),
		    				  ruolo.getName(),
		    				  ruolo.getIsAdmin(),
		    				  ruolo.getEmail(),
		    				  ruolo.getDescription(),
    						  utenteUpdate
    						);
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: saveRole -> " + stackTrace);
			appService.insertLog("error", "UserService", "saveRole", "Exception", stackTrace, "saveRole");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("UserService: saveRole -> SUCCESSFULLY END");
    	appService.insertLog("info", "UserService", "saveRole", "SUCCESSFULLY END", "", "saveRole");
    	return true;
	}
	
	/***** VOCI MENU *****/
    @Override
    public List<VoceMenu> getVociMenuList()
    {
    	return convertVociMenuModelList(vociMenuRepo.findAll(), false);
    }

    @Override
    public List<VoceMenu> getVociMenuByRoleId(Integer roleId, Boolean isAdmin)
    {
    	return convertVociMenuModelList(vociMenuRepo.getVociMenuByRoleId(roleId, isAdmin), true);
    }

	private List<VoceMenu> convertVociMenuModelList(Iterable<com.gamenet.cruscottofatturazione.entities.VoceMenu> entityVoci, Boolean aggregate)
	{
    	this.log.info("UserService: convertVociMenuModelList -> START");
    	appService.insertLog("info", "UserService", "convertVociMenuModelList", "START", "", "convertVociMenuModelList");

		try
		{
			Stream<com.gamenet.cruscottofatturazione.entities.VoceMenu> streamVoci = StreamSupport.stream(entityVoci.spliterator(), false);
			
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(entityVoci);
		    	this.log.debug("UserService: convertVociMenuModelList -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertVociMenuModelList", "Object: " + requestPrint, "", "convertVociMenuModelList");
			}
	    	
			List<VoceMenu> vociResult = new ArrayList<com.gamenet.cruscottofatturazione.models.VoceMenu>();
			
			if(aggregate)
			{
				List<com.gamenet.cruscottofatturazione.entities.VoceMenu> livelloZero = streamVoci.filter(voceX -> voceX.getParentId() == null).toList();
				
				for (com.gamenet.cruscottofatturazione.entities.VoceMenu voceMenu : livelloZero)
				{
					VoceMenu modVoce = convertVociMenuModel(voceMenu);
					
					Stream<com.gamenet.cruscottofatturazione.entities.VoceMenu> streamVociFigli = StreamSupport.stream(entityVoci.spliterator(), false);
					List<com.gamenet.cruscottofatturazione.entities.VoceMenu> livelloUno = streamVociFigli.filter(voceX -> voceX.getParentId() == modVoce.getId()).toList();
					
					List<VoceMenu> figli = new ArrayList<VoceMenu>();
					for (com.gamenet.cruscottofatturazione.entities.VoceMenu voceMenuFiglio : livelloUno)
					{
						VoceMenu modFiglio = convertVociMenuModel(voceMenuFiglio);
						
						Stream<com.gamenet.cruscottofatturazione.entities.VoceMenu> streamVociLivelloDue = StreamSupport.stream(entityVoci.spliterator(), false);
						List<com.gamenet.cruscottofatturazione.entities.VoceMenu> livelloDue = streamVociLivelloDue.filter(voceX -> voceX.getParentId() == modFiglio.getId()).toList();
						List<VoceMenu> figliLivelloDue = new ArrayList<VoceMenu>();
						
						for (com.gamenet.cruscottofatturazione.entities.VoceMenu figlioLivelloDue : livelloDue)
						{
							VoceMenu modFiglioLivelloDue = convertVociMenuModel(figlioLivelloDue);
							figliLivelloDue.add(modFiglioLivelloDue);
						}
						modFiglio.setChild(figliLivelloDue);
						
						figli.add(modFiglio);
					}
					modVoce.setChild(figli);
					
					vociResult.add(modVoce);
				}
			}
			else
			{
				List<com.gamenet.cruscottofatturazione.entities.VoceMenu> allVoci = streamVoci.toList();
				
				for (com.gamenet.cruscottofatturazione.entities.VoceMenu voceMenu : allVoci)
				{
					VoceMenu modVoce = convertVociMenuModel(voceMenu);
					
					vociResult.add(modVoce);
				}
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(vociResult);
		    	this.log.debug("UserService: convertVociMenuModelList -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertVociMenuModelList", "PROCESS END WITH: " + responsePrint, "", "convertVociMenuModelList");
			}
		
			return vociResult;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertVociMenuModelList -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertVociMenuModelList", "Exception", stackTrace, "convertVociMenuModelList");
			
			throw new RuntimeException(e);
		}
	}
	
	private VoceMenu convertVociMenuModel(com.gamenet.cruscottofatturazione.entities.VoceMenu entVoce)
	{
    	this.log.info("UserService: convertVociMenuModel -> START");
    	appService.insertLog("info", "UserService", "convertVociMenuModel", "START", "", "convertVociMenuModel");

		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(entVoce);
		    	this.log.debug("UserService: convertVociMenuModel -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertVociMenuModel", "Object: " + requestPrint, "", "convertVociMenuModel");
			}
	    	
			VoceMenu modVoce = new VoceMenu();
			
			modVoce.setId(entVoce.getId());	
			modVoce.setPath(entVoce.getPath());
			modVoce.setTitle(entVoce.getTitle());
			modVoce.setIcon(entVoce.getIcon());
			modVoce.setOrderNumber(entVoce.getOrderNumber());
			modVoce.setCssClass(entVoce.getCssClass());
	
			modVoce.setIdentifier(entVoce.getIdentifier());
			
			modVoce.setIsDettaglio(entVoce.getIsDettaglio());

			modVoce.setCreateUser(entVoce.getCreateUser());
			modVoce.setCreateDate(entVoce.getCreateDate());
			modVoce.setLastModUser(entVoce.getLastModUser());
			modVoce.setLastModDate(entVoce.getLastModDate());
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(modVoce);
		    	this.log.debug("UserService: convertVociMenuModel -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertVociMenuModel", "PROCESS END WITH: " + responsePrint, "", "convertVociMenuModel");
			}
		
			return modVoce;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertVociMenuModel -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertVociMenuModel", "Exception", stackTrace, "convertVociMenuModel");
			
			throw new RuntimeException(e);
		}
	}
	
	// Cancellazione
	@Override
	public Boolean deleteRole(Integer roleId, String utenteUpdate)
	{
    	this.log.info("UserService: deleteRole -> [roleId: " + roleId.toString() + "]");
    	appService.insertLog("info", "UserService", "deleteRole", "[roleId: " + roleId.toString() + "]", "", "deleteRole");
    	
    	try
    	{
    		roleRepo.deleteRole(roleId, utenteUpdate);
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: deleteRole -> " + stackTrace);
			appService.insertLog("error", "UserService", "deleteRole", "Exception", stackTrace, "deleteRole");
			
	        e.printStackTrace();
	        return false;
		}
    	
    	this.log.info("UserService: deleteRole -> SUCCESSFULLY END");
    	appService.insertLog("info", "UserService", "deleteRole", "SUCCESSFULLY END", "", "deleteRole");
    	return true;
	}
	
	/***** GRUPPI *****/
	@Override
	public List<GruppoUtenti> getGruppiUtente()
	{
		return convertGruppiModelList(gruppoRepo.findAll());
	}

	private List<GruppoUtenti> convertGruppiModelList(Iterable<com.gamenet.cruscottofatturazione.entities.GruppoUtenti> entity_grp)
	{
    	this.log.info("UserService: convertGruppiModelList -> START");
    	appService.insertLog("info", "UserService", "convertGruppiModelList", "START", "", "convertGruppiModelList");

		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(entity_grp);
		    	this.log.debug("UserService: convertGruppiModelList -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertGruppiModelList", "Object: " + requestPrint, "", "convertGruppiModelList");
			}
	    	
			List<GruppoUtenti> gruppi = new ArrayList<com.gamenet.cruscottofatturazione.models.GruppoUtenti>();
			
			for (com.gamenet.cruscottofatturazione.entities.GruppoUtenti ent_grp : entity_grp) {
				
				com.gamenet.cruscottofatturazione.models.GruppoUtenti mod_grp = new com.gamenet.cruscottofatturazione.models.GruppoUtenti();
				BeanUtils.copyProperties(ent_grp, mod_grp);
				gruppi.add(mod_grp);
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(gruppi);
		    	this.log.debug("UserService: convertGruppiModelList -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertGruppiModelList", "PROCESS END WITH: " + responsePrint, "", "convertGruppiModelList");
			}
		
			return gruppi;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertGruppiModelList -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertGruppiModelList", "Exception", stackTrace, "convertGruppiModelList");
			
			throw new RuntimeException(e);
		}
	}
	
	/***** BUSINESS *****/
	public List<String> getAvaiableBusinessByUser(Integer idUser, Boolean isAdmin)
	{
		return businessRepo.getAvaiableBusinessByUser(idUser, isAdmin);
	}
	
	@SuppressWarnings("unused")
	private List<Business> convertBusinessModelList(List<com.gamenet.cruscottofatturazione.entities.Business> entities_bus)
	{
    	this.log.info("UserService: convertBusinessModelList -> START");
    	appService.insertLog("info", "UserService", "convertBusinessModelList", "START", "", "convertBusinessModelList");

		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(entities_bus);
		    	this.log.debug("UserService: convertBusinessModelList -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "convertBusinessModelList", "Object: " + requestPrint, "", "convertBusinessModelList");
			}
	    	
			List<Business> bus_list = new ArrayList<com.gamenet.cruscottofatturazione.models.Business>();
			
			for (com.gamenet.cruscottofatturazione.entities.Business ent_bus : entities_bus) {
				
				com.gamenet.cruscottofatturazione.models.Business mod_bus = new com.gamenet.cruscottofatturazione.models.Business();
				BeanUtils.copyProperties(ent_bus, mod_bus);
				bus_list.add(mod_bus);
			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(bus_list);
		    	this.log.debug("UserService: convertBusinessModelList -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "convertBusinessModelList", "PROCESS END WITH: " + responsePrint, "", "convertBusinessModelList");
			}
		
			return bus_list;
		}
		catch (Exception e) 
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: convertBusinessModelList -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertBusinessModelList", "Exception", stackTrace, "convertBusinessModelList");
			
			throw new RuntimeException(e);
		}
	}

	/***** VOCI MENU - ROLE *****/

	@Override
	public RoleVoceMenu getRoleVoceMenuById(Integer idRoleVoceMenu)
	{
		this.log.info("UserService: getRoleVoceMenuById -> START");
		appService.insertLog("info", "UserService", "getRoleVoceMenuById", "START", "", "getRoleVoceMenuById");

		try {
			return convertRoleVoceMenuOptional(roleVociMenuRepo.findById(idRoleVoceMenu));
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("UserService: getRoleVoceMenuById -> " + stackTrace);
			appService.insertLog("error", "UserService", "getRoleVoceMenuById", "Exception", stackTrace, "getRoleVoceMenuById");

			throw new RuntimeException(e);
		}
	}

	private RoleVoceMenu convertRoleVoceMenuOptional(Optional<com.gamenet.cruscottofatturazione.entities.RoleVoceMenu> entityRoleOpt)
	{
		RoleVoceMenu modRoleVoce = new RoleVoceMenu();

		if (entityRoleOpt.isPresent()) {
			modRoleVoce = convertRoleVoceMenu(entityRoleOpt.get());
		}

		return modRoleVoce;
	}

	private List<RoleVoceMenu> convertRoleVoceMenuList(Iterable<com.gamenet.cruscottofatturazione.entities.RoleVoceMenu> entityRoleVoce)
	{
		this.log.info("UserService: convertRoleVoceMenuList -> START");
		appService.insertLog("info", "UserService", "convertRoleVoceMenuList", "START", "","convertRoleVoceMenuList");

		try {
			if (env.getProperty("portalecredito.mode.debug").equals("true")) {
				String requestPrint = jsonMapper.writeValueAsString(entityRoleVoce);
				this.log.debug("UserService: convertRoleVoceMenuList -> Object: " + requestPrint);
				appService.insertLog("debug", "UserService", "convertRoleVoceMenuList", "Object: " + requestPrint, "", "convertRoleVoceMenuList");
			}

			List<RoleVoceMenu> roleMenuList = new ArrayList<RoleVoceMenu>();

			for (com.gamenet.cruscottofatturazione.entities.RoleVoceMenu entRoleVoce : entityRoleVoce)
			{
				roleMenuList.add(convertRoleVoceMenu(entRoleVoce));
			}

			if (env.getProperty("portalecredito.mode.debug").equals("true")) {
				String responsePrint = jsonMapper.writeValueAsString(roleMenuList);
				this.log.debug("UserService: convertRoleVoceMenuList -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "UserService", "convertRoleVoceMenuList","PROCESS END WITH: " + responsePrint, "", "convertRoleVoceMenuList");
			}

			return roleMenuList;
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("UserService: convertRoleVoceMenuList -> " + stackTrace);
			appService.insertLog("error", "UserService", "convertRoleVoceMenuList", "Exception", stackTrace,"convertRoleVoceMenuList");

			throw new RuntimeException(e);
		}
	}

	private RoleVoceMenu convertRoleVoceMenu(com.gamenet.cruscottofatturazione.entities.RoleVoceMenu entVoce)
	{
		RoleVoceMenu modRoleVoce = new RoleVoceMenu();
		modRoleVoce.setId(entVoce.getId());

		modRoleVoce.setVoceMenuRuolo(convertVociMenuModel(entVoce.getVoceMenuRuolo()));

		RoleUser roleVoce = new RoleUser();
		BeanUtils.copyProperties(entVoce.getRuoloVoceMenu(), roleVoce);
		modRoleVoce.setRuoloVoceMenu(roleVoce);

		modRoleVoce.setCreateUser(entVoce.getCreateUser());
		modRoleVoce.setCreateDate(entVoce.getCreateDate());
		modRoleVoce.setLastModUser(entVoce.getLastModUser());
		modRoleVoce.setLastModDate(entVoce.getLastModDate());

		return modRoleVoce;
	}

	// Salvataggio
	@Override
	public Boolean saveRoleVoceMenu(RoleVoceMenu roleVoceMenu, String utenteUpdate)
	{
		this.log.info("UserService: saveRoleVoceMenu -> START");
		appService.insertLog("info", "UserService", "saveRoleVoceMenu", "START", "", utenteUpdate);

		try
		{
			if (env.getProperty("portalecredito.mode.debug").equals("true"))
			{
				String requestPrint = jsonMapper.writeValueAsString(roleVoceMenu);
				this.log.debug("UserService: saveRoleVoceMenu -> Object: " + requestPrint);
				appService.insertLog("debug", "UserService", "saveRoleVoceMenu", "Object: " + requestPrint, "", utenteUpdate);
			}

			roleVociMenuRepo.saveRoleVoceMenu(roleVoceMenu.getId(), roleVoceMenu.getRuoloVoceMenu().getId(), roleVoceMenu.getVoceMenuRuolo().getId(),utenteUpdate);
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("UserService: saveRoleVoceMenu -> " + stackTrace);
			appService.insertLog("error", "UserService", "saveRoleVoceMenu", "Exception", stackTrace,utenteUpdate);

			e.printStackTrace();
			return false;
		}

		this.log.info("UserService: saveRoleVoceMenu -> SUCCESSFULLY END");
		appService.insertLog("info", "UserService", "saveRoleVoceMenu", "SUCCESSFULLY END", "", utenteUpdate);
		return true;
	}

	// Cancellazione
	@Override
	public Boolean deleteRoleVoceMenu(Integer roleVoceId, String utenteUpdate) {
		this.log.info("UserService: deleteRoleVoceMenu -> [roleVoceId: " + roleVoceId.toString() + "]");
		appService.insertLog("info", "UserService", "deleteRoleVoceMenu", "[roleVoceId: " + roleVoceId.toString() + "]", "", utenteUpdate);

		try 
		{
			roleVociMenuRepo.deleteRoleVoceMenu(roleVoceId);
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("UserService: deleteRoleVoceMenu -> " + stackTrace);
			appService.insertLog("error", "UserService", "deleteRoleVoceMenu", "Exception", stackTrace, utenteUpdate);

			e.printStackTrace();
			return false;
		}

		this.log.info("UserService: deleteRoleVoceMenu -> SUCCESSFULLY END");
		appService.insertLog("info", "UserService", "deleteRoleVoceMenu", "SUCCESSFULLY END", "", utenteUpdate);
		return true;
	}
	
	/***** DATA TABLE LIST *****/
    @Override
    public UtentiListOverview getUtentiDataTable(JsonNode payload)
    {
    	this.log.info("UserService: getUtentiDataTable -> START");
    	appService.insertLog("info", "UserService", "getUtentiDataTable", "START", "", "getUtentiDataTable");

    	UtentiListOverview response = new UtentiListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<User>());
		
		try
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(payload);
		    	this.log.debug("UserService: getUtentiDataTable -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "getUtentiDataTable", "Object: " + requestPrint, "", "getUtentiDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());
			
//			Specification<com.gamenet.cruscottofatturazione.entities.User> spec = new QuerySpecification<>();
//
//			for (ListFilter filter : model.getFilters())
//				spec = spec.and(new QuerySpecification<>(filter));

			PageRequest request = null;
			if (model.getSort() != null && !model.getSort().isEmpty()) {
				int index = 0;
				Sort sort = null;
				for (ListSort srt : model.getSort()) {
					if (index++ == 0)
						sort = Sort.by(SortUtils.translateSort(srt.getType()), srt.getName());
					else
						sort = sort.and(Sort.by(SortUtils.translateSort(srt.getType()), srt.getName()));
				}

				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE, sort);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize(), sort);
				}
			} else {
				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize());
				}
			}

//			Page<com.gamenet.cruscottofatturazione.entities.User> pages = userRepository.findAll(spec, request);
//			if (pages != null && pages.getTotalElements() > 0)
//			{
//				response.setTotalCount((int) pages.getTotalElements());
//				
//				for (com.gamenet.cruscottofatturazione.entities.User ent_user : pages.getContent()) {
//					com.gamenet.cruscottofatturazione.models.User mod_user = convertUserModel(ent_user);
//					
//					response.getLines().add(mod_user);
//				}		
//			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(response);
		    	this.log.debug("UserService: getUtentiDataTable -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "getUtentiDataTable", "PROCESS END WITH: " + responsePrint, "", "getUtentiDataTable");
			}
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: getUtentiDataTable -> " + stackTrace);
			appService.insertLog("error", "UserService", "getUtentiDataTable", "Exception", stackTrace, "getUtentiDataTable");
			
			throw new RuntimeException(e);
		}
   
		return response;
    }

    @Override
    public RuoliListOverview getRuoliDataTable(JsonNode payload)
    {
    	this.log.info("UserService: getRuoliDataTable -> START");
    	appService.insertLog("info", "UserService", "getRuoliDataTable", "START", "", "getRuoliDataTable");

    	RuoliListOverview response = new RuoliListOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<RoleUser>());
		
		try 
		{
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String requestPrint = jsonMapper.writeValueAsString(payload);
		    	this.log.debug("UserService: getRuoliDataTable -> Object: " + requestPrint);
		    	appService.insertLog("debug", "UserService", "getRuoliDataTable", "Object: " + requestPrint, "", "getRuoliDataTable");
			}
	    	
			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());
			
//			Specification<com.gamenet.cruscottofatturazione.entities.RoleUser> spec = new QuerySpecification<>();
//
//			for (ListFilter filter : model.getFilters())
//				spec = spec.and(new QuerySpecification<>(filter));

			PageRequest request = null;
			if (model.getSort() != null && !model.getSort().isEmpty()) {
				int index = 0;
				Sort sort = null;
				for (ListSort srt : model.getSort()) {
					if (index++ == 0)
						sort = Sort.by(SortUtils.translateSort(srt.getType()), srt.getName());
					else
						sort = sort.and(Sort.by(SortUtils.translateSort(srt.getType()), srt.getName()));
				}

				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE, sort);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize(), sort);
				}
			} else {
				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize());
				}
			}

//			Page<com.gamenet.cruscottofatturazione.entities.RoleUser> pages = roleRepo.findAll(spec, request);
//			if (pages != null && pages.getTotalElements() > 0)
//			{
//				response.setTotalCount((int) pages.getTotalElements());
//				response.setLines(convertRuoliModelList(pages.getContent()));
//			}
	    	
			if(env.getProperty("portalecredito.mode.debug").equals("true"))
			{
		    	String responsePrint = jsonMapper.writeValueAsString(response);
		    	this.log.debug("UserService: getRuoliDataTable -> PROCESS END WITH: " + responsePrint);
		    	appService.insertLog("debug", "UserService", "getRuoliDataTable", "PROCESS END WITH: " + responsePrint, "", "getRuoliDataTable");
			}
		}
		catch (Exception e)
		{
    		String stackTrace = ExceptionUtils.getStackTrace(e);
    		this.log.error("UserService: getRuoliDataTable -> " + stackTrace);
			appService.insertLog("error", "UserService", "getRuoliDataTable", "Exception", stackTrace, "getRuoliDataTable");
			
			throw new RuntimeException(e);
		}
   		
		return response;
    }
    
	@Override
	public RoleVoceMenuOverview getRoleVoceMenuDataTable(JsonNode payload) {
		this.log.info("UserService: getRoleVoceMenuDataTable -> START");
		appService.insertLog("info", "UserService", "getRoleVoceMenuDataTable", "START", "", "getRoleVoceMenuDataTable");

		RoleVoceMenuOverview response = new RoleVoceMenuOverview();
		response.setTotalCount(0);
		response.setLines(new ArrayList<RoleVoceMenu>());

		try {
			if (env.getProperty("portalecredito.mode.debug").equals("true"))
			{
				String requestPrint = jsonMapper.writeValueAsString(payload);
				this.log.debug("UserService: getRoleVoceMenuDataTable -> Object: " + requestPrint);
				appService.insertLog("debug", "UserService", "getRoleVoceMenuDataTable", "Object: " + requestPrint, "", "getRoleVoceMenuDataTable");
			}

			jsonMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

			PagedListFilterAndSort model = jsonMapper.treeToValue(payload, PagedListFilterAndSort.class);

			if (model.getFilters() == null)
				model.setFilters(new ArrayList<>());

			/// Escludo record eliminati logicamente
			// model.getFilters().add(new ListFilter("deleted", "eqbool", "0", null));
//
//			Specification<com.gamenet.cruscottofatturazione.entities.RoleVoceMenu> spec = new QuerySpecification<>();
//
//			for (ListFilter filter : model.getFilters())
//				spec = spec.and(new QuerySpecification<>(filter));

			PageRequest request = null;
			if (model.getSort() != null && !model.getSort().isEmpty()) {
				int index = 0;
				Sort sort = null;
				for (ListSort srt : model.getSort()) {
					if (index++ == 0)
						sort = Sort.by(SortUtils.translateSort(srt.getType()), srt.getName());
					else
						sort = sort.and(Sort.by(SortUtils.translateSort(srt.getType()), srt.getName()));
				}

				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE, sort);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize(), sort);
				}
			}
			else
			{
				if(model.getPagesize() == 0)
				{
					request = PageRequest.of(0, Integer.MAX_VALUE);
				}
				else
				{
					request = PageRequest.of(model.getIndex(), model.getPagesize());
				}
			}

//			Page<com.gamenet.cruscottofatturazione.entities.RoleVoceMenu> pages = roleVociMenuRepo.findAll(spec, request);
//			
//			if (pages != null && pages.getTotalElements() > 0)
//			{
//				response.setTotalCount((int) pages.getTotalElements());
//				response.setLines(convertRoleVoceMenuList(pages.getContent()));
//			}

			if (env.getProperty("portalecredito.mode.debug").equals("true"))
			{
				String responsePrint = jsonMapper.writeValueAsString(response);
				this.log.debug("UserService: getRoleVoceMenuDataTable -> PROCESS END WITH: " + responsePrint);
				appService.insertLog("debug", "UserService", "getRoleVoceMenuDataTable", "PROCESS END WITH: " + responsePrint, "", "getRoleVoceMenuDataTable");
			}
		}
		catch (Exception e)
		{
			String stackTrace = ExceptionUtils.getStackTrace(e);
			this.log.error("UserService: getRoleVoceMenuDataTable -> " + stackTrace);
			appService.insertLog("error", "UserService", "getRoleVoceMenuDataTable", "Exception", stackTrace, "getRoleVoceMenuDataTable");

			throw new RuntimeException(e);
		}

		return response;
	}
}
