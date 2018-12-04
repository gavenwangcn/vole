/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.vole.common.fs.oss;

import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class UploadTokenParam {

	private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
	private static final String CONTENT_TYPE_JSON = "application/json";
	private static final String PATH_SEPARATOR = "/";
	//过期时间（单位秒）
	private long expires = 3600;
	private String bucketName;
	private String fileType;
	private String uploadDir;
	private String fileName;
	
	private String callbackUrl;
	private String callbackBody;
	private String callbackHost;
	private boolean callbackBodyUseJson = false;
	
	//如：image/jpg 可以支持通配符image/*
	private String mimeLimit;
	//单位 byte
	private Long fsizeMin;
	private Long fsizeMax;
	
	private Integer deleteAfterDays;
	

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = StringUtils.trimToNull(uploadDir);
		if(uploadDir != null){
			if(!this.uploadDir.endsWith(PATH_SEPARATOR)){
				this.uploadDir = this.uploadDir.concat(PATH_SEPARATOR);
			}
			if(this.uploadDir.startsWith(PATH_SEPARATOR)){
				this.uploadDir = this.uploadDir.substring(1);
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getCallbackBody() {
		return callbackBody;
	}

	public void setCallbackBody(String callbackBody) {
		this.callbackBody = callbackBody;
	}

	public String getCallbackHost() {
		return callbackHost;
	}

	public void setCallbackHost(String callbackHost) {
		this.callbackHost = callbackHost;
	}
	

	public void setCallbackBodyUseJson(boolean callbackBodyUseJson) {
		this.callbackBodyUseJson = callbackBodyUseJson;
	}

	public String getCallbackBodyType() {
		return callbackBodyUseJson ? CONTENT_TYPE_JSON : CONTENT_TYPE_FORM_URLENCODED;
	}

	public String getMimeLimit() {
		return mimeLimit;
	}

	public void setMimeLimit(String mimeLimit) {
		this.mimeLimit = mimeLimit;
	}

	public Long getFsizeMin() {
		return fsizeMin;
	}

	public void setFsizeMin(Long fsizeMin) {
		this.fsizeMin = fsizeMin;
	}

	public Long getFsizeMax() {
		return fsizeMax;
	}

	public void setFsizeMax(Long fsizeMax) {
		this.fsizeMax = fsizeMax;
	}

	public Integer getDeleteAfterDays() {
		return deleteAfterDays;
	}

	public void setDeleteAfterDays(Integer deleteAfterDays) {
		if(deleteAfterDays != null && deleteAfterDays > 0){
		   this.deleteAfterDays = deleteAfterDays;
		}
	}
	
	public String getFileKey(){
		if(StringUtils.isBlank(uploadDir) || StringUtils.isBlank(fileName)){
			return fileName;
		}
		return uploadDir.concat(fileName);
	}
	
	public String getCallbackRuleAsJson(){
		if(StringUtils.isAnyBlank(callbackBody,callbackHost,callbackUrl))return null;
		Map<String, String> map = new HashMap<>(4);
		map.put("callbackBody", callbackBody);
		map.put("callbackHost", callbackHost);
		map.put("callbackUrl", callbackUrl);
		map.put("callbackBodyType", getCallbackBodyType());
		return JSONObject.toJSONString(map);
	}
    
}
