package lly.h5.android.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import lly.h5.android.test.hybrid.plugin.base.ResultCode;

/**
 * Created by leon on 16/5/12.
 */
public class PaymentActivity extends FragmentActivity implements View.OnClickListener {

    static Bundle thisBundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);

        thisBundle = this.getIntent().getExtras();

        findViewById(R.id.btnPay).setOnClickListener(this);
        findViewById(R.id.btnBack).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPay:
                if(thisBundle.getString("channel").equals("")){
                    Intent result = new Intent();
                    result.putExtra("errmsg", "支付渠道不能为空");

                    setResult(ResultCode.ERROR, result);
                    this.finish();
                }else{
                    setResult(ResultCode.OK);
                    this.finish();
                }
                break;
            case R.id.btnBack:
                setResult(ResultCode.CANCEL);
                PaymentActivity.this.finish();
                break;
        }
    }

}
