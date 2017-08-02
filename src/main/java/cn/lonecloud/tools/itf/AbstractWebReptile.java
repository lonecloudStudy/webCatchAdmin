package cn.lonecloud.tools.itf;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * 统一构造接口默认实现
 * @author lonecloud
 *
 * @param <T>
 */
public abstract class AbstractWebReptile<T> implements IWebReptile<T> {

	@Override
	public List<T> catchWebByUrl(String url) {
		String html = pickData(url);
		return analyzeHTMLByString(html);
	}
	/**
	 * 爬取网页信息
	 *
	 * @param url
	 * @return
	 */
	protected static String pickData(String url) {
		// 我们可以使用一个Builder来设置UA字段，然后再创建HttpClient对象
		HttpClientBuilder builder = HttpClients.custom();
		// 对照UA字串的标准格式理解一下每部分的意思
		builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
		CloseableHttpClient httpClient = builder.build();
		try {
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 通过heml字符串进行解析
	 * 
	 * @param html
	 * @return
	 */
	protected abstract List<T> analyzeHTMLByString(String html);
}
