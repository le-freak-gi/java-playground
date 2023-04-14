package example.urlConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
public class UrlConnectionExample {

	public static void main(String[] args) {
		String url ="http://www.google.com";
		String contentType = "application/x-www-form-urlencoded";
		Map<String,Object> params = new HashMap<String, Object>();
		UrlConnectionExample urlConnectionExample = new UrlConnectionExample();
		
		System.out.println(urlConnectionExample.getUrlResponse(url, HttpConstantEnumExample.GET.value(),contentType, params));
	}
	
	public String getUrlResponse(String strUrl, String httpMethod, String contentType, Map<String,Object> params) {
		URL url=null;
		byte[] postDataBytes =null;
		if(params!=null&&!params.isEmpty()) {
			if(httpMethod.toUpperCase().equals(HttpConstantEnumExample.GET.value())) {
				strUrl+="?"+makeStringMethodParam(params);
			}else {
				try {
					postDataBytes = makeStringMethodParam(params).toString().getBytes("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return getUrlResponse(strUrl, url, httpMethod, contentType, postDataBytes);
	}
	
	public String getUrlResponse(String strUrl, URL url, String httpMethod, String contentType, byte[] postDataBytes) {
		if(url!=null) {
			if(strUrl.toUpperCase().indexOf(HttpConstantEnumExample.HTTPS.value())!=-1) {
				return getHttpsUrlResponse(url, httpMethod, contentType, postDataBytes);
			}else {
				return getHttpUrlResponse(url, httpMethod, contentType, postDataBytes);
			}
		}else {
			return null;
		}
	}
	
	public String makeStringMethodParam(Map<String,Object> params) {
		if(params!=null&&!params.isEmpty()) {
			StringBuilder stringBuilder = new StringBuilder();
			for(String key : params.keySet()) {
				if(stringBuilder.length() != 0) {
					 stringBuilder.append('&');
				}
				try {
					stringBuilder.append(key).append("=").append(URLEncoder.encode((String) params.get(key),"utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return stringBuilder.toString();
		}
		return "";
	}
	
	public String getHttpUrlResponse(URL url, String httpMethod, String contentType, byte[] postDataBytes) {
		String rtnVal = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod(httpMethod.toUpperCase());
			conn.setRequestProperty(HttpConstantEnumExample.CONTENT_TYPE.value(), contentType);
			if(postDataBytes!=null&&postDataBytes.length>0) {
				conn.setRequestProperty(HttpConstantEnumExample.CONTENT_LENGTH.value(), String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes);
			}
			rtnVal =bufferedReaderToString(new BufferedReader(new InputStreamReader(conn.getInputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}
		return rtnVal;
	}
	
	public String getHttpsUrlResponse(URL url, String httpMethod, String contentType, byte[] postDataBytes) {
		String rtnVal = null;
		HttpsURLConnection conn = null;
		try {
			conn = (HttpsURLConnection)url.openConnection();
			conn.setRequestMethod(httpMethod.toUpperCase());
			conn.setRequestProperty(HttpConstantEnumExample.CONTENT_TYPE.value(), contentType);
			if(postDataBytes!=null&&postDataBytes.length>0) {
				conn.setRequestProperty(HttpConstantEnumExample.CONTENT_LENGTH.value(), String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes);
			}
			rtnVal =bufferedReaderToString(new BufferedReader(new InputStreamReader(conn.getInputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn!=null) {
				conn.disconnect();
			}
		}
		return rtnVal;
	}
	
	public String bufferedReaderToString(BufferedReader reader) {
		String line="";
		StringBuilder stringBuilder = new StringBuilder();
		try {
			while((line=reader.readLine())!=null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

}

enum HttpConstantEnumExample {
	GET("GET"), POST("POST"), CONTENT_TYPE("Content-Type"), CONTENT_LENGTH("Content-Length"), HTTPS("HTTPS");
	private String value;
	private HttpConstantEnumExample(String value) {
		this.value=value;
	}
	public String value() {
		return this.value;
	}
	
}