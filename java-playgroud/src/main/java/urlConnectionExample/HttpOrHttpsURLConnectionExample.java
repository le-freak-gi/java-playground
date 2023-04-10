package urlConnectionExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpOrHttpsURLConnectionExample {

	public static void main(String[] args) {
		String url ="https://www.naver.com";
		if(url.toUpperCase().indexOf("HTTPS")!=-1) {
			System.out.println(connectHttpsUrl(url));
		}else {
			System.out.println(connectHttpUrl(url));
		}
	}
	
	public static String connectHttpUrl(String strUrl) {
		URL url;
		HttpURLConnection con = null;
		BufferedReader bufferedReader = null;
		StringBuilder sb = null;
		try {
			url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			String line =null;
			sb= new StringBuilder();
			while((line=bufferedReader.readLine())!=null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
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
			if(con!=null) {
				con.disconnect();
			}
		}
		return sb==null?null:sb.toString();
	}

	public static String connectHttpsUrl(String strUrl) {
		URL url;
		HttpsURLConnection con = null;
		BufferedReader bufferedReader = null;
		StringBuilder sb = null;
		try {
			url = new URL(strUrl);
			/*
			// get ssl protocol(using nmap) : cmd > nmap --script ssl -enum-ciphers www.abc.com -p443 -unprivileged
			SSLContext context = SSLContext.getInstance("TLSc1.2");
			context.init(null,null,null);
			SSLContext.setDefault(context);
			*/
			con = (HttpsURLConnection) url.openConnection();
			bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream())); 
			String line =null;
			sb= new StringBuilder();
			while((line=bufferedReader.readLine())!=null) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
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
			if(con!=null) {
				con.disconnect();
			}
		}
		return sb==null?null:sb.toString();
	}
	
}
