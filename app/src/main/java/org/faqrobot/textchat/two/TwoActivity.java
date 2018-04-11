package org.faqrobot.textchat.two;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import org.faqrobot.textchat.R;

public class TwoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        designView();
        webShow();
    }


    private void designView() {
        DesignViewGroup designViewGroup = (DesignViewGroup) findViewById(R.id.image_layout);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] string = {"从我写代码那天起，我就没有打算写代码啊啊啊啊啊啊啊", "从我写代码那天起",
                "我就没有打算写代码", "没打算", "写代码", "没打算", "写代码"};
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText(string[i]);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(Color.parseColor("#00aaff"));
            designViewGroup.addView(textView, lp);
        }
    }

    private void webShow() {
        final String con = "protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { " +"<br>"+
                "    super.onMeasure(widthMeasureSpec, heightMeasureSpec);" +"<br>"+
                "    //父容器大小" +"<br>"+
                "    int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);" +"<br>"+
                "    int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);" +"<br>"+
                "    int modeWidth = MeasureSpec.getMode(widthMeasureSpec);" +"<br>"+
                "    int modeHeight = MeasureSpec.getMode(heightMeasureSpec);" +"<br>"+
                "    int width = 0;" +"<br>"+
                "    int height = 0;" +"<br>"+
                "    int lineWidth = 0;" +"<br>"+
                "    int lineHeight = 0;" +"<br>"+
                "    int count = getChildCount();" +"<br>"+
                "    int left = getPaddingLeft();" +"<br>"+
                "    int top = getPaddingTop();" +"<br>"+
                "    // 遍历每个子元素" +"<br>"+
                "    for (int i = 0; i 小于count; i++) {" +"<br>"+
                "    View child = getChildAt(i);" +"<br>"+
                "    if (child.getVisibility() == GONE)" +"<br>"+
                "    continue;" +"<br>"+
                "    // 测量每一个child的宽和高" +"<br>"+
                "    measureChild(child, widthMeasureSpec, heightMeasureSpec);" +"<br>"+
                "    // 得到child的lp" +"<br>"+
                "    ViewGroup.LayoutParams lp = child.getLayoutParams();" +"<br>"+
                "    // 当前子控件实际占据的宽度" +"<br>"+
                "    int childWidth = child.getMeasuredWidth() + childHorizontalSpace;" +"<br>"+
                "    // 当前子控件实际占据的高度" +"<br>"+
                "    int childHeight = child.getMeasuredHeight() + childVerticalSpace;" +"<br>"+
                "" +"<br>"+
                "    if (lp != null &amp;&amp; lp instanceof MarginLayoutParams) {" +"<br>"+
                "    MarginLayoutParams params = (MarginLayoutParams) lp;" +"<br>"+
                "    childWidth += params.leftMargin + params.rightMargin;" +"<br>"+
                "    childHeight += params.topMargin + params.bottomMargin;" +"<br>"+
                "    }" +"<br>"+
                "" +"<br>"+
                "    /**" +"<br>"+
                "    * 单行的宽度大于行宽" +"<br>"+
                "    */" +"<br>"+
                "    if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {" +"<br>"+
                "    width = Math.max(lineWidth, childWidth); // 取最大的" +"<br>"+
                "    lineWidth = childWidth; // 重新开启新行，开始记录" +"<br>"+
                "    // 叠加当前高度" +"<br>"+
                "    height += lineHeight;" +"<br>"+
                "    // 开启记录下一行的高度" +"<br>"+
                "    lineHeight = childHeight;" +"<br>"+
                "    child.setTag(new Location(left, top + height, childWidth + left - childHorizontalSpace, height + child.getMeasuredHeight() + top));" +"<br>"+
                "    } else {// 否则累加值lineWidth,lineHeight取最大高度" +"<br>"+
                "    child.setTag(new Location(left + lineWidth, top + height, lineWidth + childWidth - childHorizontalSpace + left, height + child.getMeasuredHeight() + top));" +"<br>"+
                "    lineWidth += childWidth;" +"<br>"+
                "    lineHeight = Math.max(lineHeight, childHeight);" +"<br>"+
                "    }" +"<br>"+
                "    }" +"<br>"+
                "    width = Math.max(width, lineWidth) + getPaddingLeft() + getPaddingRight();" +"<br>"+
                "    height += lineHeight;" +"<br>"+
                "    sizeHeight += getPaddingTop() + getPaddingBottom();" +"<br>"+
                "    height += getPaddingTop() + getPaddingBottom();" +"<br>"+
                "    //判断容器限制情况" +"<br>"+
                "    setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width," +"<br>"+
                "    (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);" +"<br>"+
                "    }";
        BridgeWebView b = (BridgeWebView) findViewById(R.id.twoweb);
        b.setDefaultHandler(new DefaultHandler());
        b.loadUrl("file:///android_asset/web.html");
        b.registerHandler("twoweb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("网页传递数据为: ", data);
                function.onCallBack(con);
            }
        });
    }

}
