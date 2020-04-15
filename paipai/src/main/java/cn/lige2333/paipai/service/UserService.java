package cn.lige2333.paipai.service;

import cn.lige2333.paipai.model.Log;
import cn.lige2333.paipai.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.dao.UserMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userMapper.loadUserByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不对");
		}
		return user;
	}

	public List<User> getAll(){
		return userMapper.getAll();
	}
	public void setStatus(Integer id,Integer status){
		userMapper.setStatus(id,status);
	}
	public void insertUser(User user) {
		if(!VerifyUtils.isUsername(user.getUsername())){
			throw new RuntimeException("用户名输入不符合规定！");
		}
		if(!VerifyUtils.isPassword(user.getRealPassword())){
			throw new RuntimeException("密码输入不符合规定！");
		}
		if(!VerifyUtils.isMobile(user.getTelephone())){
			throw new RuntimeException("手机号不符合规范！");
		}
		synchronized (user.getUsername().trim().intern()){
			if(checkUsername(user.getUsername())){
				throw new RuntimeException("用户名已经存在！");
			}
			try{
				user.setStatus(0);
				userMapper.insertUser(user);
			}catch (Exception e){
				throw new RuntimeException("注册失败！");
			}
		}


	}
	@Log(module = "前台",description = "检查用户名")
	public Boolean checkUsername(String username) {
		Example exm = new Example(User.class);
		Example.Criteria criteria = exm.createCriteria();
		criteria.andEqualTo("username", username);
		return userMapper.selectCountByExample(exm)>0;
	}
	public Integer getUserId(String username) {
		return userMapper.loadUserByUsername(username).getId();
	}

	public void updateUser(User user) {
		if(!VerifyUtils.isPassword(user.getRealPassword())){
			throw new RuntimeException("密码输入不符合规定！");
		}
		if(!VerifyUtils.isMobile(user.getTelephone())){
			throw new RuntimeException("手机号不符合规范！");
		}
		try{
			userMapper.updateUser(user);
		}catch (Exception e){
			throw new RuntimeException("注册失败！");
		}
	}
}