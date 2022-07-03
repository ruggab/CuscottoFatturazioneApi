package com.gamenet.cruscottofatturazione.services.interfaces;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.models.RoleUser;
import com.gamenet.cruscottofatturazione.models.RoleVoceMenu;
import com.gamenet.cruscottofatturazione.models.User;
import com.gamenet.cruscottofatturazione.models.VoceMenu;
import com.gamenet.cruscottofatturazione.models.response.RoleVoceMenuOverview;
import com.gamenet.cruscottofatturazione.models.response.RuoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.UpdateGenericResponse;
import com.gamenet.cruscottofatturazione.models.response.UtentiListOverview;

public interface UserService {

	public List<User> getUsers();
	
	public String loginUser(String username , String password);
	
	public Boolean loginUserByToken(String token);	
	
	public User getByUsername(String username);

	public User getUserById(Integer userId);

	public Boolean saveUser(User utente, String utenteUpdate);
	
	public UpdateGenericResponse changeUserPassword(Integer idUser, String passwordPrecedente, String passwordNuova, String utenteUpdate) throws BadCredentialsException;
	
	public Boolean deleteUser(Integer userId, String utenteUpdate);

	/***** RUOLI *****/
	public List<RoleUser> getRuoliUtente();

	public RoleUser getRoleById(Integer roleId);
	
	/***** VOCI MENU *****/

	public List<VoceMenu> getVociMenuList();

	public List<VoceMenu> getVociMenuByRoleId(Integer roleId, Boolean isAdmin);

	public Boolean deleteRole(Integer roleId, String utenteUpdate);

	public Boolean saveRole(RoleUser ruolo, String utenteUpdate);

	/***** VOCI MENU - ROLE *****/
	
	public RoleVoceMenu getRoleVoceMenuById(Integer idRoleVoceMenu);
	
	public Boolean saveRoleVoceMenu(RoleVoceMenu roleVoceMenu, String utenteUpdate);
	
	public Boolean deleteRoleVoceMenu(Integer roleVoceId, String utenteUpdate);
	
	/***** BUSINESS *****/
	public List<String> getAvaiableBusinessByUser(Integer idUser, Boolean isAdmin);

	/***** DATA TABLE LIST *****/
	public UtentiListOverview getUtentiDataTable(JsonNode payload);

	public RuoliListOverview getRuoliDataTable(JsonNode payload);
	
	public RoleVoceMenuOverview getRoleVoceMenuDataTable(JsonNode payload);

}