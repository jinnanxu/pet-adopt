package pet.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 图像识别工具类
 * @author xujinnan
 *
 */
public class ImageRecognitionUtil {

		/**
		 * 通过图片URL请求图像识别接口，获取置信度最高的一项
		 * @param imageUrl
		 * @return 返回动物名称
		 */
	    public static String animal(String imageUrl) {
	        // 请求url
	        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
	        try {
//	            String imgStr = "http://118.25.89.125:18080/images/20210327092745fvqqk.jpg";
				String imgParam = URLEncoder.encode(imageUrl , "UTF-8");
	            String param = "url="+imgParam;
	            
	            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
	            String accessToken = getAuth();

	            String result = HttpUtil.post(url, accessToken, param);
	            JSONObject json = JSONObject.parseObject(result);
	            /*
	             * 返回报文结构示例
	             * {
				    "result": [
					        {"score": "0.985318",  "name": "金毛犬"}, 
					        {"score": "0.00703572",  "name": "拉布拉多"}, 
					        {"score": "0.00125275", "name": "霍夫瓦尔特犬"}
				        ]
				     }
	             */
	            JSONArray resultArray = json.getJSONArray("result");
	            JSONObject first = resultArray.getJSONObject(0);//置信度最高的动物类别
	            System.out.println(result);
	            return first.getString("name");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    /**
	     * 获取权限token
	     * @return 返回示例：
	     * {
	     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
	     * "expires_in": 2592000
	     * }
	     */
	    public static String getAuth() {
	        // 官网获取的 API Key 更新为你注册的
	        String clientId = "fgGGmwGqjdU7qf7HDzsFDPLI";
	        // 官网获取的 Secret Key 更新为你注册的
	        String clientSecret = "KqEIY5r2tP0LtbXpOLY3eNnjWuYpPmjA";
	        return getAuth(clientId, clientSecret);
	    }

	    /**
	     * 获取API访问token
	     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
	     * @param ak - 百度云官网获取的 API Key
	     * @param sk - 百度云官网获取的 Securet Key
	     * @return assess_token 示例：
	     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
	     */
	    public static String getAuth(String ak, String sk) {
	        // 获取token地址
	        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
	        String getAccessTokenUrl = authHost
	                // 1. grant_type为固定参数
	                + "grant_type=client_credentials"
	                // 2. 官网获取的 API Key
	                + "&client_id=" + ak
	                // 3. 官网获取的 Secret Key
	                + "&client_secret=" + sk;
	        try {
	            URL realUrl = new URL(getAccessTokenUrl);
	            // 打开和URL之间的连接
	            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
	            connection.setRequestMethod("GET");
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.err.println(key + "--->" + map.get(key));
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String result = "";
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	            /**
	             * 返回结果示例
	             */
	            System.err.println("result:" + result);
//	            JSONObject jsonObject = new JSONObject(result);
	            JSONObject jsonObject = JSONObject.parseObject(result);
	            String access_token = jsonObject.getString("access_token");
	            return access_token;
	        } catch (Exception e) {
	            System.err.printf("获取token失败！");
	            e.printStackTrace(System.err);
	        }
	        return null;
	    }

	    public static void main(String[] args) {
	    	ImageRecognitionUtil.animal("http://118.25.89.125:18080/images/20210327093229diq10.jpg");
	    }
	
}
