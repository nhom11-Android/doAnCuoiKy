package myHelper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpWorking extends AsyncTask<String, Void, String> {
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        test();
        return null;
    }

    public static void test() {
//        String url_txt = "https://github.com/nhom11-Android/doAnCuoiKy/blob/main/thuchanh3_LTM_/src/thuchanh3_ltm_/cauhoi_ALTP.txt";
        String url_txt = "http://192.168.1.5:3456/cauhoi_ALTP.txt";
        URL url;
        String out;

        {
            try {
                url = new URL(url_txt);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((out = bufferedReader.readLine()) != null) {
                    Log.d("get file", "instance initializer: " + out);
                }
                ;
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
