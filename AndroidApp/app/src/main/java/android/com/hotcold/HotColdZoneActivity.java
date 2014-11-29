package android.com.hotcold;

import android.app.Activity;
import android.app.Dialog;
import android.com.hotcold.androidapp.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import network.Backend;

public class HotColdZoneActivity extends Activity implements  OnGestureListener {

    private static final String ACTIVITY_TAG = "HotColdZoneActivity";
    private static final int EFFECT_HOT_ACTIVE = 1;
    private static final int EFFECT_COLD_ACTIVE = 2;
    private static final int HOT_THE_NEW = 99;
    private static final int COLD_THE_NEW = 100;


    // Manage JSONArray
    JSONArray listNews;
    int indexListNews = 0;
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

    private GestureDetector gDetector;
    // Components
    private LinearLayout layoutNew;
    private TextView title, description;
    private ImageView imgPreview;
    private Button botonAnterior;
    private Button botonSiguiente;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_cold_zone);

        initComponents();
        changeNew();

    }

    private void initComponents (){
        gDetector = new GestureDetector(getApplicationContext(),this);

        getSomeNews(Backend.TABLE_NOTICIAS);

        layoutNew = (LinearLayout) findViewById(R.id.layoutNews);
        title = (TextView) findViewById(R.id.textTitleNew);
        description = (TextView) findViewById(R.id.textContentNew);
        imgPreview = (ImageView) findViewById(R.id.listImgPreview);

        botonAnterior = (Button) findViewById(R.id.button5);
        botonAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousNews();
            }
        });
        botonSiguiente = (Button) findViewById(R.id.button6);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextNew();
            }
        });
        Button boton7 = (Button) findViewById(R.id.button7);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hot_cold_zone, menu);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Es necesario para utilizar el modo de reconocimiento de gestos
        return gDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent start, MotionEvent finish, float velocityX, float velocity){

        // Capture finger movement
        // Capture Upper movement
        if(start.getRawY() - finish.getRawY() > 200 ){
            Toast.makeText(getApplicationContext(), "¡Hot!", Toast.LENGTH_SHORT).show();
            makeEffect(EFFECT_HOT_ACTIVE);
        }
        // Capture lower movement
        else if (start.getRawY() - finish.getRawY() < -200 ){
            Toast.makeText(getApplicationContext(), "¡Cold!", Toast.LENGTH_SHORT).show();
            makeEffect(EFFECT_COLD_ACTIVE);
        }
        return true;
    }

    /**
     * Rate the news and get out of the list
     * @param option HOT_THE_NEW to rate up, COLD_THE_NEW to rate down
     */
    private void rateNews(int option) {

        if (option == HOT_THE_NEW) {
            // TODO ratear la noticia con positivo
        }
        else if(option == COLD_THE_NEW) {
            // TODO ratear la noticia con negativo
        }

        listNews.remove(indexListNews);

        // If still new to rate
        if(listNews.length() > 0 && indexListNews>=0) {
            // If was the last news, select the previous
            if (indexListNews == listNews.length()) {
                indexListNews--;
            }
        }
        else {
            Log.i(ACTIVITY_TAG, "No quedan noticias");
            Toast.makeText(getApplicationContext(), "¡Has votado todas las noticias!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplication(), MainActivity.class));
        }
    }


    private void previousNews() {

        indexListNews--;
        changeNew();

    }

    private void nextNew(){

        indexListNews++;
        changeNew();
    }

    /**
     * Change the new show in the view
     */
    private void changeNew() {

        // Enabled or disabled previous button
        if(indexListNews > 0)
            botonAnterior.setEnabled(true);
        else
            botonAnterior.setEnabled(false);

        if(indexListNews < listNews.length()-1)
            botonSiguiente.setEnabled(true);
        else
            botonSiguiente.setEnabled(false);

        layoutNew.setBackgroundColor(Color.TRANSPARENT);

        JSONObject jsonNew;
        try {
            jsonNew = listNews.getJSONObject(indexListNews);
            title.setText(jsonNew.getString(TITLE));
            description.setText(jsonNew.getString(DESCRIPTION));

            //imgPreview.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));
        } catch (JSONException e) {
            Log.e(ACTIVITY_TAG, "Cant open JSON, " + e);
        }

    }

    /**
     * Make the transition for the news and change colors
     * @param effectType Effect type, must be EFFECT_HOT_ACTIVE or EFFECT_COLD_ACTIVE
     */
    private void makeEffect(int effectType) {

        Animation slide;

        switch (effectType){
            case EFFECT_HOT_ACTIVE:
                //layoutNew.setBackground(drawableUp);
                slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_up);
                slide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutNew.setBackgroundColor(Color.RED);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rateNews(HOT_THE_NEW);
                        HotColdZoneActivity.this.changeNew();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

                layoutNew.startAnimation(slide);

                // TODO popup disabled
                //Dialog dialog = makePopUp(getResources().getDrawable(R.drawable.overlay_up));
                //dialog.getWindow().getAttributes().windowAnimations = R.anim.slide_out_up;

                break;
            case EFFECT_COLD_ACTIVE:
                //layoutNew.setBackground(drawableDown);
                slide=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_down);
                slide.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        layoutNew.setBackgroundColor(Color.BLUE);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rateNews(COLD_THE_NEW);
                        HotColdZoneActivity.this.changeNew();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                layoutNew.startAnimation(slide);
                break;
        }
    }

    /**
     * Show a Dialog whit a transparent background
     * @param imgSource
     * @return
     */
    private Dialog makePopUp(Drawable imgSource){

        LinearLayout layout = new LinearLayout(this);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        ImageView imgOverlay = new ImageView(getApplicationContext());
        imgOverlay.setImageDrawable(imgSource);
        layout.addView(imgOverlay);

        final Dialog dialog = new Dialog(this, R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        dialog.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }
}


