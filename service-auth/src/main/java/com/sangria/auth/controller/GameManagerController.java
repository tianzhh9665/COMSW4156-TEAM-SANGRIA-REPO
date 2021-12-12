package com.sangria.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sangria.auth.dto.ManagerDeleteDTO;
import com.sangria.auth.dto.ManagerLoginDTO;
import com.sangria.auth.dto.ManagerRegDTO;
import com.sangria.auth.dto.ResponseDTO;
import com.sangria.auth.service.GameManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 
 * @author Steven Huang
 *
 */
@Api(tags = "Game Manager Module")
@RestController
@RequestMapping("/gameManager")
public class GameManagerController extends BaseController{
	
	@Autowired
	GameManagerService gameManagerService;
	
	/**
	 * Register game manager
	 * @param dto
	 * @return ResponseDTO
	 */
	@ApiOperation(value = "Register a new game Manager")
	@PostMapping(value = "/register")
	public ResponseDTO register(@RequestBody ManagerRegDTO dto) {
		
		if(StringUtils.isBlank(dto.getUsername())) {
			return renderFail("ERROR: parameter username can not be empty");
		}
		if(StringUtils.isBlank(dto.getPassword())) {
			return renderFail("ERROR: parameter password can not be empty");
		}
		if(StringUtils.isBlank(dto.getGameName())) {
			return renderFail("ERROR: parameter gameName can not be empty");
		}
		if(dto.getUsername().length() > 16) {
			return renderFail("ERROR: username can not be longer than 16 characters");
		}
		if(dto.getPassword().length() > 16) {
			return renderFail("ERROR: password can not be longer than 16 characters");
		}
		if(dto.getGameName().length() > 32) {
			return renderFail("ERROR: gameName can not be longer than 16 characters");
		}
		
		ResponseDTO result = gameManagerService.register(dto);
		return result;
	}
	/**
	 * game manager login
	 * @param dto
	 * @return ResponseDTO
	 */
	@ApiOperation(value = "Game manager login")
	@PostMapping(value = "/login")
	public ResponseDTO login(@RequestBody ManagerLoginDTO dto) {
		
		if(StringUtils.isBlank(dto.getUsername())) {
			return renderFail("ERROR: parameter username can not be empty");
		}
		if(StringUtils.isBlank(dto.getPassword())) {
			return renderFail("ERROR: parameter password can not be empty");
		}
		
		ResponseDTO result = gameManagerService.login(dto);
		return result;
	}
	/**
	 * token authentication, verify whether given token is valid
	 * @param token
	 * @return ResponseDTO
	 */
	/*
	@GetMapping(value = "/token")
	public ResponseDTO token(String token) {
		
		if(StringUtils.isBlank(token)) {
			return renderFail("ERROR: authentication failed -- token can not be empty");
		}
		
		ResponseDTO result = gameManagerService.verifyToken(token);
		return result;
	}*/

	/**
	 * return the manager info, game info, inventory info according to the token
	 * @param token
	 * @return ResponseDTO
	 */
	@ApiOperation(value = "Query game manager and its managed game information")
	@GetMapping(value = "/info")
	public ResponseDTO info(String token) {

		if(StringUtils.isBlank(token)) {
			return renderFail("ERROR: authentication failed -- token can not be empty");
		}

		ResponseDTO result = gameManagerService.info(token);
		return result;
	}
	
	/**
	 * game manager delete
	 * @param dto
	 * @return ResponseDTO
	 */
	@ApiOperation(value = "Game manager delete")
	@PostMapping(value = "/delete")
	public ResponseDTO delete(@RequestBody ManagerDeleteDTO dto) {

		if(StringUtils.isBlank(dto.getToken())) {
			return renderFail("ERROR: parameter token can not be empty");
		}

		ResponseDTO result = gameManagerService.delete(dto);
		return result;
	}
	
	/**
	 * allow manager logout, aka set token value to null if the given token is valid
	 * @param dto
	 * @return ResponseDTO
	 */
	@ApiOperation(value = "Game manager logout")
	@PostMapping(value = "/logout")
	public ResponseDTO logout(@RequestBody ManagerDeleteDTO dto) {

		if(StringUtils.isBlank(dto.getToken())) {
			return renderFail("ERROR: authentication failed -- token can not be empty");
		}

		ResponseDTO result = gameManagerService.logout(dto.getToken());
		return result;
	}
	
}
