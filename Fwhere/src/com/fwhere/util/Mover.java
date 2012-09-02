package com.fwhere.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 采集、解析、转移
 * 
 * @author mingyuan
 * 
 */
public class Mover {
	private int totalPages;
	private XmlRpcClientConfigImpl config;
	private XmlRpcClient client;
	private String baseUrl;
	private Object userName;
	private Object password;
	private String csdnUserName;

	public Mover(int totalPages, String blogRpcUrl, String csdnUrl,
			String csdnUserName, String userName, String password) {
		this.totalPages = totalPages;
		this.baseUrl = csdnUrl;
		this.csdnUserName = csdnUserName;
		this.userName = userName;
		this.password = password;
		config = new XmlRpcClientConfigImpl();
		try {
			config.setServerURL(new URL(blogRpcUrl));
		} catch (MalformedURLException e) {
			System.out.println("请检查url");
		}
		client = new XmlRpcClient();
		client.setConfig(config);
	}

	private List<String> getlinks() {
		List<String> list = new LinkedList<String>();
		for (int i = 1; i <= totalPages; i++) {
			System.out.println("processing page " + i);
			Downloader downloader = new Downloader();
			String content = downloader.download(baseUrl + "/" + csdnUserName
					+ "/article/list/" + i);
			if (content == null)
				continue;
			Document doc = Jsoup.parse(content);
			Elements first = doc.select(".link_title");
			for (int j = 0; j < first.size(); j++) {
				Element first2 = first.get(j).select("a").first();
				String link = baseUrl + first2.attr("href");
				list.add(link);
				System.out.println("get link\t" + link);
			}
			System.out.println("page " + i + " extractor done,sleep 2s");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<CSDNPost> getPosts() {
		List<String> links = getlinks();
		List<CSDNPost> posts = new LinkedList<CSDNPost>();
		for (String link : links) {
			CSDNPost post = getPost(link);
			if (post != null) {
				posts.add(post);
			}
		}
		return posts;
	}

	private CSDNPost getPost(String url) {
		System.out.println("url\t" + url);
		Downloader downloader = new Downloader();
		String html = downloader.download(url);
		if (html == null)
			return null;
		Document doc = Jsoup.parse(html);
		String title = doc.select(".article_title").first().text();
		String categroy = "Uncategorized";
		Elements link_categories = doc
				.select(".article_manage .link_categories");
		if (link_categories != null) {
			Element first = link_categories.first();
			if (first != null) {
				Elements href = first.select("a");
				if (href != null) {
					categroy = href.text();
				}
			}
		}
		String postdate = doc.select(".article_manage .link_postdate").first()
				.text();
		String content = doc.select(".details .article_content").first().text();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		CSDNPost post = new CSDNPost();
		post.setCategories(new String[] { categroy });
		post.setTitle(title);
		try {
			post.setDateCreated(sdf.parse(postdate));
		} catch (ParseException e) {
			post.setDateCreated(new Date());
		}
		post.setDescription(content);
		return post;
	}

	public void publish(CSDNPost post) {
		Map<String, Object> struct = new HashMap<String, Object>();
		struct.put("dateCreated", post.getDateCreated());
		struct.put("description", post.getDescription());
		struct.put("title", post.getTitle());
		struct.put("categories", post.getCategories());
		Object[] params = new Object[] { userName, userName, password, struct,
				true };
		String blogid = null;
		try {
			blogid = (String) client.execute("metaWeblog.newPost", params);
		} catch (XmlRpcException e) {
			e.printStackTrace();
			System.out.println("导入出现错误：title=" + post.getTitle());
		}
		System.out.println(post.getTitle() + ">> 导入完毕,生成博文id为>>" + blogid);
		struct.clear();
	}

	public static void main(String[] args) throws IOException {
		Mover extractor = new Mover(19, "http://michaelfeng.blog.com/xmlrpc.php",
				"http://blog.csdn.net", "Jason69181", "jason69181", "Zqc82991910");
		List<CSDNPost> posts = extractor.getPosts();
		for (CSDNPost post : posts) {
			extractor.publish(post);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(post.getTitle());
		}
		System.out.println("done!");
	}
}