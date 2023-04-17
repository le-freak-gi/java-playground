package example.hwp;

import java.io.File;
import java.util.Calendar;

import example.File.FileExample;
import example.xml.XmlExample;
import example.zip.ZipExample;

public class HwpExample {

	public static void main(String[] args) {
		
		// 파일 복사하여 다름이름으로 저장
		String hwpFilePath = "C:/\\zipTest/\\test2.hwpx";
		String newFilePath = "C:/\\zipTest/\\newTest.zip";
		String unzipFilePath = "C:/\\zipTest/\\newTest";
		String contentXmlPath = unzipFilePath+ File.separator+"Contents"+ File.separator+"section0.xml";
		String newzipFilePath = "C:/\\zipTest/\\newTestHwp.hwpx";
		File hwpFile = new File(hwpFilePath);
		File newFile = new File(newFilePath);
		FileExample fileExample = new FileExample();
		ZipExample zipExample = new ZipExample();
		HwpExample hwpExample = new HwpExample();
		
		try {
			fileExample.copyFile(hwpFile , newFile);
			zipExample.unzip(newFile.getAbsolutePath(), unzipFilePath);
			hwpExample.setHwpContent(contentXmlPath);
			zipExample.zip(unzipFilePath, newzipFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fileExample.delete(newFilePath);
			fileExample.delete(unzipFilePath);
		}
	}
	
	public void setHwpContent(String contentXmlPath) throws Exception {
		XmlExample xmlExample = new XmlExample(contentXmlPath);
		String text ="문서 제목 [HY헤드라인M 22pt] "+Calendar.getInstance().getTime().toString();
		String id = "documentTitle";
		xmlExample.setXmlContentById(id, text);
	}
	
}
