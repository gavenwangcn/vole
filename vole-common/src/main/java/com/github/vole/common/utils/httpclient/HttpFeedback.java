package com.github.vole.common.utils.httpclient;

import java.util.HashMap;
import java.util.Map;

public class HttpFeedback {
	/** 回执状态码 */
	String statusCode;
	/** 回执头 */
	Map<String, String> headMap;
	/** 回执体 */
	String receiptStr;

	HttpFeedback() {
		headMap = new HashMap<String, String>();
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getReceiptStr() {
		return receiptStr;
	}

	public void setReceiptStr(String receiptStr) {
		this.receiptStr = receiptStr;
	}

	public Map<String, String> getHeadMap() {
		return headMap;
	}

	public String getHeadValue(String headKey) {
		return headMap.get(headKey);
	}

	public boolean successful() {
		return HTTPCode._200.VALUE.equals(statusCode);
	}
}
