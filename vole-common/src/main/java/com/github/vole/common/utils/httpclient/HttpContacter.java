package com.github.vole.common.utils.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Http 协议请求
 */
public class HttpContacter extends HttpContactAble {

	private static HttpContacter contacter = new HttpContacter();

	private HttpContacter() {

	}

	public static synchronized HttpContacter p() {
		if (contacter != null) {
			return contacter;
		}else{
			contacter = new HttpContacter();
		}
		return contacter;
	}

	@Override
	protected HttpClient buildHttpClient() throws Exception {
		return HttpClients.createDefault();
	}
}
