package dao;

import java.util.List;

import entity.User;

public interface UserDao {
	public boolean addUser(User user);	//�����û�
	public User selectUserByNameAndPwd(String name,String password);	//�����û�����������û�
	public List<User> selectAllUsers();
}
