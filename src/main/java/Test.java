import spider.WebPivix;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.Scanner;

public class Test {
    static String url;

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",1080)));
        Scanner scanner=new Scanner(System.in);
        System.out.println("1or2");
        String s = scanner.next();
        if (s.equals("1")){
            System.out.println("输入画师id");
            String uid = scanner.next();
            url="https://www.pixiv.net/ajax/user/"+uid+"/profile/all";
            Spider.create(new WebPivix()).addUrl(url).setDownloader(httpClientDownloader).thread(1).run();
        }else if (s.equals("2")){
            System.out.println("输入图片id");
            String uid = scanner.next();
            url ="https://www.pixiv.net/artworks/"+uid;
            Spider.create(new WebPivix()).addUrl(url).setDownloader(httpClientDownloader).thread(1).run();
        }
        System.out.println("完成");


    }
}
