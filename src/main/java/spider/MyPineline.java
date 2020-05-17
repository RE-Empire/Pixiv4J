package spider;


import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MyPineline implements Pipeline {
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.get("url").toString();

        System.out.println(url);
        String goods = resultItems.get("图片标识").toString();
        if (url == null) {
            resultItems.setSkip(true);
        }
//        Downloads.downloadPicture(url, goods);

    }
}
