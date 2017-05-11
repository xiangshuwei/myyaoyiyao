package cn.xsw.com.myyaoyiyao.MyAty;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.xsw.com.myyaoyiyao.MyAdapter.BlueBallAdapter;
import cn.xsw.com.myyaoyiyao.MyAdapter.RedBallAdapter;
import cn.xsw.com.myyaoyiyao.MyUtil.Calculator;
import cn.xsw.com.myyaoyiyao.MyUtil.RandomMadeBall;
import cn.xsw.com.myyaoyiyao.R;
import cn.xsw.com.myyaoyiyao.YaoyiYao.ShakeListener;

//当程序里面有很多的部件需要点击执行动作的时候可以使用implements OnClickListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GridView gridViRedBall, gridViBlueBall;
    private RedBallAdapter adpRedBall;
    private BlueBallAdapter adpBlueBall;
    private ArrayList<String> arrRandomRed, arrRandomBlue;

    private Button btnRandomSelect, btnClearNumber, btnPurchaseBall;
    private TextView txtResult, txtShowRedBall, txtShowBlueBall;

    private int selectHongNumber = 6, selectLanNumber = 1;

    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator; //振动器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initial View
        intView();
        //Initial Data
        intData();
    }

    private void intData() {
        // 对红球GridView进行监听，获得红球选中的数。
        gridViRedBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                RedBallAdapter.redGridViewHolder vHolder = (RedBallAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();//改变状态
                adpRedBall.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                for (int i = 0; i < gridViRedBall.getCount(); i++) {
                    RedBallAdapter.redGridViewHolder vHolder_red = (RedBallAdapter.redGridViewHolder) gridViRedBall.getChildAt(i).getTag();
                    if (adpRedBall.hisSelected.get(i)) {
                        ++tempRed;
                        selectHongNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
                    }
                    if (adpRedBall.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                    }
                }
                txtShowRedBall.setText(hq);

                long zhushu = Calculator.calculateBetNum(selectHongNumber, selectLanNumber);
                txtResult.setText("共" + String.valueOf(zhushu) + "注" + (zhushu * 2) + "元");
            }
        });

        // 对篮球GridView进行监听，获取选球状态。
        gridViBlueBall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlueBallAdapter.LanGridViewHolder vHollder = (BlueBallAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                adpBlueBall.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                for (int i = 0; i < gridViBlueBall.getCount(); i++) {
                    BlueBallAdapter.LanGridViewHolder vHolder_Blue = (BlueBallAdapter.LanGridViewHolder) gridViBlueBall.getChildAt(i).getTag();
                    if (BlueBallAdapter.lisSelected.get(i)) {
                        ++tempBlue;
                        selectLanNumber = tempBlue;
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.black));
                    }

                    if (adpBlueBall.getSelected().get(i)) {
                        lq = lq + (i + 1) + "  ";
                    }
                }
                txtShowBlueBall.setText(lq);
                long zhushu = Calculator.calculateBetNum(selectHongNumber, selectLanNumber);
                txtResult.setText("共" + String.valueOf(zhushu) + "注" + (zhushu * 2) + "元");
            }
        });

        // 当手机摇晃时
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {//
                mShakeListener.stop();
                startVibrato(); // 开始震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        randomSelect();
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 1000);
            }
        });
    }
    //猜想：原本有一个中奖界面，但删了，所以要延迟1秒跳转到中奖界面的

    // 定义震动
    public void startVibrato() {
        mVibrator.vibrate(new long[]{200, 400, 0, 400}, -1);
        // 第一个｛｝里面是节奏数组，第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        // 第二个参数是重复次数，-1为不重复，0为一直震动，非-1则从pattern的指定下标开始重复
    }


    @Override
    protected void onStart() {
        super.onStart();
        mShakeListener.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }


    private void intView() {
        // 获得振动器服务
        //getSystemService(VIBRATOR_SERVICE)：获得一个震动的服务
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        // 实例化加速度传感器检测类
        mShakeListener = new ShakeListener(this);

        gridViRedBall = (GridView) findViewById(R.id.gridview_hongqiu);
        gridViBlueBall = (GridView) findViewById(R.id.gridview_lanqiu);

        txtShowRedBall = (TextView) findViewById(R.id.txt_showhongqiu);
        txtShowBlueBall = (TextView) findViewById(R.id.txt_showlanqiu);
        txtResult = (TextView) findViewById(R.id.txt_result);

        btnRandomSelect = (Button) findViewById(R.id.btn_random_select);
        btnClearNumber = (Button) findViewById(R.id.btn_clear_number);
        btnPurchaseBall = (Button) findViewById(R.id.btn_purchase_ball);

        btnRandomSelect.setOnClickListener(this);
        btnClearNumber.setOnClickListener(this);
        btnPurchaseBall.setOnClickListener(this);

        adpRedBall = new RedBallAdapter(this, arrRandomRed);
        adpBlueBall = new BlueBallAdapter(this, arrRandomBlue);

        gridViRedBall.setAdapter(adpRedBall);
        gridViBlueBall.setAdapter(adpBlueBall);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // 清空选号按钮
            case R.id.btn_clear_number:
                adpRedBall.clearData();
                adpBlueBall.clearData();
                txtShowRedBall.setText("");
                txtShowBlueBall.setText("");
                selectHongNumber = 0;
                selectLanNumber = 0;
                txtResult.setText("共0注0元");
                break;
            // 随机选号按钮
            case R.id.btn_random_select:
                randomSelect();
                txtResult.setText("共1注2元");
                break;
            // 投注确定按钮
            case R.id.btn_purchase_ball:
                Toast.makeText(MainActivity.this , "请确认付款::" + txtResult.getText().toString() , Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void randomSelect() {
        getBallNumber();
        adpRedBall.updateData(arrRandomRed);
        adpBlueBall.updateData(arrRandomBlue);
        selectHongNumber = 6;
        selectLanNumber = 1;
        String hq = "";
        for (int i = 0; i < arrRandomRed.size(); i++) {
            hq = hq + (Integer.parseInt(arrRandomRed.get(i)) + 1) + "  ";
        }
        txtShowRedBall.setText(hq);

        String lq = "";
        for (int i = 0; i < arrRandomBlue.size(); i++) {
            lq = lq + (Integer.parseInt(arrRandomBlue.get(i)) + 1) + "  ";
        }
        txtShowBlueBall.setText(lq);

        txtResult.setText("共1注2元");
    }

    private void getBallNumber() {
        arrRandomRed = RandomMadeBall.getRedBall();
        arrRandomBlue = RandomMadeBall.getBlueBall();
    }
}
