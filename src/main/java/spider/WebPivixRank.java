package spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

public class WebPivixRank implements PageProcessor {

    private final String webbase = "https://www.pixiv.net/artworks/";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64 ) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36")
            .addHeader("Referer","https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index");


    public void process(Page page) {
        if (page.getUrl().toString().startsWith("https://api")){
            long l = System.currentTimeMillis();
            JSONObject jsonObject = JSON.parseObject(page.getJson().toString());
            JSONArray jsonArray = jsonObject.getJSONArray("response").getJSONObject(0).getJSONArray("works");
            for (int i = 0; i <jsonArray.size() ; i++) {
//                String s = StringUtils.substringBetween(jsonArray.getJSONObject(i).toString(), "\"large\":\"", "\""); //~60ms
                String s = jsonArray.getJSONObject(i).getJSONObject("work").getJSONObject("image_urls").getString("large");//~40+ms
                page.addTargetRequest(s);
            }
        } else{
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
        Spider.create(new WebPivixRank())
                .addUrl("https://api.imjad.cn/pixiv/v1/?type=rank&content=illust&mode=monthly&per_page=20&page=1")
                .thread(1)
                .run();
    }


}
