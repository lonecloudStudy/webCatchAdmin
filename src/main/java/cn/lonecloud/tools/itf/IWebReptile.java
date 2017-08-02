package cn.lonecloud.tools.itf;

import java.util.List;

/**
 * 用于对网页抓取的统一接口
 * @author lonecloud
 *
 * @param <T>
 */
public interface IWebReptile<T> {
	/**
	 * 统一开放接口
	 * @param url
	 * @return
	 */
	List<T> catchWebByUrl(String url);
	
	
}
