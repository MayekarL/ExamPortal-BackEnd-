package com.exam.pojo;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.exam.entity.User;

public class UserDto {

	private int code;
	private String message;
	private List<String> roleName;
	private HttpStatus status;
	private List<UserRequest> userList;
	private UserRequest userRequest;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<UserRequest> getUserList() {
		return userList;
	}

	public void setUserList(List<UserRequest> userList) {
		this.userList = userList;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public UserRequest getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(UserRequest userRequest) {
		this.userRequest = userRequest;
	}

	public List<String> getRoleName() {
		return roleName;
	}

	public void setRoleName(List<String> roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserDto [code=" + code + ", message=" + message + ", status=" + status + ", userList=" + userList
				+ ", userRequest=" + userRequest + "]";
	}

}
