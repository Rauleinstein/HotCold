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
public class Backend {

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

    public static void sendUsersNews(Bitmap photo, String content){

        // TODO enviar foto si existe y el contenido


    }
}
