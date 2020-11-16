package com.veveup.adream_2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/**
 * 游戏Activity 根据layout文件 将游戏页面显示出来
 */
public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity=null;
    TextView textView ;
    private int score=0;
    public MainActivity(){
        mainActivity=this;
    }
    public static MainActivity getMainActivity(){
        return mainActivity;
    }
    public void clearScore(){
        score=0;
        showScore();
    }
    public void addScore(int soore){
        score+=soore;
        showScore();
    }
    public void showScore(){
        textView.setText(score+" ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.score);

    }
}
