package cn.lige2333.paipaiadmin.service;

import cn.lige2333.paipaiadmin.Entity.User;
import cn.lige2333.paipaiadmin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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


	public Boolean checkUsername(String username) {
		Example exm = new Example(User.class);
		Example.Criteria criteria = exm.createCriteria();
		criteria.andEqualTo("username", username);
		return userMapper.selectCountByExample(exm)>0;
	}

}