
package com.tee686.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.casit.tee686.R;

import java.io.File;
import java.util.ArrayList;

public class Section686Activity extends Activity{
	
	private ViewPager mViewPager;
    private PagerTitleStrip mPagerTitleStrip;
    private ImageView mPageImg;
    private int currIndex_me8 = 0;
    private int currIndex_mea6 = 0;
    private int currIndex_tg6 = 0;
    private int sec = 1;
    private ImageView mSec1,mSec2,mSec3,mSec4,mSec5,mSec6,mSec7,mSec8;
    private ArrayList<View> views;
    private ArrayList<String> titles;
    private Spinner spinner;
    private LayoutInflater inflater;
    private RelativeLayout rel;
            
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_section686);
        
		inflater = LayoutInflater.from(this);
		//View vHeader = inflater.inflate(R.layout.header, null);
		//TextView tx = (TextView)findViewById(R.id.text_header);
		//tx.setText(R.string.hd_mea_aa_lax);
		
		//final LayoutInflater inflater = getLayoutInflater();  
		spinner=(Spinner)findViewById(R.id.spinnermenu);
		String[] spinnerStr = getResources().getStringArray(R.array.spinner_list);
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list, android.R.layout.simple_spinner_item);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerStr);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//将adapter添加到spinner中
		spinner.setAdapter(adapter);
		
		spinner.setSelection(0, true);
		//添加事件Spinner事件监听 
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
		//设置默认值  
		spinner.setVisibility(View.GONE);
		
		rel = (RelativeLayout)findViewById(R.id.relativelayout_choose_section);
		ImageButton btn_6861 = (ImageButton)findViewById(R.id.btn_6861);
		ImageButton btn_6862 = (ImageButton)findViewById(R.id.btn_6862);
		ImageButton btn_6863 = (ImageButton)findViewById(R.id.btn_6863);
		ImageButton btn_6864 = (ImageButton)findViewById(R.id.btn_6864);
		ImageButton backbtn = (ImageButton)findViewById(R.id.image_backbtn);

        //初始化686标准切面界面
		Bundle bundle =getIntent().getExtras();
        if(bundle != null) {
            sec = bundle.getInt("sec");
        }
		switch (sec) {
            case 1:
                forMEA6();
                break;
            case 2:
                forME8();
                break;
            case 3:
                forTG6();
                break;
            case 4:
                forEx();
                break;
        }
		
		btn_6861.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				currIndex_mea6 = 0;
				forMEA6();
				spinner.setSelection(0/*currIndex_mea6*/, true);
			}
        });
		
		btn_6862.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				currIndex_me8 = 0;
				forME8();
				spinner.setSelection(0/*currIndex_me8+6*/, true);
			}
        });
		
		btn_6863.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				currIndex_tg6 = 0;
				forTG6();
				spinner.setSelection(0/*currIndex_tg6+14*/, true);
			}
        });

        btn_6864.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
            	if(isPayed()) {
	                forEx();
	                spinner.setSelection(0, true);
            	} else {
            		payForEx();
            		spinner.setSelection(0, true);
            	}
            }
        });

        backbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Section686Activity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
    }

    protected boolean isPayed() {
    	File file = getFileStreamPath("pay.xml");
		if(file.exists()) {
			return true;
		}
		return false;
	}

	private void forEx() {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sec_me_rvio, null);
        rel.removeAllViews();
        rel.addView(layout);
        TextView tx = (TextView)findViewById(R.id.text_header);
        tx.setText(R.string.hd_me_rvio);

        ImageView view1iv1 = (ImageView)layout.findViewById(R.id.iv1_me_rvio);
        ImageView view1iv2 = (ImageView)layout.findViewById(R.id.iv2_me_rvio);
        ImageView view1iv3 = (ImageView)layout.findViewById(R.id.iv3_me_rvio);
        ImageView view1iv4 = (ImageView)layout.findViewById(R.id.iv4_me_rvio);

        view1iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q1_ME_RvioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q1_zoomin, R.anim.hold);
            }
        });

        view1iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q2_ME_RvioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q2_zoomin, R.anim.hold);
            }
        });

        view1iv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q3_ME_RvioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q3_zoomin, R.anim.hold);
            }
        });

        view1iv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q4_ME_RvioActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q4_zoomin, R.anim.hold);
            }
        });
    }

    private void forMEA6() {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sec_me_lax, null);
        rel.removeAllViews();
        rel.addView(layout);
        TextView tx = (TextView)findViewById(R.id.text_header);
        tx.setText(R.string.hd_me_lax);

        ImageView view1iv1 = (ImageView)layout.findViewById(R.id.iv1_me_lax);
        ImageView view1iv2 = (ImageView)layout.findViewById(R.id.iv2_me_lax);
        ImageView view1iv3 = (ImageView)layout.findViewById(R.id.iv3_me_lax);
        ImageView view1iv4 = (ImageView)layout.findViewById(R.id.iv4_me_lax);


        view1iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q1_ME_LaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q1_zoomin, R.anim.hold);
            }
        });

        view1iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q2_ME_LaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q2_zoomin, R.anim.hold);
            }
        });

        view1iv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q3_ME_LaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q3_zoomin, R.anim.hold);
            }
        });

        view1iv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q4_ME_LaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q4_zoomin, R.anim.hold);
            }
        });
    }

    public void forME8() {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sec_me_fc, null);
        rel.removeAllViews();
        rel.addView(layout);
        TextView tx = (TextView)Section686Activity.this.findViewById(R.id.text_header);
        tx.setText(R.string.hd_me_fc);

        ImageView view1iv1 = (ImageView)layout.findViewById(R.id.iv1_me_fc);
        ImageView view1iv2 = (ImageView)layout.findViewById(R.id.iv2_me_fc);
        ImageView view1iv3 = (ImageView)layout.findViewById(R.id.iv3_me_fc);
        ImageView view1iv4 = (ImageView)layout.findViewById(R.id.iv4_me_fc);


        view1iv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q3_ME_FcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q1_zoomin, R.anim.hold);
            }
        });

        view1iv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q4_ME_FcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q2_zoomin, R.anim.hold);
            }
        });

        view1iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q1_ME_FcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q3_zoomin, R.anim.hold);
            }
        });

        view1iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q2_ME_FcActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q4_zoomin, R.anim.hold);
            }
        });
    }

    private void forTG6() {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sec_tg_m_sax, null);
        rel.removeAllViews();
        rel.addView(layout);
        TextView tx = (TextView)findViewById(R.id.text_header);
        tx.setText(R.string.hd_tg_m_sax);

        ImageView view1iv1 = (ImageView)layout.findViewById(R.id.iv1_tg_m_sax);
        ImageView view1iv2 = (ImageView)layout.findViewById(R.id.iv2_tg_m_sax);
        ImageView view1iv3 = (ImageView)layout.findViewById(R.id.iv3_tg_m_sax);
        ImageView view1iv4 = (ImageView)layout.findViewById(R.id.iv4_tg_m_sax);


        view1iv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q3_TG_M_SaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q1_zoomin, R.anim.hold);
            }
        });

        view1iv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q4_TG_M_SaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q2_zoomin, R.anim.hold);
            }
        });

        view1iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q1_TG_M_SaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q3_zoomin, R.anim.hold);
            }
        });

        view1iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Section686Activity.this, Q2_TG_M_SaxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.q4_zoomin, R.anim.hold);
            }
        });
    }

    @Override
	public void onResume()
	{	
		spinner.setSelection(0,true);
	    currIndex_me8 = 0;//当前页面   
	    currIndex_mea6 = 0;
	    currIndex_tg6 = 0;
		super.onResume();
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            /*Intent intent = new Intent(this, Section686Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            startActivity(intent);*/
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	private void payForEx() {
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.blank, null);
		rel.removeAllViews();
		rel.addView(layout);
		TextView tx = (TextView)findViewById(R.id.text_header);
		tx.setText(R.string.hd_me_rvio);
	}

	//食道中段大血管水平四象限初始化
	/*private void forMEA6() {
		*//*ImageButton ib = (ImageButton)findViewById(R.id.btn_6861);
		ib.setBackgroundResource(0);*//*
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.page_mea6, null);
		rel.removeAllViews();
		rel.addView(layout);
		mViewPager = (ViewPager)layout.findViewById(R.id.viewpager_mea6);
        mPagerTitleStrip = (PagerTitleStrip)layout.findViewById(R.id.pagertitle_mea6);

        TextView tx = (TextView)findViewById(R.id.text_header);
      	tx.setText(R.string.hd_me_lax);

        //小圆点
        mSec1 = (ImageView)layout.findViewById(R.id.sec1_mea6);
        mSec2 = (ImageView)layout.findViewById(R.id.sec2_mea6);
        mSec3 = (ImageView)layout.findViewById(R.id.sec3_mea6);
        mSec4 = (ImageView)layout.findViewById(R.id.sec4_mea6);
        mSec5 = (ImageView)layout.findViewById(R.id.sec5_mea6);
        mSec6 = (ImageView)layout.findViewById(R.id.sec6_mea6);

        //将要分页显示的View装入数组中
        LayoutInflater mLi = LayoutInflater.from(getApplicationContext());
        View view1 = mLi.inflate(R.layout.sec_me_lax, null);
        View view2 = mLi.inflate(R.layout.sec_mea_aa_sax, null);
        View view3 = mLi.inflate(R.layout.sec_mea_da_lax, null);
        View view4 = mLi.inflate(R.layout.sec_mea_da_sax, null);
        View view5 = mLi.inflate(R.layout.sec_mea_uaa_lax, null);
        View view6 = mLi.inflate(R.layout.sec_mea_uaa_sax, null);

        //获得第一幅图四个象限的imageview的id
        ImageView view1iv1 = (ImageView)view1.findViewById(R.id.iv1_mea_aa_lax);
        ImageView view1iv2 = (ImageView)view1.findViewById(R.id.iv2_mea_aa_lax);
        ImageView view1iv3 = (ImageView)view1.findViewById(R.id.iv3_mea_aa_lax);
        ImageView view1iv4 = (ImageView)view1.findViewById(R.id.iv4_mea_aa_lax);


        //每个页面的view数据
        views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);

        view1iv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q1_ME_LaxActivity.class);
		        startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
        });

        view1iv2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q2_ME_LaxActivity.class);
		        startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
        });

        view1iv3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q3_ME_LaxActivity.class);
		        startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
        });

        view1iv4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q4_ME_LaxActivity.class);
		        startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			}
        });

        //每一个页面的标题
        titles = new ArrayList<String>();
        titles.add("①");
        titles.add("②");
        titles.add("③");
        titles.add("④");
        titles.add("⑤");
        titles.add("⑥");



      //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }
        };

        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener_mea6());
        mViewPager.setAdapter(mPagerAdapter);//与ListView用法相同，设置重写的Adapter。这样就实现了ViewPager的滑动效果。
        mViewPager.setCurrentItem(0);
	}*/
	
	//食道中段心房心室水平四象限初始化
	/*private void forME8() {
		LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.page_me8, null);
		rel.removeAllViews();
		rel.addView(layout);
		
		TextView tx = (TextView)findViewById(R.id.text_header);
      	tx.setText(R.string.hd_me_fc);
      	
		mViewPager = (ViewPager)layout.findViewById(R.id.viewpager_me8);            
        mPagerTitleStrip = (PagerTitleStrip)layout.findViewById(R.id.pagertitle_me8);   
          
        mSec1 = (ImageView)layout.findViewById(R.id.sec1_me8);  
        mSec2 = (ImageView)layout.findViewById(R.id.sec2_me8);  
        mSec3 = (ImageView)layout.findViewById(R.id.sec3_me8);  
        mSec4 = (ImageView)layout.findViewById(R.id.sec4_me8);  
        mSec5 = (ImageView)layout.findViewById(R.id.sec5_me8);  
        mSec6 = (ImageView)layout.findViewById(R.id.sec6_me8);  
        mSec7 = (ImageView)layout.findViewById(R.id.sec7_me8);  
        mSec8 = (ImageView)layout.findViewById(R.id.sec8_me8);  
                 
        //将要分页显示的View装入数组中   
        LayoutInflater mLi = LayoutInflater.from(getApplicationContext());  
        View view1 = mLi.inflate(R.layout.sec_me_fc, null);  
        View view2 = mLi.inflate(R.layout.sec_me_mc, null);  
        View view3 = mLi.inflate(R.layout.sec_me_tc, null);  
        View view4 = mLi.inflate(R.layout.sec_me_lax, null);  
        View view5 = mLi.inflate(R.layout.sec_me_av_lax, null);  
        View view6 = mLi.inflate(R.layout.sec_me_av_sax, null);  
        View view7 = mLi.inflate(R.layout.sec_me_rvio, null);  
        View view8 = mLi.inflate(R.layout.sec_me_b, null);  
        
      //获得第一幅图四个象限的imageview的id
        ImageView view1iv1 = (ImageView)view1.findViewById(R.id.iv1_mea_aa_lax);
        ImageView view1iv2 = (ImageView)view1.findViewById(R.id.iv2_mea_aa_lax);
        ImageView view1iv3 = (ImageView)view1.findViewById(R.id.iv3_mea_aa_lax);
        ImageView view1iv4 = (ImageView)view1.findViewById(R.id.iv4_mea_aa_lax);
        
        //每个页面的view数据   
        views = new ArrayList<View>();  
        views.add(view1);  
        views.add(view2);  
        views.add(view3);  
        views.add(view4);  
        views.add(view5);  
        views.add(view6);  
        views.add(view7);  
        views.add(view8);  

        view1iv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q1_ME_LaxActivity.class);
		        startActivity(intent); 				
			}
        });
        
        view1iv2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q2_ME_LaxActivity.class);
		        startActivity(intent); 				
			}
        });
        
        view1iv3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q3_ME_LaxActivity.class);
		        startActivity(intent); 				
			}
        });
        
        view1iv4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Section686Activity.this, Q4_ME_LaxActivity.class);
		        startActivity(intent); 				
			}
        });
        
        //每一个页面的标题   
        titles = new ArrayList<String>();  
        titles.add("①");  
        titles.add("②");  
        titles.add("③");  
        titles.add("④");  
        titles.add("⑤");  
        titles.add("⑥");  
        titles.add("⑦");  
        titles.add("⑧");  
        
      //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {  
              
            @Override  
            public boolean isViewFromObject(View arg0, Object arg1) {  
                return arg0 == arg1;  
            }  
              
            @Override  
            public int getCount() {  
                return views.size();  
            }
  
            @Override  
            public void destroyItem(View container, int position, Object object) {  
                ((ViewPager)container).removeView(views.get(position));  
            }  
              
            @Override  
            public CharSequence getPageTitle(int position) {  
                return titles.get(position);
            }  
              
            @Override  
            public Object instantiateItem(View container, int position) {  
                ((ViewPager)container).addView(views.get(position));  
                return views.get(position);  
            }  
        }; 
        
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener_me8());  
        mViewPager.setAdapter(mPagerAdapter);//与ListView用法相同，设置重写的Adapter。这样就实现了ViewPager的滑动效果。   
        mViewPager.setCurrentItem(0);		
	}*/
	
	//胃底水平四象限初始化
	/*private void forTG6() {
		LinearLayout layout = (LinearLayout) inflater.inflate(
                R.layout.page_tg6, null);
		rel.removeAllViews();
		rel.addView(layout);
		
		TextView tx = (TextView)findViewById(R.id.text_header);
      	tx.setText(R.string.hd_tg_m_sax);
      	
		mViewPager = (ViewPager)layout.findViewById(R.id.viewpager_tg6);            
        mPagerTitleStrip = (PagerTitleStrip)layout.findViewById(R.id.pagertitle_tg6);   
          
        mSec1 = (ImageView)layout.findViewById(R.id.sec1_tg6);  
        mSec2 = (ImageView)layout.findViewById(R.id.sec2_tg6);  
        mSec3 = (ImageView)layout.findViewById(R.id.sec3_tg6);  
        mSec4 = (ImageView)layout.findViewById(R.id.sec4_tg6);  
        mSec5 = (ImageView)layout.findViewById(R.id.sec5_tg6);  
        mSec6 = (ImageView)layout.findViewById(R.id.sec6_tg6);   
                 
        //将要分页显示的View装入数组中   
        LayoutInflater mLi = LayoutInflater.from(getApplicationContext());  
        View view1 = mLi.inflate(R.layout.sec_tg_b_sax, null);  
        View view2 = mLi.inflate(R.layout.sec_tg_d_lax, null);  
        View view3 = mLi.inflate(R.layout.sec_tg_lax, null);  
        View view4 = mLi.inflate(R.layout.sec_tg_m_sax, null);  
        View view5 = mLi.inflate(R.layout.sec_tg_rvi, null);  
        View view6 = mLi.inflate(R.layout.sec_tg_tc, null);    
                  
        //每个页面的view数据   
        views = new ArrayList<View>();  
        views.add(view1);  
        views.add(view2);  
        views.add(view3);  
        views.add(view4);  
        views.add(view5);  
        views.add(view6);    

        //每一个页面的标题   
        titles = new ArrayList<String>();  
        titles.add("①");  
        titles.add("②");  
        titles.add("③");  
        titles.add("④");  
        titles.add("⑤");  
        titles.add("⑥");  
        
      //填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {  
              
            @Override  
            public boolean isViewFromObject(View arg0, Object arg1) {  
                return arg0 == arg1;  
            }  
              
            @Override  
            public int getCount() {  
                return views.size();  
            }
  
            @Override  
            public void destroyItem(View container, int position, Object object) {  
                ((ViewPager)container).removeView(views.get(position));  
            }  
              
            @Override  
            public CharSequence getPageTitle(int position) {  
                return titles.get(position);
            }  
              
            @Override  
            public Object instantiateItem(View container, int position) {  
                ((ViewPager)container).addView(views.get(position));  
                return views.get(position);  
            }  
        }; 
        
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener_tg6());  
        mViewPager.setAdapter(mPagerAdapter);//与ListView用法相同，设置重写的Adapter。这样就实现了ViewPager的滑动效果。   
        mViewPager.setCurrentItem(0);		
	}*/
	
	//下拉菜单
	public class SpinnerSelectedListener implements OnItemSelectedListener {
		
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent;
			switch(arg2){
				case 1:
					/*Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					//intent = new Intent(Section686Activity.this, Section686Activity.class); 
			        //startActivity(intent);*/
					forMEA6();
					mViewPager.setCurrentItem(0);
					currIndex_mea6 = 0;
					break;
				case 2:
					/*Toast.makeText(arg0.getContext(),"You choose " 
				+ arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();
					intent = new Intent(Section686Activity.this, Section6641Activity.class); 
			        startActivity(intent);*/
			        forMEA6();
					mViewPager.setCurrentItem(1);
					currIndex_mea6 = 1;
					break;
				case 3:
					forMEA6();					
					mViewPager.setCurrentItem(2);
					currIndex_mea6 = 2;
					break;
				case 4:
					forMEA6();					
					mViewPager.setCurrentItem(3);
					currIndex_mea6 = 3;
					break;
				case 5:
					forMEA6();					
					mViewPager.setCurrentItem(4);
					currIndex_mea6 = 4;
					break;
				case 6:
					forMEA6();
					mViewPager.setCurrentItem(5);
					currIndex_mea6 = 5;
					break;
				case 7:
					forME8();				
					mViewPager.setCurrentItem(0);
					currIndex_me8 = 0;
					break;
				case 8:
					forME8();					
					mViewPager.setCurrentItem(1);
					currIndex_me8 = 1;
					break;
				case 9:
					forME8();					
					mViewPager.setCurrentItem(2);
					currIndex_me8 = 2;
					break;
				case 10:
					forME8();
					mViewPager.setCurrentItem(3);
					currIndex_me8 = 3;
					break;
				case 11:
					forME8();
					mViewPager.setCurrentItem(4);
					currIndex_me8 = 4;
					break;
				case 12:
					forME8();
					mViewPager.setCurrentItem(5);
					currIndex_me8 = 5;
					break;
				case 13:
					forME8();
					mViewPager.setCurrentItem(6);
					currIndex_me8 = 6;
					break;
				case 14:
					forME8();
					mViewPager.setCurrentItem(7);
					currIndex_me8 = 7;
					break;
				case 15:
					forTG6();
					mViewPager.setCurrentItem(0);
					currIndex_tg6 = 0;
					break;
				case 16:
					forTG6();
					mViewPager.setCurrentItem(1);
					currIndex_tg6 = 1;
					break;
				case 17:
					forTG6();
					mViewPager.setCurrentItem(2);
					currIndex_tg6 = 2;
					break;
				case 18:
					forTG6();
					mViewPager.setCurrentItem(3);
					currIndex_tg6 = 3;
					break;
				case 19:
					forTG6();
					mViewPager.setCurrentItem(4);
					currIndex_tg6 = 4;
					break;
				case 20:
					forTG6();
					mViewPager.setCurrentItem(5);
					currIndex_tg6 = 5;
					break;
				default:
					break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

	}
      
    //食道中段大血管水平界面滑动
    /*public class MyOnPageChangeListener_mea6 implements OnPageChangeListener {
    	  
        public void onPageSelected(int arg0) {//参数arg0为选中的View   
  
        	int mea6_flag=0;
            Animation animation = null;//声明动画对象   
            switch (arg0) {  
            case 0: //页面一            
            	TextView tv_mea6_1 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_1.setText(R.string.hd_me_lax);
                mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                if (currIndex_mea6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);//圆点移动效果动画，从当前View移动到下一个View   
                } 
                else
                {
                	mea6_flag=1;
                }
                break;  
            case 1: //页面二   
            	TextView tv_mea6_2 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_2.setText(R.string.hd_mea_aa_sax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_mea6 == arg0-1) {//如果滑动到上一个View   
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0); //圆点移动效果动画，从当前View移动到下一个View   
  
                      
                } else if (currIndex_mea6 == arg0+1) {//圆点移动效果动画，从当前View移动到下一个View，下同。   
  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	mea6_flag=1;
                }
                break;  
            case 2: //页面三   
            	TextView tv_mea6_3 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_3.setText(R.string.hd_mea_uaa_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_mea6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_mea6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	mea6_flag=1;
                }
                break;  
            case 3:  //页面四
            	TextView tv_mea6_4 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_4.setText(R.string.hd_mea_uaa_sax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_mea6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                      
                } else if (currIndex_mea6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	mea6_flag=1;
                }
                break;  
            case 4:  //页面五
            	TextView tv_mea6_5 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_5.setText(R.string.hd_mea_da_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));   
                if (currIndex_mea6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_mea6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	mea6_flag=1;
                }
                break;  
            case 5:  //页面六
            	TextView tv_mea6_6 = (TextView)findViewById(R.id.text_header);
            	tv_mea6_6.setText(R.string.hd_mea_da_sax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));    
                if (currIndex_mea6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_mea6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	mea6_flag=1;
                }
                break;
            } 
            if(mea6_flag!=1)
            {
            	animation.setFillAfter(true);// True:设置图片停在动画结束位置   
            	animation.setDuration(200);//设置动画持续时间
            }
            currIndex_mea6 = arg0;//设置当前View   
            if(currIndex_mea6!=spinner.getSelectedItemPosition())
            {
            	spinner.setSelection(0*//*currIndex_mea6*//*, true);
            }
        }

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}    
	}*/

	//食道中段心房心室水平界面滑动
    /*public class MyOnPageChangeListener_me8 implements OnPageChangeListener {
  
        public void onPageSelected(int arg0) {//参数arg0为选中的View   
  
            Animation animation = null;//声明动画对象   
            int me8_flag=0;
            switch (arg0) {  
            case 0: //页面一              
            	TextView tv_me8_1 = (TextView)findViewById(R.id.text_header);
            	tv_me8_1.setText(R.string.hd_me_fc);
                mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);//圆点移动效果动画，从当前View移动到下一个View   
                }
                else
                {
                	me8_flag=1;
                }
                break;  
            case 1: //页面二   
            	TextView tv_me8_2 = (TextView)findViewById(R.id.text_header);
            	tv_me8_2.setText(R.string.hd_me_mc);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));   
                if (currIndex_me8 == arg0-1) {//如果滑动到上一个View   
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0); //圆点移动效果动画，从当前View移动到下一个View   
  
                      
                } else if (currIndex_me8 == arg0+1) {//圆点移动效果动画，从当前View移动到下一个View，下同。   
  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	me8_flag=1;
                }
                break;  
            case 2: //页面三   
            	TextView tv_me8_3 = (TextView)findViewById(R.id.text_header);
            	tv_me8_3.setText(R.string.hd_me_tc);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	me8_flag=1;
                }
                break;  
            case 3:  //页面四
            	TextView tv_me8_4 = (TextView)findViewById(R.id.text_header);
            	tv_me8_4.setText(R.string.hd_me_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                      
                } else if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	me8_flag=1;
                }
                break;  
            case 4:  //页面五
            	TextView tv_me8_5 = (TextView)findViewById(R.id.text_header);
            	tv_me8_5.setText(R.string.hd_me_av_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	me8_flag=1;
                }
                break;  
            case 5:  //页面六
            	TextView tv_me8_6 = (TextView)findViewById(R.id.text_header);
            	tv_me8_6.setText(R.string.hd_me_av_sax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	me8_flag=1;
                }
                break;  
            case 6:  //页面七
            	TextView tv_me8_7 = (TextView)findViewById(R.id.text_header);
            	tv_me8_7.setText(R.string.hd_me_rvio);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_me8 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	me8_flag=1;
                }
                break;  
            case 7:  //页面八
            	TextView tv_me8_8 = (TextView)findViewById(R.id.text_header);
            	tv_me8_8.setText(R.string.hd_me_b);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec7.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec8.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));  
                if (currIndex_me8 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                }
                else
                {
                	me8_flag=1;
                }
                break;  
            } 
            if(me8_flag!=1)
            {            	
            	animation.setFillAfter(true);// True:设置图片停在动画结束位置   
            	animation.setDuration(200);//设置动画持续时间
            }
            currIndex_me8 = arg0;//设置当前View     
            if(currIndex_me8!=spinner.getSelectedItemPosition()-6)
            {
            	spinner.setSelection(0*//*currIndex_me8+6*//*, true);
            }
        }

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}    
	}*/
    
    //胃底水平界面滑动
    /*public class MyOnPageChangeListener_tg6 implements OnPageChangeListener {
  	  
        public void onPageSelected(int arg0) {//参数arg0为选中的View   
  
            Animation animation = null;//声明动画对象   
            int tg6_flag=0;
            switch (arg0) {  
            case 0: //页面一              
            	TextView tv_tg6_1 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_1.setText(R.string.hd_tg_m_sax);
                mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                if (currIndex_tg6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);//圆点移动效果动画，从当前View移动到下一个View   
                } 
                else
                {
                	tg6_flag=1;
                }
                break;  
            case 1: //页面二   
            	TextView tv_tg6_2 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_2.setText(R.string.hd_tg_b_sax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));   
                if (currIndex_tg6 == arg0-1) {//如果滑动到上一个View   
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0); //圆点移动效果动画，从当前View移动到下一个View   
  
                      
                } else if (currIndex_tg6 == arg0+1) {//圆点移动效果动画，从当前View移动到下一个View，下同。   
  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	tg6_flag=1;
                }
                break;  
            case 2: //页面三   
            	TextView tv_tg6_3 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_3.setText(R.string.hd_tg_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_tg6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_tg6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	tg6_flag=1;
                }
                break;  
            case 3:  //页面四
            	TextView tv_tg6_4 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_4.setText(R.string.hd_tg_tc);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));  
                if (currIndex_tg6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                      
                } else if (currIndex_tg6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                } 
                else
                {
                	tg6_flag=1;
                }
                break;  
            case 4:  //页面五
            	TextView tv_tg6_5 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_5.setText(R.string.hd_tg_rvi);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                if (currIndex_tg6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_tg6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	tg6_flag=1;
                }
                break;  
            case 5:  //页面六
            	TextView tv_tg6_6 = (TextView)findViewById(R.id.text_header);
            	tv_tg6_6.setText(R.string.hd_tg_d_lax);
            	mSec1.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));//进入第一个导航页面，小圆点为选中状态，下一个页面的小圆点是未选中状态。   
                mSec2.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator)); 
                mSec3.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec4.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec5.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
                mSec6.setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));    
                if (currIndex_tg6 == arg0-1) {  
                    animation = new TranslateAnimation(arg0-1, arg0, 0, 0);  
                } else if (currIndex_tg6 == arg0+1) {  
                    animation = new TranslateAnimation(arg0+1, arg0, 0, 0);  
                }  
                else
                {
                	tg6_flag=1;
                }
                break;
            }  
            if(tg6_flag!=1)
            {          	
            	animation.setFillAfter(true);// True:设置图片停在动画结束位置   
            	animation.setDuration(200);//设置动画持续时间
            }
            currIndex_tg6 = arg0;//设置当前View     
            if(currIndex_tg6!=spinner.getSelectedItemPosition()-14)
            {
            	spinner.setSelection(0*//*currIndex_tg6+14*//*, true);
            }
        }

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}    
	}*/
}
