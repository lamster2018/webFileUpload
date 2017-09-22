package demo.lizhiweike.com.webfiledemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 *
 *
 * 参考文档
 *
 * http://www.jianshu.com/p/21b8a1f67e72
 *
 *
 */
public class MainActivity extends AppCompatActivity {
    public static final int RESULT_CODE_CHOOSE_IMAGE = 0x3001;
    protected String TAG = "ceshi";
    private FrameLayout webContainer;
    private WebView x5web;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edit);
        editText.setText("http://chuantu.biz/#upload");
        TextView ok = (TextView) findViewById(R.id.ok);
        TextView clear = (TextView) findViewById(R.id.clear);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x5web.loadUrl(editText.getText().toString().trim());
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        webContainer = (FrameLayout) findViewById(R.id.webContainer);
        x5web = new WebView(getApplicationContext());
        x5web.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        webContainer.addView(x5web);

        initSetting();
        x5web.setWebViewClient(new WeikeWebviewClient());
        x5web.setWebChromeClient(new WeikeWebviewChromeClient());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        x5web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        x5web.setHorizontalScrollBarEnabled(true);//滑动块
        x5web.setVerticalScrollBarEnabled(true);

        WebSettings setting = x5web.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setDomStorageEnabled(true);
        setting.setLoadWithOverviewMode(true);//设置加载进来的页面自适应手机屏幕
        setting.setUseWideViewPort(true);
        setting.setBuiltInZoomControls(false);//设置页面可缩放,必须把缩放按钮禁掉,不然无法取消
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webContainer.removeView(x5web);
    }

    public void startAlbum() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent, "选择图片"),
                    RESULT_CODE_CHOOSE_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.putExtra("return-data", true);
            startActivityForResult(intent, RESULT_CODE_CHOOSE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ");
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CODE_CHOOSE_IMAGE) {
            Uri result = data.getData();
            if (uploadMessageAboveL != null) {
                Uri[] results = null;
                results = new Uri[]{result};
                uploadMessageAboveL.onReceiveValue(results);
                uploadMessageAboveL = null;
            }
        }
    }


    public class WeikeWebviewChromeClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = valueCallback;
            startAlbum();
            return true;
        }

//        @Override
//        public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
//            super.openFileChooser(valueCallback, s, s1);
//        }
        //    // For Android 3.0+
//    public void openFileChooser(ValueCallback uploadMsg) {
//        //打开图库
//    }
//
//    //3.0--版本
//    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//        openFileChooser(uploadMsg);
//    }
//
//
//    // For Android 4.1
//    public void openFileChooser(ValueCallback uploadMsg, String acceptType, String capture) {
//        openFileChooser(uploadMsg);
//    }
    }

}
