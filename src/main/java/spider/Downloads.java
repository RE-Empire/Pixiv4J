package spider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloads {
        public static void downloadPicture(String u, String goods) {
            String dirs = System.getProperty("user.dir")+"/src/main/resources/";
            URL url = null;
            try {
                url = new URL(u);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                File file = new File(dirs + goods +".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024 * 50];
                int length;
                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                System.out.println("已经下载：" + dirs + goods);
                dataInputStream.close();
                fileOutputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
