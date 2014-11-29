package network;

import android.app.Activity;
import android.com.hotcold.androidapp.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by bott on 29/11/2014.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Activity activity;

    public DownloadImageTask(Activity _activity, ImageView bmImage) {
        this.bmImage = bmImage;
        activity = _activity;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error recovering urlIMG", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {

        if(activity != null) {
            if (result == null)
                bmImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.videodefault));
            else
                bmImage.setImageBitmap(result);
        }
    }
}
