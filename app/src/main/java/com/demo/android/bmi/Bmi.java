package com.demo.android.bmi;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class Bmi extends AppCompatActivity {

    Button button;
    EditText fieldHeight;
    EditText fieldWeight;
    TextView textHeight,textWeight;

    public static final String TAG = "LifeCycle";

    //加入偏好設定
    public static final String PREF="BMI_PREF";
    public static final String PREF_HEIGHT="BMI_Height";
    public static final String PREF_WEIGHT="BMI_Weight";

    //Restore preferences
    private void restorePrefs(){
        SharedPreferences settings=getSharedPreferences(PREF,0);
        String pref_height=settings.getString(PREF_HEIGHT,"");
        String pref_weight=settings.getString(PREF_WEIGHT,"");
//
//
//        if (!"".equals(pref_height)){
//            fieldHeight.setText(pref_height);
////            fieldWeight.requestFocus();
//        }
//
//        if (!"".equals(pref_weight)){
//            fieldWeight.setText(pref_weight);
////            fieldWeight.requestFocus();
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Bmi.onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Bmi.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Bmi.onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Bmi.onPause");
    //Save user preferences. use Editor object to make changes.
        SharedPreferences settings=getSharedPreferences(PREF,0);
//        settings.edit().putString(PREF_HEIGHT,
//                fieldHeight.getText().toString()).commit();
//        settings.edit().putString(PREF_WEIGHT,
//                fieldWeight.getText().toString()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Bmi.onResume");
        restorePrefs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"Bmi.onRestart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Bmi.onCreate");
        setContentView(R.layout.activity_bmi);
        findViews();
        setListeners();

//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);

//        playViewAnimationTranslate();

//        //屬性動畫開始
//        playFrameAnimation4();
        playPropertyAnimation();
//        //屬性動畫束


//        DisplayMetrics monitorsize =new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(monitorsize);
//        String opt="手機螢幕解析度為：" + monitorsize.widthPixels
//                + "x" + monitorsize.heightPixels +"x" +monitorsize.widthPixels;
//        System.out.println("opt=" + opt);

//        transferMethod();

    }

    void findViews(){
        button = (Button) findViewById(R.id.submit);
        fieldHeight = (EditText) findViewById(R.id.height);
        fieldWeight = (EditText) findViewById(R.id.weight);
        textHeight=(TextView) findViewById(R.id.textHeight);
        textWeight=(TextView)findViewById(R.id.textWeight);
    }

    void setListeners(){
        button.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(Bmi.this,Report.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT",fieldHeight.getText().toString());
            bundle.putString("KEY_WEIGHT",fieldWeight.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    void openOptionsDialog(){
        Toast.makeText(Bmi.this,"顯示Toast訊息",Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog =
                ProgressDialog.show(Bmi.this, "處理中...", "請等一會，處理完畢會自動結束...");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
//                    result.setTextColor(Color.BLUE);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        };
        thread.start();
    }

////homework
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_getmonitorsize);
//
//        //---------------------需引用 android.util.DisplayMetrics--------------------
//        DisplayMetrics monitorsize =new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(monitorsize);
//
//        //-----------------------------------------------------------------------------------------
//
//        String opt="手機螢幕解析度為：" + monitorsize.widthPixels
//
//                + "x" + monitorsize.heightPixels;
//
//        TextView textview=(TextView)findViewById(R.id.textView1);
//        textview.setText(opt);
//    }

//
    void playViewAnimationTranslate(){
        //要先產生一個動畫物件,並參數的地方要設定動畫資源
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim_translate);
        fieldHeight.startAnimation(animation);
        fieldWeight.startAnimation(animation);
        button.startAnimation(animation);
    }


    //底下兩個二選一
    void playFrameAnimation4(){
        AnimationDrawable anim1=(AnimationDrawable) fieldHeight.getBackground();
        anim1.start();
    }



    void playPropertyAnimation(){
        ObjectAnimator oaRotate =ObjectAnimator.ofFloat(fieldHeight,"rotation",0,360);
        oaRotate.setDuration(1000);
        oaRotate.setRepeatCount(5);  //若是採用依序播放動畫方式,第一段動畫的播放次數不可設為無限,否則其他動畫看不到
        oaRotate.setRepeatMode(ObjectAnimator.REVERSE);
        oaRotate.addListener(new Animator.AnimatorListener() {//監聽動畫事件,非必要
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(Bmi.this,"第一段動畫結束",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        //建立移動動畫
        //第二個參數是表示水平方向移動,Y表示上下移動,
        //第三個參數是從0移動到400
//        android:duration 动画持续时间,时间以毫秒为单位
//        android:startOffset 动画之间的时间间隔，从上次动画停多少时间开始执行下个动画
//        android:interpolator 指定一个动画的插入器
//        android:fillAfter 当设置为true ，该动画转化在动画结束后被应用
//        android:repeatMode 定义重复的行为
//        android:repeatCount 动画的重复次数


        ObjectAnimator oaForadtextHeight=ObjectAnimator.ofFloat(textHeight,"x",-400,0);
        oaForadtextHeight.setDuration(1000);
//        oaForadtextHeight.setInterpolator(It);
//        oaForadtextHeight.setRepeatCount(1);
//        oaForadtextHeight.setInterpolator(ll);
//        oaForadtextHeight.setRepeatMode(ObjectAnimator.RESTART);
        fieldHeight.requestFocus();
        ObjectAnimator oaForadHeight=ObjectAnimator.ofFloat(fieldHeight,"x",-400,0);
        oaForadHeight.setDuration(1000);

//        oaForadHeight.setRepeatCount(1);
//        oaForadHeight.setRepeatMode(ObjectAnimator.REVERSE);


        ObjectAnimator oaForadtextWeight=ObjectAnimator.ofFloat(textWeight,"x",-400,0);
        oaForadtextWeight.setDuration(1000);
//        oaForadWeight.setRepeatCount(ObjectAnimator);
//        oaForadtextWeight.setRepeatMode(ObjectAnimator.REVERSE);

        fieldWeight.requestFocus();
        ObjectAnimator oaForadWeight=ObjectAnimator.ofFloat(fieldWeight,"x",-400,0);
        oaForadWeight.setDuration(1000);

//        oaForadWeight.setRepeatCount(ObjectAnimator);
//        oaForadWeight.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator oaForadButton=ObjectAnimator.ofFloat(button,"x",-400,0);
        oaForadButton.setDuration(1000);
//        oaForadButton.setRepeatCount(ObjectAnimator.INFINITE);
//        oaForadButton.setRepeatMode(ObjectAnimator.REVERSE);



        AnimatorSet as=new AnimatorSet();//決定前面寫到的動畫效困是用那一種動畫,先後順序
//        as.playSequentially(oaRotate,oaForad);//先旋轉再平移
        as.playSequentially(
                oaForadtextHeight
                ,oaForadHeight
                ,oaForadtextWeight
                ,oaForadWeight
                ,oaForadButton);//先旋轉再平移
        as.start();
    }


    private void transferMethod() {
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();


        TranslateAnimation translateAnim2 = new TranslateAnimation(0, -300, 0, -500);

        translateAnim2.setDuration(600);

        translateAnim2.setInterpolator(interpolator);

        translateAnim2.setFillAfter(true);

        fieldHeight.startAnimation(translateAnim2);
    }
}
