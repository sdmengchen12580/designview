package org.faqrobot.textchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.faqrobot.textchat.five.FiveActivity;
import org.faqrobot.textchat.four.FourActivity;
import org.faqrobot.textchat.one.OneActivity;
import org.faqrobot.textchat.three.ThreeActivity;
import org.faqrobot.textchat.two.TwoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttons[] = new Button[13];
    private int buttonsId[] =
            {
                    R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
                    R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
                    R.id.button11, R.id.button12, R.id.button13,
            };

    private Class<?> classes[] =
            {
                    OneActivity.class, TwoActivity.class, ThreeActivity.class, FourActivity.class,FiveActivity.class
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(buttonsId[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    private void changeAct(Class<?> act) {
        startActivity(new Intent(this, act));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                changeAct(classes[0]);
                break;
            case R.id.button2:
                changeAct(classes[1]);
                break;
            case R.id.button3:
                changeAct(classes[2]);
                break;
            case R.id.button4:
                changeAct(classes[3]);
                break;
            case R.id.button5:
                changeAct(classes[4]);
                break;
            default:
                break;
        }
    }
}
