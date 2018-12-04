package com.github.vole.common.utils.httpclient;

import com.github.vole.common.utils.ReflectionUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 各模块利用HTTP通讯基类
 * @description 基于apache httpclient4
 */
public abstract class HttpContactAble {
	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
	public static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
	public static final String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
	public static final String CONTENT_TYPE_XML = "text/xml;charset=utf-8";
	public static final String CONTENT_TYPE_TEXT = "text/plain";
	private static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)";

	/** 请求头参数 */
	private Map<String, String> headParams = new HashMap<String, String>();
	/** 返回内容编码 */
	private String responseCharset = null;
	/** 请求超时 */
	// ConnectionRequestTimeout used when requesting a connection from the
	// connection manager
	private int connectRequestTimeout = 5 * 1000;
	/** 连接超时 */
	// Connection timeout is the timeout until a connection with the server is
	// established
	private int connectionTimeout = 5 * 1000;
	/** socket超时 */
	private int socketTimeout = 10 * 1000;

	/** 默认回复JSON格式内容 */
	public HttpContactAble() {
		headParams.put("Content-Type", CONTENT_TYPE_JSON);
		headParams.put("User-Agent", DEFAULT_USER_AGENT);
	}

	public HttpContactAble(String contentType) {
		headParams.put("Content-Type", contentType);
		headParams.put("User-Agent", DEFAULT_USER_AGENT);
	}

	public void setHeadParam(String headParamName, String headParamValue) {
		headParams.put(headParamName, headParamValue);
	}

	public void setHeadParams(Map<String, String> headParams) {
		this.headParams = headParams;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/***************** post ****************/
	public HttpFeedback doPost(String requestURL, String requestContent) throws Exception {
		HttpPost post = new HttpPost(requestURL);
		post.setEntity(new StringEntity(requestContent));
		return doSend(post);
	}

	public HttpFeedback doPost(String requestURL, String requestContent, String mimeType, String charset) throws Exception {
		HttpPost post = new HttpPost(requestURL);
		post.setEntity(new StringEntity(requestContent, ContentType.create(mimeType, charset)));
		return doSend(post);
	}

	public HttpFeedback doPost(String requestURL, Map<String, String> requestParams) throws Exception {
		return doPost(requestURL, requestParams, "utf-8");
	}

	public HttpFeedback doPost(String requestURL, Map<String, String> requestParams, String charset) throws Exception {
		HttpPost post = new HttpPost(requestURL);
		post.setEntity(makeFormEntity(requestParams));
		return doSend(post);
	}

	/***************** get ****************/
	public HttpFeedback doGet(String requestURL, Map<String, String> requestParams) throws Exception {
		URIBuilder builder = new URIBuilder(requestURL);
		if (requestParams != null && requestParams.size() > 0) {
			for (Iterator<Entry<String, String>> it = requestParams.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				builder.setParameter(entry.getKey(), entry.getValue());
			}
		}
		URI uri = null;
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			throw new HttpException(e.getMessage());
		}
		HttpGet get = new HttpGet(uri);
		return doSend(get);
	}

	public HttpFeedback doGet(String requestURL, String queryString) throws Exception {
		HttpGet get = new HttpGet(requestURL.endsWith("?") ? (requestURL + queryString) : (requestURL + "?" + queryString));
		return doSend(get);
	}

	public HttpFeedback doGet(String requestURL) throws Exception {
		HttpGet get = new HttpGet(requestURL);
		return doSend(get);
	}
	/****************** put *******************/
	public HttpFeedback doPut(String requestURL, Map<String, String> requestParams) throws Exception {
		HttpPut put = new HttpPut(requestURL);
		put.setEntity(makeFormEntity(requestParams));
		return doSend(put);
	}
	public HttpFeedback doPut(String requestURL, String requestContent) throws Exception {
		HttpPut post = new HttpPut(requestURL);
		post.setEntity(new StringEntity(requestContent));
		return doSend(post);
	}
	/****************** delete *******************/
	public HttpFeedback doDelete(String requestURL, Map<String, String> requestParams) throws Exception {
		HttpDelete delete = new HttpDelete(requestURL);
		delete.setEntity(makeFormEntity(requestParams));
		return doSend(delete);
	}
	public HttpFeedback doDelete(String requestURL, String requestContent) throws Exception {
		HttpDelete delete = new HttpDelete(requestURL);
		delete.setEntity(new StringEntity(requestContent));
		return doSend(delete);
	}
	/****************** send *******************/

	private HttpFeedback doSend(HttpRequestBase request) throws Exception {
		request.setConfig(RequestConfig.custom().setConnectionRequestTimeout(connectRequestTimeout).setConnectTimeout(connectionTimeout)
				.setSocketTimeout(socketTimeout).build());
		if (headParams.size() > 0) {
			for (Iterator<Entry<String, String>> it = headParams.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpFeedback httpFeedback = new HttpFeedback();
		HttpClient httpClient = null;
		try {
			httpClient = buildHttpClient();
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			httpFeedback.receiptStr = EntityUtils.toString(entity, "utf-8");
			StatusLine statusLine = response.getStatusLine();
			httpFeedback.statusCode = String.valueOf(statusLine.getStatusCode());
			Map<String, String> headMap = new HashMap<String, String>();
			StringBuilder temp = new StringBuilder();
			for (Header header : response.getAllHeaders()) {
				if (!headMap.containsKey(header.getName())) {
					temp.setLength(0);
					for (Header h : response.getHeaders(header.getName())) {
						temp.append(h.getValue()).append(";");
					}
					headMap.put(header.getName(), temp.toString());
				}
			}
			httpFeedback.headMap = headMap;
		} catch (Exception e) {
			throw e;
		} finally {
			ReflectionUtils.invokeNoParam(httpClient, "close");
		}
		return httpFeedback;
	}

	private UrlEncodedFormEntity makeFormEntity(Map<String, String> requestParams) throws Exception {
		if (requestParams != null && requestParams.size() > 0) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (Iterator<Entry<String, String>> it = requestParams.entrySet().iterator(); it.hasNext();) {
				Entry<String, String> entry = it.next();
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			return new UrlEncodedFormEntity(formparams, "utf-8");
		}
		return null;
	}
	private class HttpDelete extends HttpEntityEnclosingRequestBase {
		public static final String METHOD_NAME = "DELETE";
		public String getMethod() {
			return METHOD_NAME;
		}
		public HttpDelete(final String uri) {
			super();
			setURI(URI.create(uri));
		}
	}
	abstract protected HttpClient buildHttpClient() throws Exception;
}
