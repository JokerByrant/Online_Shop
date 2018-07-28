package service.imp;

import java.util.List;

import dao.UserDao;
import dao.imp.UserDaoImp;
import entity.User;
import service.UserService;

public class UserServiceImp implements UserService{
	UserDao ud = new UserDaoImp();
	
	@Override
	public boolean regist(User user) {
		return ud.addUser(user);
	}

	@Override
	public User login(String name, String password) {
		return ud.selectUserByNameAndPwd(name, password);
	}

	@Override
	public List<User> FindAllUsers() {
		return ud.selectAllUsers();
	}

}
