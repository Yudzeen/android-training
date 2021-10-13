package ebj.yujinkun.webviewapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String WEB_URL = "https://yudzeen.github.io/my-page/";

    private WebView webView;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new MainWebViewClient());
        webView.setWebChromeClient(new MainWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        applyDarkModeSettings();

        webView.loadUrl(WEB_URL);
    }

    private class MainWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d(TAG, "Request: " + request.getUrl() + " Error: " + error);
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.d(TAG, "Request: " + request.getUrl() + " Error Response: " + errorResponse.getData());
            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "onPageStarted: " + url);
            progressBar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "onPageFinished: " + url);
            progressBar.setVisibility(View.GONE);
            updateInfo();
            super.onPageFinished(view, url);
        }
    }

    private class MainWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d(TAG, "onConsoleMessage. " + consoleMessage.sourceId() +
                    ":" + consoleMessage.lineNumber() + " " + consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }

    private void applyDarkModeSettings() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
            Log.d(TAG, "Force dark strategy available");
            WebSettingsCompat.setForceDarkStrategy(webView.getSettings(),
                    WebSettingsCompat.WEB_THEME_DARKENING_ONLY);
        } else {
            Log.d(TAG, "Force dark strategy not available");
        }

        if (DarkModeUtil.isDarkMode(this)) {
            Log.d(TAG, "Dark mode on.");
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                Log.d(TAG, "Force dark on");
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
            } else {
                Log.d(TAG, "Force dark not supported.");
            }
        } else {
            Log.d(TAG, "Dark mode off.");
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                Log.d(TAG, "Force dark off.");
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
            } else {
                Log.d(TAG, "Force dark not supported.");
            }
        }
    }

    private void updateInfo() {
        JsonObject mainObject = new JsonObject();
        JsonArray array = new JsonArray();
        for (int i = 0; i < 3; i++) {
            JsonObject object = new JsonObject();
            object.add("id", new JsonPrimitive(i));
            object.add("name", new JsonPrimitive("item " + i));
            array.add(object);
        }
        JsonObject prop2 = new JsonObject();
        for (int i = 0; i < 3; i++) {
            JsonObject temp = new JsonObject();
            temp.add("attr", new JsonPrimitive("value"));
            prop2.add("temp" + i, temp);
        }

        mainObject.add("prop",  new JsonPrimitive("value1"));
        mainObject.add("list", array);
        mainObject.add("map", prop2);

        String info = mainObject.toString();
        Log.d(TAG, "updateInfo: " + info);

        webView.evaluateJavascript("updateInfo('"+ info +"')", null);
    }

}
