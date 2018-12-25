package com.example.demoyan;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demoyan.Adapter.PageAdapter;
import com.example.demoyan.config.Config;
import com.example.demoyan.http.HttpUtil;
import com.example.demoyan.netty.advertise.LockClient;
import com.example.demoyan.netty.advertise.lock.AdvertiseServer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private EditText editText;
    private static TextView textView;
    private static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AdvertiseServer(8869).start();
        editText = findViewById(R.id.string);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
//        imageView.setImageResource(R.drawable.house1);
        button = findViewById(R.id.click);
        button.setOnClickListener(this);
        @SuppressLint("WrongViewCast") TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("tab4"));

        //设置adapter，滑动时间
        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String message = editText.getText().toString();
                        LockClient.getClient().sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String string = (String) msg.obj;
                    textView.setText(string);
                    break;
                case 2:
                    String string2 = (String) msg.obj;
                    getImage(string2);
                    break;
                case 3:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }

    };

    public static void getImage(final String string){

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpUtil.sendOkHttpRequest(Config.baseUrl + string, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("", "handleMessage: "+ e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        byte[] bytes = response.body().bytes();
                        //response.body().close();
                        //把byte字节组装成图片
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Log.e("", "handleMessage: "+bmp );
                        Message message = new Message();
                        message.what = 3;
                        message.obj = bmp;
                        MainActivity.handler.sendMessage(message);
                    }
                });
            }
        }).start();
    }
}
