package comjason_lewisg.httpsgithub.boozic.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class FeedbackController {

    public void onCreate() {}

    public FeedbackController() {}

    public void sendFeedback(String input) {
        sendFeedbackInBackground(input);
    }

    private void sendFeedbackInBackground(final String input) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... urls) {
                try {
                    StringBuilder urlString = new StringBuilder();
                    //TODO: Store the Server IP in global locaiton
                    urlString.append("http://54.210.175.98:9080/api/GCM/SendEmail?");
                    //append input
                    String value = URLEncoder.encode(input, "UTF-8");
                    urlString.append("EmailBody=").append(value);

                    Log.v("FeedbackURL", urlString.toString());
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
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response == null) {
                    Log.v("ERROR", "There was an error");
                }
            }
        }.execute();
    }
}
