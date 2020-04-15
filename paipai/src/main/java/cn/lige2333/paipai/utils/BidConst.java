package cn.lige2333.paipai.utils;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidConst {
	//存储精度
	public static final int STORE_SCALE=4;
	//运算精度
	public static final int CAL_SCALE=8;
	//显示精度
	public static final int DISPLAY_SCALE=2;
	//定义系统级别的0
	public static final BigDecimal ZERO = new BigDecimal("0.0000");
	//定义授信额度
	public static final BigDecimal INIT_BORROW_LIMIT = new BigDecimal("5000.0000");
	//定义默认管理员初始用户名密码
	public static final String DEFAULT_ADMIN_NAME="admin";
	public static final String DEFAULT_ADMIN_PASSWORD="1111";
	//手机验证码有效期
	public static final int VERIFYCODE_VAILDATE_SECOND=900;
	//验证邮箱有效期
	public static final int VERIFYEMAIL_VAILDATE_DAY=5;
	//存储图片的公共文件夹地址
	public static final String PUBLIC_IMG_SHARE_PATH="/www/upload/";

}
