package com.tee686.activity;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import com.alipay.android.appDemo4.AlixId;
import com.alipay.android.appDemo4.BaseHelper;
import com.alipay.android.appDemo4.MobileSecurePayHelper;
import com.alipay.android.appDemo4.MobileSecurePayer;
import com.alipay.android.appDemo4.PartnerConfig;
import com.alipay.android.appDemo4.ResultChecker;
import com.alipay.android.appDemo4.Rsa;
import com.casit.tee686.R;

public class ME_RvioActivity extends Activity{

	protected static final String TAG = "ME_Rvio";
	private VideoView vv;
    private ImageButton ib;
	private ProgressDialog mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.q1_me_rvio);
        
		performPay();
        
		MediaController mc = new MediaController(this);  
		vv = (VideoView)findViewById(R.id.vv_q1_me_rvio);

		vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q1_me_rvio));

		vv.setMediaController(mc);
		vv.setOnCompletionListener(onCompListener);
		vv.start();

        ib = (ImageButton)findViewById(R.id.me_rvio_btn);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ME_RvioActivity.this, Section686Activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sec", 4);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
	}

	private void performPay() {
		// check to see if the MobileSecurePay is already installed.
        // 检测安全支付服务是否被安装
        MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
        mspHelper.detectMobile_sp();
        
        // start pay for this order.
 		// 根据订单信息开始进行支付
 		try {
 			// prepare the order info.
 			// 准备订单信息
 			String orderInfo = getOrderInfo();
 			// 这里根据签名方式对订单信息进行签名
 			String signType = getSignType();
 			String strsign = sign(signType, orderInfo);
 			Log.v("sign:", strsign);
 			// 对签名进行编码
 			strsign = URLEncoder.encode(strsign, "UTF-8");
 			// 组装好参数
 			String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
 					+ getSignType();
 			Log.v("orderInfo:", info);
 			// start the pay.
 			// 调用pay方法进行支付
 			MobileSecurePayer msp = new MobileSecurePayer();
 			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);

 			if (bRet) {
 				// show the progress bar to indicate that we have started
 				// paying.
 				// 显示“正在支付”进度条
 				closeProgress();
 				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,
 						true);
 			} else
 				;
 		} catch (Exception ex) {
 			Toast.makeText(this, R.string.remote_call_failed,
 					Toast.LENGTH_SHORT).show();
 		}
	}
	
	private String sign(String signType, String orderInfo) {
		// TODO Auto-generated method stub
		return Rsa.sign(orderInfo, PartnerConfig.RSA_PRIVATE);
	}

	private String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}

	/**
	 * 关闭进度框
	 */
	private void closeProgress() {
		// TODO Auto-generated method stub
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**获取商品订单信息
	 * @return
	 */
	private String getOrderInfo() {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + getOutTradeNo() + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + this.getClass().getSimpleName()
				+ "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + "食道中段右心室流入流出切面" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\""
				+ "10" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\""
				+ "http://notify.java.jpxx.org/index.jsp" + "\"";

		return strOrderInfo;
	}

	/**获取外部订单号
	 * @return
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String strKey = format.format(date);

		Random r = new Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}
	
	// the handler use to receive the pay result.
	// 这里接收支付结果，支付宝手机端同步通知
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String ret = (String) msg.obj;

				Log.e(TAG, ret); // strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
				switch (msg.what) {
				case AlixId.RQF_PAY: {
					//
					closeProgress();

					BaseHelper.log(TAG, ret);

					// 处理交易结果
					try {
						// 获取交易状态码，具体状态代码请参看文档
						String tradeStatus = "resultStatus={";
						int imemoStart = ret.indexOf("resultStatus=");
						imemoStart += tradeStatus.length();
						int imemoEnd = ret.indexOf("};memo=");
						tradeStatus = ret.substring(imemoStart, imemoEnd);

						// 先验签通知
						ResultChecker resultChecker = new ResultChecker(ret);
						int retVal = resultChecker.checkSign();
						// 验签失败
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
							BaseHelper.showDialog(
									ME_RvioActivity.this,
									"提示",
									getResources().getString(
											R.string.check_sign_failed),
									android.R.drawable.ic_dialog_alert);
						} else {// 验签成功。验签成功后再判断交易状态码
							if (tradeStatus.equals("9000"))// 判断交易状态码，只有9000表示交易成功
								BaseHelper.showDialog(ME_RvioActivity.this, "提示",
										"支付成功。交易状态码：" + tradeStatus,
										R.drawable.infoicon);
							else
								BaseHelper.showDialog(ME_RvioActivity.this, "提示",
										"支付失败。交易状态码:" + tradeStatus,
										R.drawable.infoicon);
						}

					} catch (Exception e) {
						e.printStackTrace();
						BaseHelper.showDialog(ME_RvioActivity.this, "提示", ret,
								R.drawable.infoicon);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	OnCompletionListener onCompListener = new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q1_me_rvio));

			vv.start();
		}		
	};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            /*Intent intent = new Intent(this, Section686Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            startActivity(intent);*/
            finish();
            overridePendingTransition(R.anim.hold, R.anim.me_rvio_zoomout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*private String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                            .equals(android.os.Environment.MEDIA_MOUNTED);   
        if(sdCardExist){
              //��ȡ��Ŀ¼ 
          sdDir = Environment.getExternalStorageDirectory();
        }   
        return sdDir.toString(); 
    } */
    protected void onResume() {
        super.onResume();
        vv = (VideoView)findViewById(R.id.vv_q1_me_rvio);
        vv.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.q1_me_rvio));
        vv.setOnCompletionListener(onCompListener);
        vv.start();
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();

		Log.v(TAG, "onDestroy");

		try {
			mProgress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
