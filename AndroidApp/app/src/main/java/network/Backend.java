package network;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

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
    public static final String TABLE_NOTICIAS = "Noticias";
    public static final String TABLE_HOT_NEWS = "HotNews";
    public static final String TABLE_COLD_NEWS = "ColdNews";

    private static final String TITLE = "title";

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
                Log.i(TAG, str);
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

    /**
     * Get some news form the system
     * @param addr
     * @return
     * @throws Exception
     */
    public static JSONArray GetNews(String addr) throws Exception {
        Asinc asi = new Asinc();
        asi.execute(addr);
        JSONArray ret = asi.get();
        return ret;

    }

    public static void sendUsersNews(Bitmap photo, String content){

        // TODO enviar foto si existe y el contenido


    }
}
