/**
 * 
 */
package com.github.vole.common.fs.oss;

import com.github.vole.common.fs.local.FileUtils;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadObject {

	private String fileName;
	private String mimeType;
	private String catalog;
	private String url;
	private byte[] bytes;
	private File file;
	private InputStream inputStream;
	private Map<String, Object> metadata = new HashMap<String, Object>();

	public UploadObject(String filePath) {
		if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
			this.url = filePath;
			this.fileName = FileUtils.parseFileName(this.url);
		} else {
			this.file = new File(filePath);
			this.fileName = file.getName();
		}
	}

	public UploadObject(File file) {
		this.fileName = file.getName();
		this.file = file;
	}

	public UploadObject(String fileName, File file) {
		this.fileName = fileName;
		this.file = file;
	}

	public UploadObject(String fileName, InputStream inputStream, String mimeType) {
		this.fileName = fileName;
		this.inputStream = inputStream;
		this.mimeType = mimeType;
	}

	public UploadObject(String fileName, byte[] bytes, String mimeType) {
		this.fileName = fileName;
		this.bytes = bytes;
		this.mimeType = mimeType;
	}

	public UploadObject(String fileName, byte[] bytes) {
		this.fileName = fileName;
		this.bytes = bytes;
		this.mimeType = perseMimeType(bytes);
	}

	public String getFileName() {
		if (StringUtils.isBlank(fileName)) {
			fileName = UUID.randomUUID().toString().replaceAll("\\-", "");
		}
		if (mimeType != null && !fileName.contains(".")) {
			String fileExtension = FileUtils.getFileExtension(mimeType);
			if(fileExtension != null)fileName = fileName + fileExtension;
		}
		
		return fileName;
	}


	public String getUrl() {
		return url;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public File getFile() {
		return file;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setString(String mimeType) {
		this.mimeType = mimeType;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public UploadObject addMetaData(String key, Object value) {
		metadata.put(key, value);
		return this;
	}
	
	public String getMimeType(){
		return mimeType;
	}


	public String getCatalog() {
		return catalog;
	}

	public UploadObject toCatalog(String catalog) {
		this.catalog = catalog;
		return this;
	}

	private static String perseMimeType(byte[] bytes){
		try {
			MagicMatch match = Magic.getMagicMatch(bytes);
			String mimeType = match.getMimeType();
			return mimeType;
		} catch (Exception e) {
			return null;
		}
	}
}
