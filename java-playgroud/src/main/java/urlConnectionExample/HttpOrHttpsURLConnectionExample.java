package urlConnectionExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpOrHttpsURLConnectionExample {

	public static void main(String[] args) {
		String url ="https://www.naver.com";
		System.out.println(connectUrl(url));
	}
	
	public static String connectUrl(String strUrl) {
		if(strUrl.toUpperCase().indexOf("HTTPS")!=-1) {
			return connectHttpsUrl(strUrl);
		}else {
			return connectHttpUrl(strUrl);
		}
	}
	
	public static String connectHttpUrl(String strUrl) {
		URL url;
		HttpURLConnection con = null;
		String rtnval =null;
		try {
			url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			rtnval =getStringFromInputStream(con.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con.disconnect();
			}
		}
		return rtnval;
	}
	
	public static String connectHttpsUrl(String strUrl) {
		URL url;
		HttpsURLConnection con = null;
		String rtnval =null;
		try {
			url = new URL(strUrl);
			/*
			// get ssl protocol(using nmap) : cmd > nmap --script ssl -enum-ciphers www.abc.com -p443 -unprivileged
			SSLContext context = SSLContext.getInstance("TLSc1.2");
			context.init(null,null,null);
			SSLContext.setDefault(context);
			*/
			con = (HttpsURLConnection) url.openConnection();
			rtnval =getStringFromInputStream(con.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				con.disconnect();
			}
		}
		return rtnval;
	}
	
	public static String getStringFromInputStream(InputStream in) {
		BufferedReader bufferedReader = null;
		StringBuilder sb = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(in)); 
			String line =null;
			sb= new StringBuilder();
			while((line=bufferedReader.readLine())!=null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb==null?null:sb.toString();
	}
	
}
