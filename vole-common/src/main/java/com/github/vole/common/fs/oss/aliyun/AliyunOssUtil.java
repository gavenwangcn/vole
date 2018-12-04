package com.github.vole.common.fs.oss.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.github.vole.common.fs.oss.UploadObject;
import com.github.vole.common.fs.oss.UploadTokenParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 基于阿里云 OSS 的文件系统实现(https://help.aliyun.com/document_detail/32008.html?spm=a2c4g.11186623.3.3.9W7hli)
 * <p/>
 * 1. 开通 OSS <br/>
 * 2. 创建 bucket <br/>
 * 3. 为 bucket 绑定域名, eg: img.ponly.com --&gt; ponly.oss-cn-hongkong.aliyuncs.com <br/>
 *
 * URI 说明: schema://[member[:password]@][:port]/path
 * 		如： aliyun://accessId:accessKey@bucket.endpoint/path, aliyun://accessId:accessKey@oss-ch-sh.oss-cn-shanghai.aliyuncs.com
 *
 */
public class AliyunOssUtil {
	
	private static final String URL_PREFIX_PATTERN = "(http).*\\.(com|cn)\\/";
	private static final String DEFAULT_CALLBACK_BODY = "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}";
	
	private static OSSClient ossClient;
	private static String bucketName;
	private static String urlprefix;
	private static boolean isPrivate;
	private static String accessKeyId;
	private static String host;

	/**
	 * 创建存储空间时指定存储空间的权限和存储类型
	 * @param aliyunOss
	 */
	public static void createBucket(AliyunOss aliyunOss) {
		
		Validate.notBlank(aliyunOss.getEndpoint(), "[endpoint] not defined");
		Validate.notBlank(aliyunOss.getBucketName(), "[bucketName] not defined");
		Validate.notBlank(aliyunOss.getAccessKey(), "[accessKey] not defined");
		Validate.notBlank(aliyunOss.getSecretKey(), "[secretKey] not defined");
		Validate.notBlank(aliyunOss.getUrlprefix(), "[urlprefix] not defined");
		
		accessKeyId = aliyunOss.getAccessKey();
		// 创建OSSClient实例
		ossClient = new OSSClient(aliyunOss.getEndpoint(), aliyunOss.getAccessKey(), aliyunOss.getSecretKey());
		bucketName = aliyunOss.getBucketName();
		urlprefix = aliyunOss.getUrlprefix().endsWith("/") ? aliyunOss.getUrlprefix() : (aliyunOss.getUrlprefix() + "/");
		isPrivate = aliyunOss.isPrivate();
		host = StringUtils.remove(aliyunOss.getUrlprefix(),"/").split(":")[1];
		// 判断存储空间是否存在
		if (!ossClient.doesBucketExist(aliyunOss.getBucketName())) {
			System.out.println("Creating bucket " + aliyunOss.getBucketName() + "\n");
			// 新建存储空间默认为标准存储类型，私有权限。
            ossClient.createBucket(aliyunOss.getBucketName());
            // 创建存储空间
            CreateBucketRequest createBucketRequest= new CreateBucketRequest(aliyunOss.getBucketName());
			// 设置存储空间的权限为公共读，默认是私有。
			createBucketRequest.setCannedACL(aliyunOss.isPrivate() ? CannedAccessControlList.Private : CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
		}
	}

	/**
	 * 上传文件
	 * @param object
	 * @return
	 */
	public static String upload(UploadObject object) {
		try {
			PutObjectRequest request = null;
			if(object.getFile() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), object.getFile());
			}else if(object.getBytes() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), new ByteArrayInputStream(object.getBytes()));
			}else if(object.getInputStream() != null){
				request = new PutObjectRequest(bucketName, object.getFileName(), object.getInputStream());
			}else{
				throw new IllegalArgumentException("upload object is NULL");
			}
			// Upload an object to your bucket from a file
			PutObjectResult result = ossClient.putObject(request);
			if(result.getResponse() == null){
				return isPrivate ? object.getFileName() : urlprefix + object.getFileName();
			}
			if(result.getResponse().isSuccessful()){
				return result.getResponse().getUri();
			}else{
				throw new RuntimeException(result.getResponse().getErrorResponseAsString());
			}
		} catch (OSSException e) {
			throw new RuntimeException(e.getErrorMessage());
		}
	}

	/**
	 * 服务端签名后，不通过后端应用服务器，直传到oss
	 * https://help.aliyun.com/document_detail/31926.html
	 * https://help.aliyun.com/document_detail/31989.html?spm=a2c4g.11186623.6.907.tlMQcL
	 * @param param
	 * @return
	 */
	public Map<String, Object> createUploadToken(UploadTokenParam param) {
		
		Map<String, Object> result = new HashMap<>();
		
		PolicyConditions policyConds = new PolicyConditions();
		if(param.getFsizeMin() != null && param.getFsizeMax() != null){			
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, param.getFsizeMin(), param.getFsizeMax());
		}else{
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		}
		if(param.getUploadDir() != null){			
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, param.getUploadDir());
		}
        
		if(StringUtils.isBlank(param.getCallbackHost())){
			param.setCallbackHost(host);
		}
		
		if(StringUtils.isBlank(param.getCallbackBody())){
			param.setCallbackBody(DEFAULT_CALLBACK_BODY);
		}
		
		Date expire = DateUtils.addSeconds(new Date(), (int)param.getExpires());
		String policy = ossClient.generatePostPolicy(expire, policyConds);
        String policyBase64 = null;
        String callbackBase64 = null;
        try {
        	policyBase64 = BinaryUtil.toBase64String(policy.getBytes(StandardCharsets.UTF_8.name()));
        	String callbackJson = param.getCallbackRuleAsJson();
        	if(callbackJson != null){
        		callbackBase64 = BinaryUtil.toBase64String(callbackJson.getBytes(StandardCharsets.UTF_8.name()));
        	}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		String signature = ossClient.calculatePostSignature(policy);
		
		result.put("OSSAccessKeyId", accessKeyId);
		result.put("policy", policyBase64);
		result.put("signature", signature);
		result.put("host", urlprefix);
		result.put("dir", param.getUploadDir());
		result.put("expire", String.valueOf(expire.getTime()));
		if(callbackBase64 != null){
			result.put("callback", callbackBase64);
		}
		return result;
	}

	/**
	 * 台删除单个文件
	 * @param fileKey
	 * @return
	 */
	public boolean delete(String fileKey) {
		ossClient.deleteObject(bucketName, fileKey);
		return true;
	}
	
	public String getDownloadUrl(String fileKey) {
		//ObjectAcl objectAcl = ossClient.getObjectAcl(bucketName, key);
		if(isPrivate){
			URL url = ossClient.generatePresignedUrl(bucketName, fileKey, DateUtils.addHours(new Date(), 1));
			return url.toString().replaceFirst(URL_PREFIX_PATTERN, urlprefix);
		}
		return urlprefix + fileKey;
	}

	/**
	 * 关闭OSSClient
	 */
	public void close() {
		ossClient.shutdown();
	}

	public static void main(String[] args) {
		AliyunOss aliyunOss = new AliyunOss();
		aliyunOss.setEndpoint("*** Provide OSS endpoint ***");
		aliyunOss.setAccessKey ("*** Provide your AccessKeyId  ***");
		aliyunOss.setSecretKey("*** Provide your AccessKeySecret ***");
		aliyunOss.setBucketName("*** Provide bucket name ****");
		AliyunOssUtil.createBucket(aliyunOss);
		UploadObject uploadObject = new UploadObject("D:\\test.txt");
		AliyunOssUtil.upload(uploadObject);
	}
}
