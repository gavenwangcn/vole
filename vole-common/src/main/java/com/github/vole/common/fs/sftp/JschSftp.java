package com.github.vole.common.fs.sftp;

import com.github.vole.common.fs.local.FileUtils;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * Sftp(Jsch) 连接类包括上传下载
 */
public class JschSftp {

	private static final Logger LOG = Logger.getLogger(JschSftp.class);

	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
//			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			LOG.error("sftp connect exception:", e);
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param channel
	 */
	public static void upload(String directory, String uploadFile, ChannelSftp channel) {
		try {
			creatDir(directory, channel);
			channel.cd(directory);
			File file = new File(uploadFile);
			channel.put(new FileInputStream(file), file.getName());
			sftpClose(channel);
		} catch (Exception e) {
			LOG.error("sftp upload exception:", e);
		}finally {
			if (channel != null) {
				sftpClose(channel);
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFilePath
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param channel
	 */
	public static void download(String directory, String downloadFilePath, String saveFile, ChannelSftp channel) {
		try {
			channel.cd(directory);
			File file = new File(saveFile);
			channel.get(downloadFilePath, new FileOutputStream(file));
		} catch (Exception e) {
			LOG.error("sftp download exception:", e);
		}finally {
			if (channel != null) {
				sftpClose(channel);
			}
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param channelSftp
	 */
	public static String downloadGetString(String directory, String downloadFile, String saveFile, ChannelSftp channelSftp) {
		try {
			channelSftp.cd(directory);
			File file = new File(saveFile);
			channelSftp.get(downloadFile, new FileOutputStream(file));
			return readFileByLines(file.getPath());
		} catch (Exception e) {
			LOG.error("sftp downloadGetString excetpin:", e);
		}
		return null;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// 以行为单位读取文件内容，一次读一整行
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			LOG.error(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOG.error("BufferedReader close exception:", e);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 根据传入的目录创建文件夹
	 * 
	 * @param directory
	 * @param sftp
	 * @throws SftpException
	 */
	public static void creatDir(String directory, ChannelSftp sftp) throws SftpException {
		String[] dirArr = directory.split("/");
		StringBuffer tempStr = new StringBuffer("");
		for (int i = 1; i < dirArr.length; i++) {
			tempStr.append("/" + dirArr[i]);
			try {
				sftp.cd(tempStr.toString());
			} catch (SftpException e) {
				sftp.mkdir(tempStr.toString());
			}
		}
	}

	/**
	 * sftpClose:关闭Sftp <br/>
	 * 
	 * @param sftp
	 */
	public static void sftpClose(ChannelSftp sftp) {
		if (null == sftp) {
			return;
		}
		try {
			try {
				if (!sftp.isClosed()) {
					sftp.disconnect();
				}
			} finally {
				Session session = sftp.getSession();
				if (session.isConnected()) {
					session.disconnect();
				}
			}
		} catch (JSchException e) {
			LOG.error("sftp disconnect exception:", e);
		}
	}
	
	/***
	 * 连接SFTP服务器，根据文件路径读取文件文本内容.<br/>
	 * 
	 * @param dataFilePath
	 *            SFTP保存的文件全路径
	 * @return 文件内容.
	 */
	public static String getFileContentFormSFTP(final ChannelSftp channelSftp, final String dataFilePath) {
		String property = System.getProperty("member.dir") + File.separator + "temp/";
		FileUtils.isDir(property);
		String directory = dataFilePath.substring(0, dataFilePath.lastIndexOf("/")); // 文件路径
		String downloadFile = dataFilePath.substring(dataFilePath.lastIndexOf("/") + 1); // 文件名称
		String saveFile = property + "/" + downloadFile; // 保存文件路径
		LOG.info("==>从SFTP获取文件内容，源文件路径[" + dataFilePath + "], 保存本地的临时文件路径[" + saveFile + "]");
		return downloadGetString(directory, downloadFile, saveFile, channelSftp);
	}
	
	/**
	 * 从SFTP服务器上下载文件
	 * 
	 * @return
	 */
	public static File downFileFromSFTP(ChannelSftp channelSftp, final String filePath) {
		// 创建临时目录，用来存放下载的文件
		StringBuffer tempFilePath = new StringBuffer(System.getProperty("member.dir")).append(File.separator).append("temp");
		isDir(tempFilePath.toString());
		String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
		String tempPath = filePath.substring(0, filePath.lastIndexOf("/") + 1);

		// 创建临时返回文件
		String saveFile = tempFilePath + "/" + fileName;
		File returnFile = new File(saveFile);
		try {
			download(tempPath, fileName, saveFile, channelSftp);
		} catch (Exception e) {
			LOG.error("==>对账文件下载失败：", e);
		} finally {
			if (channelSftp != null) {
				sftpClose(channelSftp);
			}
		}
		return returnFile;
	}
	
	/**
	 * 传入文件夹路径，该方法能够实现创建整个路径
	 * 
	 * @param path
	 *            文件夹路径，不包含文件名称及后缀名
	 */
	public static void isDir(String path) {
		String[] paths = path.split("/");
		String filePath = "";
		for (int i = 0; i < paths.length; i++) {
			if (i == 0) {
				filePath = paths[0];
			} else {
				filePath += "/" + paths[i];
			}
			creatDir(filePath);
		}
	}

	/**
	 * 该方法用来判断文件夹是否存在，如果不存在则创建，存在则什么都不做
	 * 
	 * @param filePath
	 */
	public static void creatDir(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	// 测试例子
	public static void main(String[] args) {
		JschSftp sf = new JschSftp();
		String host = "10.7.111.178";
		int port = 22;
		String username = "elive";
		String password = "lakala";
		String directory = "/home/elive/1/test/";
		
		String downloadFile = "nginx.conf";
		String saveFile = "D:\\nginx123.conf";
		
		String uploadFile = "D:\\test01.txt.rar";
		 String deleteFile = "test01.txt.rar";
		ChannelSftp sftp = sf.connect(host, port, username, password);
		sf.upload(directory, uploadFile, sftp);
//		sf.download(directory, downloadFile, saveFile, sftp);
//		 sf.delete(directory, deleteFile, sftp);
		try {
//			sf.creatDir(directory, sftp);
//			 sftp.cd(directory);
//			 System.out.println("finished");
//			sf.sftpClose(sftp);
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			if (sf != null){
				sf.sftpClose(sftp);
			}
		}
	}

}
