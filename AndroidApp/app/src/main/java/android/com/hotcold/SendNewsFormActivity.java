package android.com.hotcold;

import android.app.Activity;
import android.com.hotcold.androidapp.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import network.Backend;


public class SendNewsFormActivity extends Activity {

    private static final String ACTIVITY_TAG = "SendNewsFormActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;

    // View components
    Button sendNewsButton;
    ImageView imgPreview, videoPreview;
    EditText textNew;
    Activity activity;

    static Bitmap imageNews;

    String imageNewsFileName; //Name of the image to will be send


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_news_form);

        initComponents();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_news_form, menu);
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

    private void initComponents () {

        activity = this;

        videoPreview = (ImageView) findViewById(R.id.videoPreview);
        videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO añadir funcionalidad
                dispatchTakeVideoIntent();
            }
        });

        sendNewsButton = (Button) findViewById(R.id.sendNewsButton);
        sendNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO añadir funcionalidad
                if(textNew.getText().toString().trim().length()>0) {
                    Backend.sendUsersNews(imageNews, textNew.getText().toString());
                }
                else{
                    Toast.makeText(activity, "No has introducido texto para la noticia", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO añadir funcionalidad
                dispatchTakePictureIntent();

            }
        });

        textNew = (EditText) findViewById(R.id.textNew);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Capture the intent of camera for a PHOTO, and set it to image preview
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            imageNews = (Bitmap) extras.get("data");
            imgPreview.setImageBitmap(imageNews);
        }

        // Capture the intent of camera for a VIDEO, and set it to video preview
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri videoUri = data.getData();
            String videoFile = videoUri.toString();
            Log.i(ACTIVITY_TAG, "Video capture: " + videoFile);
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile,
                    MediaStore.Images.Thumbnails.MINI_KIND);

            if(thumbnail != null) {
                videoPreview.setImageBitmap(thumbnail);
            }
            else{
                videoPreview.setImageDrawable(getResources().getDrawable(R.drawable.videodefault));
            }

        }

    }

    // Launch camera to get a picture
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

}
