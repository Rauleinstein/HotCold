package android.com.hotcold;

import android.app.Activity;
import android.com.hotcold.androidapp.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hotcold.utilitys.Utils;
import network.Backend;
import network.DownloadImageTask;

import network.News;


public class MainActivity extends Activity {

    private static final String ACTIVITY_TAG = "Main activity";

    // Components
    ImageView imgStarred1, imgStarred2;
    TextView titleStarred1, titleStarred2;
    ListView listPrincipal;
    MyListAdapter myAdapter;

    // Manage JSONObject - News
    private String ID_NEW = "id";
    private String TITLE = "title";
    private String DESCRIPTION = "description";
    private String URL_NEW = "link";
    private String URL_IMG = "link";
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
        recoverNewsStars();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        fillNewsList();
        recoverNewsStars();
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


        listPrincipal = (ListView)findViewById(R.id.listPrincipalNews);
        listPrincipal.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    /**
     * Recover the 2 most voted news
     */
    private void recoverNewsStars() {

        JSONObject starredNews;
        try {
            starredNews = listNews.getJSONObject(0);
            titleStarred1.setText(starredNews.getString(TITLE));

            new DownloadImageTask(this, imgStarred1)
                    .execute(starredNews.getString(URL_IMG));

            //imgStarred1.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));

            //listNews.remove(0);
            listNews = Utils.jsonArrayRemove(listNews, 0);

            starredNews = listNews.getJSONObject(0);
            titleStarred2.setText(starredNews.getString(TITLE));
            new DownloadImageTask(this, imgStarred2)
                    .execute(starredNews.getString(URL_IMG));
            //imgStarred2.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));

            //listNews.remove(0);
            listNews = Utils.jsonArrayRemove(listNews, 0);
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
            listNews = Backend.GetNews(Backend.GET_NEWS_PHP + newsType);
        } catch (Exception e) {
            Log.e(ACTIVITY_TAG, "Cant get news...");
        }
    }

    /**
     * Fill the list of news
     */
    private void fillNewsList() {

        myAdapter = new MyListAdapter();
        listPrincipal.setAdapter(myAdapter);

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

    class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listNews.length();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final HolderAdapter holder;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                holder = new HolderAdapter();
                convertView = inflater.inflate(R.layout.item_list_new, parent, false);

                holder.titlePreview = (TextView)convertView.findViewById(R.id.listTitleNewPreview);
                holder.imgPreview = (ImageView) convertView.findViewById(R.id.listImgPreview);
                holder.element = (LinearLayout)convertView.findViewById(R.id.listItemNew);

                holder.id = position;

                convertView.setTag(holder);
            }
            else{
                holder = (HolderAdapter) convertView.getTag();
            }

            final int positionList = position;
            // Agregando identificador unico al boton
            holder.element.setTag(holder);
            holder.element.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //makeIntent(positionList);
                }
            });

            try {
                JSONObject aNew = listNews.getJSONObject(position);
                holder.titlePreview.setText(aNew.getString(TITLE));
                new DownloadImageTask(getParent(), holder.imgPreview)
                        .execute(aNew.getString(URL_IMG));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return convertView;
        }
    }

    private class HolderAdapter{
        int id;
        LinearLayout element;
        TextView titlePreview;
        ImageView imgPreview;

        public boolean equals(Object o)
        {
            HolderAdapter h = (HolderAdapter) o;

            return h.id == this.id;
        }
    }
}
