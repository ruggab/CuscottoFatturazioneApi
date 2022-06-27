package com.gamenet.cruscottofatturazione.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.gamenet.cruscottofatturazione.models.GruppoUtenti;
import com.gamenet.cruscottofatturazione.models.RoleUser;
import com.gamenet.cruscottofatturazione.models.RoleVoceMenu;
import com.gamenet.cruscottofatturazione.models.User;
import com.gamenet.cruscottofatturazione.models.VoceMenu;
import com.gamenet.cruscottofatturazione.models.request.ChangePasswordRequest;
import com.gamenet.cruscottofatturazione.models.request.DeleteRequest;
import com.gamenet.cruscottofatturazione.models.request.RoleMenuRequest;
import com.gamenet.cruscottofatturazione.models.request.RoleSaveRequest;
import com.gamenet.cruscottofatturazione.models.request.RoleVoceMenuSaveRequest;
import com.gamenet.cruscottofatturazione.models.request.UserBusinessRequest;
import com.gamenet.cruscottofatturazione.models.request.UserSaveRequest;
import com.gamenet.cruscottofatturazione.models.response.RoleVoceMenuOverview;
import com.gamenet.cruscottofatturazione.models.response.RuoliListOverview;
import com.gamenet.cruscottofatturazione.models.response.UpdateGenericResponse;
import com.gamenet.cruscottofatturazione.models.response.UtentiListOverview;
import com.gamenet.cruscottofatturazione.services.interfaces.UserAuthenticationService;
import com.gamenet.cruscottofatturazione.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	private final UserAuthenticationService userAuthService;
	
	@GetMapping("/getUserList")
	public List<User> getUserList() 
	{
		return userService.getUsers();
	}
	
	@PostMapping("/getUserById")
	public User getUserById(@RequestBody Integer userId) 
	{
		return userService.getUserById(userId);
	}
	
	@PostMapping("/saveUser")
	public Boolean saveUser(@RequestBody UserSaveRequest utenteReq) 
	{
		return userService.saveUser(utenteReq.getUser(), utenteReq.getUtenteUpdate());
	}
	
	@PostMapping("/changeUserPassword")
	public UpdateGenericResponse changeUserPassword(@RequestBody ChangePasswordRequest chPassReq) 
	{
		return userService.changeUserPassword(chPassReq.getIdUtente(), chPassReq.getPasswordPrecedente(), chPassReq.getPasswordNuova(), chPassReq.getUtenteUpdate());
	}
	
	@PostMapping("/deleteUser")
	public Boolean deleteUser(@RequestBody DeleteRequest utenteReq) 
	{
		return userService.deleteUser(utenteReq.getIdEntity(), utenteReq.getUtenteUpdate());
	}
	
	@PostMapping("/logout")
	public Boolean logout(@RequestBody Integer userId) 
	{
		return userAuthService.logout(userId);
	}
	
	/***** RUOLI *****/

	@GetMapping("/getRuoliList")
	public List<RoleUser> getRuoliList()
	{
		return userService.getRuoliUtente();
	}
	
	@PostMapping("/getRoleById")
	public RoleUser getRoleById(@RequestBody Integer roleId) 
	{
		return userService.getRoleById(roleId);
	}
	
	/***** VOCI MENU *****/
	
	@GetMapping("/getVociMenuList")
	public List<VoceMenu> getVociMenuList() 
	{
		return userService.getVociMenuList();
	}
	
	@PostMapping("/getVociMenuByRoleId")
	public List<VoceMenu> getVociMenuByRoleId(@RequestBody RoleMenuRequest menuReq) 
	{
		return userService.getVociMenuByRoleId(menuReq.getIdRole(), menuReq.getIsAdmin());
	}
	
	@PostMapping("/saveRole")
	public Boolean saveRole(@RequestBody RoleSaveRequest roleReq) 
	{
		return userService.saveRole(roleReq.getRole(), roleReq.getUtenteUpdate());
	}
	
	@PostMapping("/deleteRole")
	public Boolean deleteRole(@RequestBody DeleteRequest roleReq) 
	{
		return userService.deleteRole(roleReq.getIdEntity(), roleReq.getUtenteUpdate());
	}
	
	/***** GRUPPI *****/
	@GetMapping("/getGruppiList")
	public List<GruppoUtenti> getGruppiList()
	{
		return userService.getGruppiUtente();
	}
	
	/***** BUSINESS *****/
	@PostMapping("/getAvaiableBusinessByUser")
	public List<String> getAvaiableBusinessByUser(@RequestBody UserBusinessRequest userBusReq)
	{
		return userService.getAvaiableBusinessByUser(userBusReq.getIdUser(), userBusReq.getIsAdmin());
	}
	
	/***** VOCI MENU - RUOLI *****/
	
	@PostMapping("/getRoleVoceMenuById")
	public RoleVoceMenu getRoleVoceMenuById(@RequestBody Integer idRoleVoceMenu) 
	{
		return userService.getRoleVoceMenuById(idRoleVoceMenu);
	}
	
	@PostMapping("/saveRoleVoceMenu")
	public Boolean saveRoleVoceMenu(@RequestBody RoleVoceMenuSaveRequest roleVoceMenuReq) 
	{
		return userService.saveRoleVoceMenu(roleVoceMenuReq.getRoleVoceMenu(), roleVoceMenuReq.getUtenteUpdate());
	}
	
	@PostMapping("/deleteRoleVoceMenu")
	public Boolean deleteRoleVoceMenu(@RequestBody DeleteRequest roleVoceMenuReq) 
	{
		return userService.deleteRoleVoceMenu(roleVoceMenuReq.getIdEntity(), roleVoceMenuReq.getUtenteUpdate());
	}

	/***** DATA TABLE LIST *****/
	@PostMapping("/getUtentiDataTable")
	public UtentiListOverview getUtentiDataTable(@RequestBody JsonNode payload) 
	{
		return userService.getUtentiDataTable(payload);
	}

	@PostMapping("/getRuoliDataTable")
	public RuoliListOverview getRuoliDataTable(@RequestBody JsonNode payload) 
	{
		return userService.getRuoliDataTable(payload);
	}

	@PostMapping("/getRoleVoceMenuDataTable")
	public RoleVoceMenuOverview getRoleVoceMenuDataTable(@RequestBody JsonNode payload) 
	{
		return userService.getRoleVoceMenuDataTable(payload);
	}
}
