package android.com.hotcold;

import android.app.Activity;
import android.com.hotcold.androidapp1.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import network.Backend;


public class SendNewsForm extends Activity {

    // View components
    Button sendNewsButton;
    ImageView imgPreview;
    EditText textNew;
    Activity activity;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
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

        // Capture the intent of camera, and set it to imgage preview
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            imageNews = (Bitmap) extras.get("data");
            imgPreview.setImageBitmap(imageNews);
        }
    }

    // Launch camera to get a picture
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }

}
