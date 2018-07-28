package service;

import java.util.List;

import entity.User;

public interface UserService {
	public boolean regist(User user);
	public User login(String name,String password);
	public List<User> FindAllUsers();
}
