package com.github.vole.common.fs.local;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	/**
	 * 传入文件夹路径，该方法能够实现创建整个路径
	 * @param path 文件夹路径，不包含文件名称及后缀名
	 */
	public static void isDir(String path){
		String[] paths = path.split("/");
			String filePath = "";
			for(int i = 0 ; i < paths.length ; i++){
				if(i == 0){
					filePath = paths[0];
				}else{
					filePath += "/" + paths[i];
				}
				creatDir(filePath);
			}
	}
	
	/**
	 * 该方法用来判断文件夹是否存在，如果不存在则创建，存在则什么都不做
	 * @param filePath
	 */
	public static void creatDir(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
				file.mkdir();
		}
	}

	/**
	 * 获取扩展名
	 * @param filePath
	 * @return
	 */
	public static String parseFileExtension(String filePath){
		if(filePath.contains("/")){
			filePath = filePath.substring(filePath.lastIndexOf("/"));
		}
		filePath = filePath.split("\\?")[0];
		if(filePath.contains(".")){
			return filePath.substring(filePath.lastIndexOf(".") + 1);
		}
		return null;
	}

	/**
	 * 获取扩展名
	 * @param mimeType
	 * @return
	 */
	public static String getFileExtension(String mimeType){
		Map<String,String> maps = new HashMap<>();
		maps.put("image/jpeg",".jpg");
		maps.put("image/gif",".gif" );
		maps.put("image/png",".png" );
		maps.put("image/bmp",".bmp" );
		maps.put("text/plain",".txt");
		maps.put("application/zip",".zip" );
		maps.put("application/x-zip-compressed",".zip" );
		maps.put("multipart/x-zip",".zip" );
		maps.put("application/x-compressed",".zip" );
		maps.put("audio/mpeg3",".mp3" );
		maps.put("video/avi",".avi" );
		maps.put("audio/wav",".wav" );
		maps.put("application/x-gzip",".gzip" );
		maps.put("application/x-gzip",".gz");
		maps.put("text/html",".html");
		maps.put("application/x-shockwave-flash",".svg");
		maps.put("application/pdf",".pdf" );
		maps.put("application/msword",".doc" );
		maps.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",".docx" );
		maps.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",".xlsx" );
		maps.put("application/vnd.ms-excel",".xls" );
		maps.put("application/vnd.ms-powerpoint",".ppt" );
		maps.put("application/vnd.openxmlformats-officedocument.presentationml.presentation",".pptx" );
		return maps.get(mimeType);
	}

	/**
	 * 获取文件名
	 * @param filePath
	 * @return
	 */
	public static String  parseFileName(String filePath){
		filePath = filePath.split("\\?")[0];
		int index = filePath.lastIndexOf("/") + 1;
		if(index > 0){
			return filePath.substring(index);
		}
		return filePath;
	}

	public static void main(String[] args) {
		System.out.println(parseFileExtension("http:www.ssss.com/cccc/123.png?xxx"));
		System.out.println(parseFileExtension("123.png"));
		System.out.println(parseFileExtension("http:www.ssss.com/cccc/dtgh4r4tt/"));

		System.out.println(parseFileName("http:www.ssss.com/cccc/123.png?cfg"));
	}
}
