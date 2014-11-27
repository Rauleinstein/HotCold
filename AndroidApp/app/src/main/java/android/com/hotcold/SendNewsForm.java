package android.com.hotcold;

import android.app.Activity;
import android.com.hotcold.androidapp1.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import network.Backend;


public class SendNewsForm extends Activity {

    // View components
    Button sendNewsButton;
    ImageView imgPreview;
    EditText textNew;


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

        sendNewsButton = (Button) findViewById(R.id.sendNewsButton);
        sendNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO añadir funcuinalidad
                Backend.sendUsersNews("contenido");
            }
        });


        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO añadir funcionalidad
            }
        });

        textNew = (EditText) findViewById(R.id.textNew);

    }


}
