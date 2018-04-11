package org.faqrobot.textchat.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import org.faqrobot.textchat.R;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        final String con = "protected void onDraw(Canvas canvas) {" +"<br>"+
                "        super.onDraw(canvas);" +"<br>"+
                "        mPaint.setColor(Color.GRAY);" +"<br>"+
                "        mPaint.setAntiAlias(true);" +"<br>"+
                "        //FILL填充, STROKE描边,FILL_AND_STROKE填充和描边" +"<br>"+
                "        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);" +"<br>"+
                "        //获取宽高" +"<br>"+
                "        int with = getWidth();" +"<br>"+
                "        int height = getHeight();" +"<br>"+
                "        //半径" +"<br>"+
                "        float radius = with / 4;" +"<br>"+
                "        //宽；高；半径；画笔  (圆形圆心已经确认-正方形的中心)" +"<br>"+
                "        canvas.drawCircle(with / 2, with / 2, radius, mPaint);" +"<br>"+
                "        mPaint.setColor(Color.BLUE);" +"<br>"+
                "        //用于父容器大小的界限-圆形所在的范围" +"<br>"+
                "        oval.set(with / 2 - radius, with / 2 - radius," +"<br>"+
                "                 with / 2 + radius, with / 2 + radius);" +"<br>"+
                "        //根据进度画圆弧（圆弧的父容器；圆弧的起始角度；圆弧的角度；半径连线；画笔）" +"<br>"+
                "        canvas.drawArc(oval, 0, 30, true, mPaint);" +"<br>"+
                "" +"<br>"+
                "        mPaint.setColor(Color.RED);" +"<br>"+
                "        canvas.drawArc(oval, 90, 30, true, mPaint);" +"<br>"+
                "    }";
        BridgeWebView b = (BridgeWebView) findViewById(R.id.oneweb);
        b.getSettings().setJavaScriptEnabled(true);
        b.setDefaultHandler(new DefaultHandler());
        b.loadUrl("file:///android_asset/web.html");
        b.registerHandler("oneweb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("网页传递数据为: ", data);
                function.onCallBack(con);
            }
        });
    }
}
