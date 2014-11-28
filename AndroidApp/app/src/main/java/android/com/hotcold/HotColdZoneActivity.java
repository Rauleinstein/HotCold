package android.com.hotcold;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.com.hotcold.androidapp.R;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HotColdZoneActivity extends Activity implements  OnGestureListener {

    private static final String ACTIVITY_TAG = "HotColdZoneActivity";
    private static final int EFFECT_HOT_ACTIVE = 1;
    private static final  int EFFECT_COLD_ACTIVE = 2;

    private GestureDetector gDetector;
    private LinearLayout layoutNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_cold_zone);

        initComponents();
    }

    private void initComponents (){
        gDetector = new GestureDetector(getApplicationContext(),this);

        // Define de drag and drop event
        layoutNew = (LinearLayout) findViewById(R.id.layoutNews);


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

    private void makeEffect(int effectType) {

        Animation slide;

        switch (effectType){
            case 1:
                layoutNew.setBackgroundColor(Color.RED);
                slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_up);
                layoutNew.startAnimation(slide);
                break;
            case 2:
                layoutNew.setBackgroundColor(Color.BLUE);
                slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_down);
                layoutNew.startAnimation(slide);
                break;
        }
    }
}


