package cn.lige2333.aliserver.config;
import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101200672281";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDwZMPkwlfqUSLVacFdume/jR8CoaffDpYhrUajJzHOT1OcBlIiMJ8CrnD3Z1nePp2YkVXMKVYI4A3wEZrpKjHwB2RVKe8ogmrX2B6oKKokjIa6h7e4hmZ3F0uTfWFF0cDs72vHmu7ZEHH89I5BVKE84LHxaImIGoOyQ2Pl/W24vcHLxNGMSnC8jqFcHkgBlEhBUCmmQKyg2r1/t9C1KuZ/5lWQBgx8mouSf6d3QOIMdetrUzofMO3OGsNDY2jYYvHeedzB2rACtyvj3gV9v3LzRiN+ExANzgWYYfEDKbdY1408D6UjstmnA/OhvPLFIHK/G2BuyuFhPZQp9Q39OJ5AgMBAAECggEAdYlaB6rD0fLOkv314YoTHyLm70D0l5Ha+t4w8V4+/fqyFicqZuqZlM58qZ4prQTfanyAHyfmWJpS52k/aQebCZb4yY0blct3lMmg58QlVvwUdq4km/BXpkpPVd92BySc1VCjBnW85Y6qoBWClBuLYc9Z+5zy6dBAzK/OySpmqJ7I0umS0DLJ29v7TZvlwjTbZY7n1Uxg/FSlqS1d+2YDsgURSVADWlD1qX0DM9VINr5ZCsPQsX3MQHHKzvmrKyH/5+0Mal0AzZdXrWIKYSubKa7bRF+ZQUKD/pL0mf5vM98KzarvfEIdKy2L4uNoZ+uQOpfpe4fsdmCGaaRoN5PQAQKBgQC4OTNOcQ/1rl/65SZbF4R2m+wvjjCji2Yw/bdytBzm9wP/8Jl2jkDSLMFUtSitkmy72cekqx1KRc9Ow1V+q6JzTHjNxzOvuKg9c8sI66fd9GFIolR4s/siPTrpR8ljptvRtstAlH4vpKNHkaXUIpxIu79wBm/yDf2CvfLM5ZiA8QKBgQC3Fy+Wnr0LGd7LAQLZ8Wkz8PIN/ol7jyEAP+/1IvKfZkW1W7JYl2oP+S9Z93MydPh2udk008x/edONgHyIbl7suC4hRnqQqoURjRCOQgiFDioS9DWzvJhC9lXlVGKb+B0QJnlbbdebsxJ2naK4etfRw4meQTUwyXFTlML2Yn36CQKBgGBOnvDSEIradxi9tNiIKdqr1sRhrDHDTVAtQzr2x+lCl1e0mzWsVlzJwuQatMJQuwFVb5Y5Ul2isya03TwUVB+8iabiDbY+bc16fFkUgTR6bmU/X7OeO5qBsguEWNb8wwwWaJlBR9p2Ulm23Z9R3Kv1YrYY12sOP0qbLJRLLvexAoGAM/cvuZdaXpE8K/AgiA0959EmUpSBHwGzwydiivgdVuKnPF9HUl+/acM3DljuaE7Myp3bKWRH8ZJ5Wfsy3WI+LP/lj+o6FV9gn2ejYE0br/AiPvjzWQ1ZRgA2xv1Zc51LWm8hGLMnc/iyb9oz+hpbsRzHrZQibbNvJ+LcJyQzT0kCgYBfkkIqIz1QTGFqhjC2gq/1NvJmewkFYYcNqdPfup8k0KvL8Q6/esBhXMSC1WwpCF6LzVh8dhuY30aZ/V9u5HIx4E8eDr4jp8WTsaei14yhhNVY+k5FJ6GF+J1bu6Uio82oAYI/b0nKqinWDyhQYyHaeWtdtZEiJRklKm++BCrIeg==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA+rmWhYH8wchm/2gyVxAfob0l2Wz5nX80K4elg6t431Ei9pnk2f9nOGqvqUzTQKiZEts4fqIXeJy4qs7IlqBVo7QiliE5MvYkBUOIcgwsFQav8cp/rZLhc66B94QHsESFL7phF0cl9N6oK7QcMuRr8mvT3jvZn4d5tCGEHVNKvCBYuaOvOaCznYH01O+aKBDriooL/9nFIbJ6B1Ss6Qs3HdwIGLMCOm/FBxUSTb9MgvRx07+UYxsGbSdhNkXs9UC0WvaMlstjjCFpFRoZ4RQV8QZuW5SBGzNZida7kYgeYUzCx+pFe0Xq8uJoMAlDRMQIF+O2+PdJT/ZE0kdgy9PBdQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://122.51.138.251:9876/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://122.51.138.251:9876/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

