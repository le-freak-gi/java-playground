package example.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessExample {

	public static void main(String[] args) {
		ProcessExample processExample = new ProcessExample();
		String[] commandArgs = new String[3];
		String charset;
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			commandArgs[0] = "cmd";
			commandArgs[1] = "/c";
			commandArgs[2] = "ipconfig";
			charset="euc-kr";
		}else {
			commandArgs[0] = "bash";
			commandArgs[1] = "-c";
			commandArgs[2] = "ifconfig";
			charset="utf-8";
		}
		System.out.println(processExample.executeCommandLine(charset, commandArgs));
	}
	
	public String executeCommandLine(String charset, String... commandArgs) {
		ProcessBuilder processBuilder= null;
		Process process =  null;
		String message = null;
		try {
			processBuilder = new ProcessBuilder();
			process= processBuilder.command(commandArgs).redirectErrorStream(true).start();
			message = getProcessMessage( process,  charset);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(process!=null) {
				process.destroy();
			}
		}
		return message;
	}
	
	public String getProcessMessage(Process process, String charset) {
		BufferedReader reader = null;
		String message=null;
		try {
			reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
			message =bufferedReaderToString(reader);
		}catch (Exception e) {
			e.getStackTrace();
		}finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return message;
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
