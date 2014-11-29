package network;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by bott on 27/11/2014.
 */

public abstract class Backend {

    private static final String TAG = "Backend_utils";

    public static final String ADDRES = "http://hotcold.esy.es/get_news.php?tabla=";
    public static final String ADDRES2 = "http://hotcold.esy.es/save_news.php?tabla=";
    public static final String TABLE_NOTICIAS = "Noticias";
    public static final String TABLE_HOT_NEWS = "HotNews";
    public static final String TABLE_COLD_NEWS = "ColdNews";

    private static final String TITLE = "title";

    private static class AjSendNews extends AsyncTask<News, Void, JSONArray>{

        private Exception exception;

        @Override
        protected JSONArray doInBackground(News... noticia) {
            try {
                String title = "title="+noticia[0].getTitle();
                String description = "description="+noticia[0].description;
                String link = "link="+noticia[0].link;
                String guid = "guid="+noticia[0].guid;
                String pubDate = "pubDate="+noticia[0].pubDate;
                String temperatura = "temperatura="+noticia[0].temperatura;
                String latitud = "latitud="+noticia[0].latitud;
                String longitud = "longitud="+noticia[0].longitud;

                String tabla = "ColdNews";
                String noticiaurl = ADDRES2 + tabla +"&"+title+"&"+ description+"&"+link+"&"+guid+"&"+pubDate+"&"+temperatura+"&"+latitud+"&"+longitud ;
                System.out.println(noticiaurl);
                URL url = new URL(noticiaurl);
                // read from the URL
                Scanner scan = new Scanner(url.openStream());
                String str = new String();
                while (scan.hasNext())
                    str += scan.nextLine();
                scan.close();
                Log.i(TAG, str);
                return null;

            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }
    }

    private static class Asinc extends AsyncTask<String, Void, JSONArray>{

        private Exception exception;

        @Override
        protected JSONArray doInBackground(String... addr) {
            try {
                URL url = new URL(addr[0]);
                // read from the URL
                Scanner scan = new Scanner(url.openStream());
                String str = new String();
                while (scan.hasNext())
                    str += scan.nextLine();
                scan.close();
                System.out.println(str);
                try {
                    JSONArray jsonArray = new JSONArray(str);

                    return jsonArray;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }
    }

    public JSONArray GetNews(String addr) throws Exception {
        Asinc asi = new Asinc();
        asi.execute(addr);
        JSONArray ret = asi.get();
        return ret;

    }
    public static JSONArray SendNews(News noticia) throws Exception {
        AjSendNews send = new AjSendNews();
        send.execute(noticia);
        JSONArray ret = send.get();
        return ret;

    }

    public static void sendUsersNews(Bitmap photo, String content){

        // TODO enviar foto si existe y el contenido


    }
}
