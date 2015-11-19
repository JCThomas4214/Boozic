package comjason_lewisg.httpsgithub.boozic.Controllers;


import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import comjason_lewisg.httpsgithub.boozic.Handlers.FavoritesAdapterHandler;
import comjason_lewisg.httpsgithub.boozic.MainActivity;

public class RemoveFromFavoritesController {

    MainActivity m;

    public void onCreate() {}

    public RemoveFromFavoritesController(MainActivity m) {
        this.m = m;
    }

    public void removeFavoritesList(final FavoritesAdapterHandler mAdapter) {


        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    String android_id = Settings.Secure.getString(m.applicationContext.getContentResolver(),Settings.Secure.ANDROID_ID);
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/products/deleteFromFavourites?");
                    //append Device id
                    urlString.append("DeviceId=").append( android_id );
                    //append all items in remove list
                    int size = mAdapter.getRemovedList().size();
                    String tmp;
                    urlString.append("&ProductIds=");
                    for (int i = 0; i < size; i++) {
                        if (i+1 < size) {
                            tmp = mAdapter.getRemovedList().get(i).productID + ",";
                            urlString.append(tmp);
                        }
                        else urlString.append(mAdapter.getRemovedList().get(i).productID);
                    }

                    Log.v("URL", urlString.toString());
                    URL url = new URL(urlString.toString());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();
                        return true;
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                mAdapter.clearRemoveList();
            }
        }.execute();
    }
}
