
package org.ygq.tools.test.jtd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * http访问工具类（2.0访问模式）
 * @author xhxy
 *
 */
public class HttpUtil
{
	private static final Logger logger = Logger.getLogger(HttpUtil.class.getName());
	
	/**
	 * @description post方式获取数据
	 * @author YeZhangwei
	 * @createDate 2017年6月19日 上午11:06:17
	 * @param urlStr
	 * @param headerValue
	 * @param content
	 * @return
	 * @remark
	 */
	public static String getPostSource( String urlStr, String headerName, String headerValue, String content )
	{
		URL url = null;
		
		HttpURLConnection conn = null;
		
		BufferedReader reader = null;
		
		long beginTime = System.currentTimeMillis();
		
		try
		{
			url = new URL( urlStr );
			
			conn = (HttpURLConnection) url.openConnection();
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection)conn).setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				SSLContext instance = SSLContext.getInstance("TLS");
				instance.init(null, new TrustManager[]{new X509TrustManager() {
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
					
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
						
					}
				}}, new SecureRandom());
				((HttpsURLConnection)conn).setSSLSocketFactory(instance.getSocketFactory());
			}
			conn.setDoOutput( true );
			conn.setUseCaches( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/json;charset=UTF-8" );
			//设置头
			if (headerValue != null && !headerValue.isEmpty()) {
				conn.setRequestProperty( headerName, headerValue );
			}
			//写入内容
			if (content != null && !content.isEmpty()) {
				conn.setDoInput( true );
				
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), Charset.forName("UTF-8")));
				bw.write(content);
				bw.flush();
				bw.close();
			}else {
				conn.setDoInput( false );
			}
			
			reader = new BufferedReader( new InputStreamReader( conn.getInputStream(), Charset.forName( "UTF-8" ) ) );
			
			String result = null;
			
			StringBuilder sb = new StringBuilder();
			
			while ( (result = reader.readLine()) != null )
			{
				sb.append( result );
			}
			result = URLDecoder.decode( sb.toString(), "UTF-8" );
			
			long time = System.currentTimeMillis() - beginTime;
			
			logger.info( "【API调用耗时】: "+time+"ms [参数]: " + URLDecoder.decode(content, "UTF-8") + " [结果]: " + result );
			
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				if( reader != null )
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			conn.disconnect();
		}
	}
	
	/**
	 * @description post方式获取数据
	 * @author YeZhangwei
	 * @createDate 2017年6月19日 上午11:06:17
	 * @param urlStr
	 * @param headerValue
	 * @param content
	 * @return
	 * @remark
	 */
	public static String getGetSource( String urlStr, String headerName, String headerValue, Path path )
	{
		URL url = null;
		
		HttpURLConnection conn = null;
		
		BufferedReader reader = null;
		
		long beginTime = System.currentTimeMillis();
		
		try
		{
			url = new URL( urlStr );
			
			conn = (HttpURLConnection) url.openConnection();
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection)conn).setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				SSLContext instance = SSLContext.getInstance("TLS");
				instance.init(null, new TrustManager[]{new X509TrustManager() {
					
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					
					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}
					
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
						
					}
				}}, new SecureRandom());
				((HttpsURLConnection)conn).setSSLSocketFactory(instance.getSocketFactory());
			}
			conn.setDoOutput( false );
			conn.setUseCaches( false );
			conn.setRequestMethod( "GET" );
			conn.setRequestProperty( "Content-Type", "application/json;charset=UTF-8" );
			//设置头
			if (headerValue != null && !headerValue.isEmpty()) {
				conn.setRequestProperty( headerName, headerValue );
			}
			//写入内容
			conn.setDoInput( true );
			//reader = new BufferedReader( new InputStreamReader( conn.getInputStream(), Charset.forName( "UTF-8" ) ) );
			BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
			
			byte[] temp = new byte[1024];
			
			int length = 0;
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(-1!=(length=bufferedInputStream.read(temp))) {
				baos.write(temp, 0, length);
			}
			Files.write(path, baos.toByteArray(), StandardOpenOption.CREATE);
			
			String result = urlStr;
			
			long time = System.currentTimeMillis() - beginTime;
			
			logger.info( "【API调用耗时】: "+time+"ms [参数]: " + URLDecoder.decode(urlStr, "UTF-8") + " [结果]: " + path );
			
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			try
			{
				if( reader != null )
				{
					reader.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			conn.disconnect();
		}
	}
	
	
	
}
