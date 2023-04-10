package example.processExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExample {

	public static void main(String[] args) {
		
		String[] commnadArgs= new String[3];
		String charset;
		if(isWindow()) {
			commnadArgs[0] ="cmd";
			commnadArgs[1] ="/c";
			commnadArgs[2] ="ipconfig";
			charset = "EUC-KR";
		}else {
			commnadArgs[0] ="bash";
			commnadArgs[1] ="-c";
			commnadArgs[2] ="ifconfig";
			charset = "UTF-8";
		}
		System.out.println( executeCommandLine(commnadArgs, charset));
	}
	
	public static String executeCommandLine(String[] commnadArgs, String charset) {
		ProcessBuilder processBuilder = null;
		Process process =null;
		try {
			processBuilder = new ProcessBuilder();
			process = processBuilder.command(commnadArgs)
								    .redirectErrorStream(true)
								    .start();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String rtnMsg = getProcessMesage(process, charset);
		process.destroy();
		return rtnMsg;
	}
	
	public static boolean isWindow() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}
	
	public static String getProcessMesage(Process process, String charset) {
		BufferedReader reader = null;
		StringBuilder sb = null;
		try {
			reader = new BufferedReader(new InputStreamReader(process.getInputStream(),charset));
			sb = new StringBuilder(bufferedReaderToString(reader));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sb==null?"":sb.toString();
	}
	
	public static String bufferedReaderToString(BufferedReader reader) {
		String line = "";
		StringBuilder sb = new StringBuilder();
		try {
			while((line=reader.readLine())!=null) {
				sb.append(System.lineSeparator());
				sb.append(line);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
