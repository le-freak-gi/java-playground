package example.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipExample {

	public static void main(String[] args) {
        String fileZipPath = "C://zipTest/test3.zip";
        String destDirPath = "C://zipTest/test3";
        ZipExample zipExample = new ZipExample();
        try {
			zipExample.unzip(fileZipPath, destDirPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        String targetPath = "C://zipTest/test3";
        String zipFileName = "C://zipTest/test4.zip";
        try {
			zipExample.zip( targetPath,  zipFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	
	public void unzip(String fileZipPath, String destDirPath) throws Exception {
		if(!new File(fileZipPath).exists()) {
			throw new FileNotFoundException(fileZipPath);
		}
        byte[] buffer = new byte[1024];
        ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new FileInputStream(fileZipPath));
			ZipEntry zipEntry = zis.getNextEntry();
			File destDir = new File(destDirPath);
			while (zipEntry != null) {
				File newFile = newFile(destDir, zipEntry);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					writeInputStream(newFile, zis, buffer);
				}
				zipEntry = zis.getNextEntry();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(zis!=null) {
				try {
					zis.closeEntry();
					zis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	public void writeInputStream(File newFile, ZipInputStream zis, byte[] buffer) throws Exception {
		File parent = newFile.getParentFile();
		if (!parent.isDirectory() && !parent.mkdirs()) {
			throw new IOException("Failed to create directory " + parent);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
	public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
	    File destFile = new File(destinationDir, zipEntry.getName());
	    String destDirPath = destinationDir.getCanonicalPath();
	    String destFilePath = destFile.getCanonicalPath();
	    if (!destFilePath.startsWith(destDirPath + File.separator)) {
	        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
	    }
	    return destFile;
	}
	
	public void zip(String targetPath, String zipFileName) throws Exception {
		if(!new File(targetPath).exists()) {
			throw new FileNotFoundException(targetPath);
		}
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
		try {
			fos = new FileOutputStream(zipFileName);
			zipOut = new ZipOutputStream(fos);
			File fileToZip = new File(targetPath);
			if(fileToZip.isDirectory()) {
				for(String fileName : fileToZip.list()) {
					String filePath = targetPath+File.separator+fileName;
					File file = new File(filePath);
					zipFile(file, fileName, zipOut);
				}
			}else {
				zipFile(fileToZip, fileToZip.getName(), zipOut);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(zipOut!=null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}
	
    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws Exception {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
        	try {
        		if (fileName.endsWith("/")) {
        			zipOut.putNextEntry(new ZipEntry(fileName));
        			zipOut.closeEntry();
        		} else {
        			zipOut.putNextEntry(new ZipEntry(fileName + "/"));
        			zipOut.closeEntry();
        		}
        	} catch(Exception e) {
        		e.printStackTrace();
        		throw e;
        	} 
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        writeOutputStream( fileToZip,  fileName,  zipOut);
    }
	
    public void writeOutputStream(File fileToZip, String fileName, ZipOutputStream zipOut) throws Exception{
        FileInputStream fis=null;
		try {
			fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
    }
}
