package cn.lige2333.paipai.service;

import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.utils.BidConst;
import cn.lige2333.paipai.utils.DateUtil;
import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.entity.Code;
import cn.lige2333.paipai.dao.CodeMapper;

import java.util.Date;

@Transactional
@Service
public class CodeService {
	@Autowired
	private CodeMapper codeMapper;

	public CodeMapper getCodeMapper() {
		return codeMapper;
	}

	public void saveCode(String rancode,String telephone) {
		Code code = new Code();
		code.setRancode(rancode);
		code.setTelephone(telephone);
		code.setExpireDate(DateUtils.addSeconds(new Date(), BidConst.VERIFYCODE_VAILDATE_SECOND));
		codeMapper.insert(code);
	}

	public void checkCode(User user, String code) {
		Code code1 = codeMapper.selectByTelephoneAndDate(user.getTelephone());
		if(code1==null){
			throw new RuntimeException("该手机没有接收验证码！");
		}
		if(!code.equals(code1.getRancode())){
			throw new RuntimeException("验证码不正确！");
		}
		if(DateUtil.secondsBetweenNotAbs(new Date(),code1.getExpireDate())>=0){
			throw new RuntimeException("验证码过期了！");
		}
	}
}