package dao;

import java.util.List;

import entity.User;

public interface UserDao {
	public boolean addUser(User user);	//增加用户
	public User selectUserByNameAndPwd(String name,String password);	//根据用户名密码查找用户
	public List<User> selectAllUsers();
}
