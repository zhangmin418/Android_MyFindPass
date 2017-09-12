package jiyun.com.example.lenovo.android_myfindpass;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView iv_left;
    private EditText ed_phone;
    private EditText ed_captcha;//点击获取验证码按钮证码按钮
    private Button btn_captcha;
    private EditText ed_pass;
    private Button btn_find;
//    private TimeCount time;//验证码倒计时
    private int s = 0;
    private int time = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_captcha = (EditText) findViewById(R.id.ed_captcha);
        btn_captcha = (Button) findViewById(R.id.btn_captcha);
        ed_pass = (EditText) findViewById(R.id.ed_pass);
        btn_find = (Button) findViewById(R.id.btn_find);

//        time = new TimeCount(60000, 1000);//构造CountDownTimer对象


        btn_captcha.setOnClickListener(this);
        btn_find.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_captcha:
                Random random = new Random();
                s = random.nextInt(1000) + 8999;
                //通知
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //延时发送
                PendingIntent pend = PendingIntent.getActivity(this, 0, new Intent(MainActivity.this, MainActivity.class), 0);
                NotificationCompat.Builder buli = new NotificationCompat.Builder(this);
                buli.setDefaults(Notification.DEFAULT_VIBRATE);//默认震动
                buli.setTicker("请查收你的验证码!!!");
                buli.setSmallIcon(R.mipmap.ic_launcher);
                buli.setContentTitle("验证码提示：");
                buli.setContentText("您的验证码为：" + s + "，不要随便给别人看你的验证码，请不要上当");
                buli.setContentIntent(pend);
                buli.setNumber(1);
                buli.setWhen(System.currentTimeMillis());
                Notification notification = buli.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                manager.notify(1, notification);

                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        btn_captcha.setText("还剩" + millisUntilFinished / 1000 + "s");
                        btn_captcha.setEnabled(false);
                    }

                    @Override
                    public void onFinish() {
                        btn_captcha.setText("倒计时完毕了");
                        btn_captcha.setEnabled(true);
                    }
                }.start();

//                if (btn_captcha.getText().toString().equals("获取验证码")){
//                    btn_captcha.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                }else {
//                    btn_captcha.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                }
                break;
            case R.id.btn_find:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String phone = ed_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String captcha = ed_captcha.getText().toString().trim();
        if (TextUtils.isEmpty(captcha)) {
            Toast.makeText(this, "验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pass = ed_pass.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        if (captcha.equals(s)) {
            Toast.makeText(MainActivity.this, "您输入的验证码有误,请重新获取", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "成功找回密码", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));

        }


    }
}

//    /**
//     * 验证码倒计时
//     */
//    class TimeCount extends CountDownTimer {
//        public TimeCount(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
//        }
//
//        @Override
//        public void onTick(long l) {
//            btn_captcha.setClickable(false);
//            btn_captcha.setText("剩余"+1/ 60 + "秒");
//        }
//
//        //计时完毕时触发
//        @Override
//        public void onFinish() {
//            btn_captcha.setText("重新获取");
//            btn_captcha.setClickable(true);
//        }
//    }
//    /**
//     * 获取验证码请求
//     */
//    private void getVerifyMessage() {
//        //验证码获取成功后
//        btn_captcha.setEnabled(false);
//    }
//
//    /**
//     * 开始计时
//     */
//    private void startRockOn() {
//
//        time.start();//开始计时
//    }

