package cn.lige2333.paipai.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;
import java.util.Random;

public class CodeUtils {

    // 短信应用 SDK AppID
    private static final int APP_ID = 1400055498; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private static final String APP_KEY = "d8f288bf8f3e7bbaca6fef968d83238c";
    // 短信模板 ID，需要在短信应用中申请
    private static final int TEMPLATE_ID = 66705; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    private static final String SMS_SIGN = "沁文管会之家"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

    /**
     * 生成6位随机验证码
     *
     * @param
     * @return
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static void sendMessage(String phoneNumber,String code){
        try {
            String[] params = {code};
            SmsSingleSender ssender = new SmsSingleSender(APP_ID, APP_KEY);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                    TEMPLATE_ID, params, SMS_SIGN, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            e.printStackTrace();
           throw new RuntimeException("HTTP请求错误");
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
            throw new RuntimeException("Json解析错误");
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
            throw new RuntimeException("网络IO错误");
        }
    }
}
