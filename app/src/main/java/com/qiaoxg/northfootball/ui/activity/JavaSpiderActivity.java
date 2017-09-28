package com.qiaoxg.northfootball.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qiaoxg.northfootball.R;
import com.qiaoxg.northfootball.app.BaseActivity;
import com.qiaoxg.northfootball.utils.FileUtils;
import com.qiaoxg.northfootball.utils.JavaSpider;
import com.qiaoxg.northfootball.utils.PermissionUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JavaSpiderActivity extends BaseActivity {

    private static final String TAG = "JavaSpiderActivity";
    @BindView(R.id.inputUrl_et)
    EditText inputUrlEt;
    @BindView(R.id.webName_et)
    EditText webNameEt;
    @BindView(R.id.ok_btn)
    Button okBtn;
    @BindView(R.id.show_btn)
    Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_spider);
        ButterKnife.bind(this);
    }


    public void writeExternalStorage() {
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    getData();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.ok_btn, R.id.show_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                writeExternalStorage();
                break;
            case R.id.show_btn:
                String path = FileUtils.getHtmlTempDir();
                //解析本地的网页地址
                JavaSpider.Get_LocalHtml(path);
                break;
        }
    }

    private void getData() {
        Thread downloadThread = new Thread() {
            public void run() {

                String url = "http://www.dongqiudi.com/";
                Document document = null;
                try {
                    document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("li");
                for (Element element : elements) {
                    Elements titleElement = element.select("h2 a");
                    String title = titleElement.text();
                    String link = titleElement.attr("href").trim();

                    Elements dataElement = element.select(".time");
                    String date = dataElement.text();

                    Elements imgElement = element.select("img");

                    Log.e(TAG, "run: 链接：        " + link);
                    Log.e(TAG, "run: 标题：        " + title);
                    Log.e(TAG, "run: 发布时间： " + date);
                    System.out.println();
                    System.out.println();
                }
                Log.e(TAG, "run: size is " + elements.size());
            }
        };
        downloadThread.start();
    }
}
