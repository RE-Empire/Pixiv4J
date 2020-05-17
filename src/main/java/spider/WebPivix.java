package spider;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebPivix implements PageProcessor {

    private final String webbase = "https://www.pixiv.net/artworks/";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64 ) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
            .addHeader("Referer","https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index");


    public void process(Page page) {
        if (page.getUrl().toString().endsWith("all")) {
            String s1 = StringUtils.substringBetween(page.getHtml().toString(), "\"illusts\":", ",\"manga\":");
            Map<String, Object> map = JSON.parseObject(s1, Map.class);
            Set<String> strings = map.keySet();
            for (String s : strings) {
                page.addTargetRequest(webbase+s);
            }
        }else if (page.getUrl().toString().startsWith("https://www")){
            String s = StringUtils.substringBetween(page.getJson().toString(), "original\":\"", "\"}");
            page.addTargetRequest(s);
        }else {
            byte[] bytes = page.getBytes();
            String s=page.getUrl()+"";
            s=s.substring(s.length()-14);
            System.out.println(s);
            Util.byte2File(bytes,"F:\\spider\\",s);
        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",1080)));

        Spider.create(new WebPivix())
                .addUrl("https://www.pixiv.net/ajax/user/1422579/profile/all")
                .setDownloader(httpClientDownloader)
//                .addPipeline(new MyPineline())
                .thread(1)
                .run();
    }

}
