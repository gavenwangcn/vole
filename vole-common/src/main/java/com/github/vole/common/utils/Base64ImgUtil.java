package com.github.vole.common.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * 图片文件转换为Base64编码
 */
public class Base64ImgUtil {
	public static void main(String[] args) {
		// 测试从图片文件转换为Base64编码
		String strImg = GetImageStr("e://Koala.jpg");
		// 测试从Base64编码转换为图片文件
		GenerateImage(strImg, "d://t.jpg");
	}

	/**
	 * 根据图片路径对图片进行BASE64编码
	 * 
	 * @param imgFilePath 图片文件所在路径
	 * @return 图片的base64编码字符串
	 */
	public static String GetImageStr(String imgFilePath) {
		if(imgFilePath==null||("").equals(imgFilePath)){
			return null;
		}
		byte[] data = null;
		InputStream in = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFilePath);
			int count = -1;//in.available有可能为0所以不能用0做默认值,否则会死循环
			while (count ==-1) {
				count = in.available();
			}
			if(count<=0){
				return null;
			}
			data = new byte[count];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		// 对字节数组Base64编码
		return new Base64().encodeToString(data);
	}

	/**
	 * 对BASE64字符串解码并保存成文件
	 * 
	 * @param imgStr
	 *            base64编码的字符串
	 * @param imgFilePath
	 *            保存图片的文件路径
	 * @return 成功或失败
	 */
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		if (imgStr == null||("").equals(imgStr)
				||imgFilePath == null||("").equals(imgFilePath))
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);

			// 生成jpeg图片
			out = new FileOutputStream(imgFilePath);
//			out.write(bytes);
//			out.flush();
			bos = new BufferedOutputStream(out);
			// 使用缓冲区写二进制字节数据
			bos.write(bytes);
			// BufferedReader //读取
			// BufferedWriter //写入
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

	/**
	 * 对BASE64字符串解码并保存成字节数组
	 * 
	 * @param imgStr
	 *            base64编码的字符串
	 * @return 返回Base64解码后的字节数组
	 */
	public static byte[] GenerateImageByte(String imgStr) {
		if (imgStr == null||("").equals(imgStr))
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0, j = bytes.length; i < j; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param p_Str
	 *            Base64编码的字符窜
	 * @return byte[]类型的照片信息
	 * @throws IOException
	 */
	public static byte[] fromBase64(String p_Str) throws IOException {
		if(p_Str==null)
			return null;
		byte[] byteBuffer = new BASE64Decoder().decodeBuffer(p_Str);
		return byteBuffer;
	}

	/**
	 * @param bytes
	 *            byte[]类型的照片信息
	 * @return Java Image对象。可以直接在java程序中绘制到UI界面
	 */
	public static Image getImage(byte[] bytes) {
		if(bytes==null){
			return null;
		}
		Image img = null;
		InputStream isPhoto = null;
		try {
			isPhoto = new ByteArrayInputStream(bytes);
			img = ImageIO.read(isPhoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (isPhoto != null) {
					isPhoto.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		return img;
	}
}
