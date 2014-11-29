package android.com.hotcold;

import android.app.Activity;
import android.com.hotcold.androidapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import network.Backend;
import network.DownloadImageTask;

import network.Backend;
import network.News;


public class MainActivity extends Activity {

    private static final String ACTIVITY_TAG = "Main activity";

    // Components
    ImageView imgStarred1, imgStarred2;
    TextView titleStarred1, titleStarred2;

    // Manage JSONObject - News
    private String ID_NEW = "id";
    private String TITLE = "title";
    private String DESCRIPTION = "description";
    private String URL = "link";
    private String DATE = "pubDate";
    private String TEMPERATURE = "temperatura";
    private String LATITUDE = "latitud";
    private String LONGITUDE = "longitud";

    Button boton1, boton2;

    //Manage news list
    JSONArray listNews;
    int indexListNews=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        
        getSomeNews(Backend.TABLE_NOTICIAS);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        recoverNewsStars();
        //TODO fillNewsList();

        News noticia = new News("a","a","a",1,"a","a", "a");
        try {
            Backend.SendNews(noticia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents(){

        titleStarred1 = (TextView) findViewById(R.id.titleStar1);
        titleStarred2 = (TextView) findViewById(R.id.titleStar2);

        imgStarred1 = (ImageView) findViewById(R.id.imgStar1);
        imgStarred2 = (ImageView) findViewById(R.id.imgStar2);

        boton1 = (Button) findViewById(R.id.button);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), SendNewsFormActivity.class));
            }
        });

        boton2 = (Button)findViewById(R.id.button2);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), HotColdZoneActivity.class));
            }
        });
    }

    /**
     * Recover the 2 most voted news
     */
    private void recoverNewsStars() {

        JSONObject starredNews;
        try {
            starredNews = listNews.getJSONObject(0);
            titleStarred1.setText(starredNews.getString(TITLE));

            new DownloadImageTask(imgStarred1)
                    .execute(starredNews.getString(URL));

            //imgStarred1.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));

            listNews.remove(0);

            starredNews = listNews.getJSONObject(0);
            titleStarred2.setText(starredNews.getString(TITLE));
            new DownloadImageTask(imgStarred2)
                    .execute(starredNews.getString(URL));
            //imgStarred2.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));

            listNews.remove(0);
        } catch (JSONException e) {
            Log.e(ACTIVITY_TAG, "Cant get starred news: " + e);
        }
    }

    /**
     * Get a list of news
     * @param newsType Type of news to get. Types im Backend class
     */
    private void getSomeNews(String newsType){
        try {
            listNews = Backend.GetNews(Backend.ADDRES + newsType);
        } catch (Exception e) {
            Log.e(ACTIVITY_TAG, "Cant get news...");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
