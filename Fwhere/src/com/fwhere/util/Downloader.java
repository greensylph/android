package com.fwhere.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * downloader
 * 
 * @author mingyuan
 * 
 */
public class Downloader {
	private HttpClientParams params = null;
	private HttpClient client = null;

	/**
	 * 默认构造函数，初始化一系列变量
	 */
	public Downloader() {
		// 构造HttpClientParams参数
		params = new HttpClientParams();
		params.setParameter(
				HttpClientParams.USER_AGENT,
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 GTBDFff GTB7.0 (.NET CLR 3.5.30729)");
		params.setParameter(HttpClientParams.ALLOW_CIRCULAR_REDIRECTS, false);
		params.setParameter(HttpClientParams.MAX_REDIRECTS, 4);
		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT,
				(long) 60 * 1000);
		params.setParameter(HttpClientParams.SO_TIMEOUT, 60 * 1000);
		// 使用系统提供的默认的恢复策略
		params.setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		client = new HttpClient(params);
	}

	/**
	 * 下载网页
	 * 
	 * @param url
	 *            网页url
	 * @return String类型的网页源码
	 */
	public String download(String url) {
		HttpMethod method = new GetMethod(url);
		String sourceCode = null;
		method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		// 读取内容
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			reader = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream(), "utf8"));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\r\n");
			}
			sourceCode = builder.toString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 释放连接
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return sourceCode;
	}
}
