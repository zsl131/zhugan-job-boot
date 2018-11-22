package com.zslin.wx.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 与微信平台进行交互的网络处理工具类
 * @author 钟述林
 *
 */
public class InternetTools {

	/**
	 * 处理get请求
	 * @param serverName url
	 * @param params 参数
	 * @return 返回结果
	 */
	public static String doGet(String serverName, Map<String, Object> params) {
		String result = null;
		int flag = 0;
		while(result==null && (flag++)<3) {
			try {
				URL url = new URL(rebuildUrl(serverName, params));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000);
				conn.connect();
				BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
				result = reader.readLine();
//				System.out.println("res::"+result);
			} catch (Exception e) {
				System.out.println("InternetTools.toGet 出现异常："+e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * 重新生成url
	 * @param serverName
	 * @param params
	 * @return
	 */
	private static String rebuildUrl(String serverName, Map<String, Object> params) {
		StringBuffer sb = new StringBuffer(serverName);
		if(serverName.indexOf("?")<0) {
			sb.append("?1=1");
		}
		if(params!=null) {
			for(String key : params.keySet()) {
				sb.append("&").append(key).append("=").append(params.get(key));
			}
		}
//		System.out.println("===url::"+sb.toString());
		return sb.toString();
	}

	public static String sendPost(String url, Map<String, Object> params) {
		StringBuffer sb = new StringBuffer(url.indexOf("?")>0?"":"?");
		for(String key : params.keySet()) {
			sb.append("&").append(key).append("=").append(params.get(key));
		}
		return sendPost(url, sb.toString());
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		//使用finally块来关闭输出流、输入流
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return result;
	}
}
