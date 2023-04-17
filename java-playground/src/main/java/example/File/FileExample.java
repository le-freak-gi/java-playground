package example.File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileExample {
	
	public void copyFile(File file , File newFile) throws Exception {
		if(file==null||!file.exists()) {
			return;
		}
        FileInputStream input=null;
        FileOutputStream output=null;
		try {
			input = new FileInputStream(file);
			output = new FileOutputStream(newFile);
			byte[] buf = new byte[1024];
			int readData;
			while ((readData = input.read(buf)) > 0) {
				output.write(buf, 0, readData);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
			if(output!=null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	public void delete(String path) {
		File file = new File(path);
		if(file.exists()) {
			if(file.isFile()) {
				if(!file.delete()) {
					System.out.println("failed "+file.getAbsolutePath());
				}
			}else if(file.isDirectory()) {
				for(String childFileName : file.list()) {
					String childFullPath = path+File.separator+childFileName;
					delete(childFullPath);
				}
				if(!file.delete()) {
					System.out.println("failed "+file.getAbsolutePath());
				}
			}
		}
	}
}
