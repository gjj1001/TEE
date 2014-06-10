package com.tee686.adapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.casit.tee686.R;
<<<<<<< HEAD
import com.tee686.activity.RecordVedioActivity;
import com.tee686.config.ContentFlag;
import com.tee686.entity.Message;
import com.tee686.https.NetWorkHelper;
=======
import com.tee686.config.ContentFlag;
import com.tee686.entity.Message;
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import com.tee686.service.base.RecordPlayService;
import com.tee686.utils.ExpressionUtil;
import com.tee686.utils.ImageUtil;
import com.tee686.utils.ImageUtil.ImageCallback;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
=======
import android.content.Context;
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.MessageQueue.IdleHandler;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
/**
 * 聊天页面ListView内容适配器
 */
public class ChatMsgViewAdapter extends BaseAdapter{
	private Context context;
    private LayoutInflater mInflater;
    public List<Message> msgList;
    private ListView lv;
    private String uname;
    private Timer timer = new Timer();
    RecordPlayService playService = new RecordPlayService();
    public static String currMsgId="";		//当前正在播放语音的ID
    public ChatMsgViewAdapter(Context context, String uname, List<Message> msgList, ListView lv) {
        this.context = context;
        this.msgList = msgList;
        this.uname = uname;
        this.lv = lv;
        mInflater = LayoutInflater.from(this.context);
    }

    public int getCount() {
        return msgList.size();
    }

    public Object getItem(int position) {
        return msgList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
	public int getItemViewType(int position) {
		// 区别两种view的类型，标注两个不同的变量来分别表示各自的类型
		Message msg = msgList.get(position);
		if (msg.getSend_person().equals(uname)) {
			return 0;
		} else {
			return 1;
		}
	}
    
	public int getViewTypeCount() {
		// 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2
		return 2;
	}
	
    public View getView(final int position, View convertView, ViewGroup parent) {
    	final Message msg = msgList.get(position);
    	ViewHolder viewHolder = null;
	    if (convertView == null)
	    {	
	    	  if (msg.getSend_person().equals(uname))
			  {	
				  convertView = mInflater.inflate(R.layout.chat_item_right, null);
			  }else{
				  convertView = mInflater.inflate(R.layout.chat_item_left, null);
			  }
	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.msgBgView = (View) convertView.findViewById(R.id.chat_msg_bg);
			  viewHolder.ivUserImage = (ImageView) convertView.findViewById(R.id.iv_userhead);
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	    viewHolder.tvSendTime.setText(msg.getSend_date());
	    viewHolder.tvUserName.setText(msg.getSend_person());
	    SpannableString spannableString = ExpressionUtil.getExpressionString(context, msg.getSend_ctn()); 
	    TextView tvContent = (TextView) viewHolder.msgBgView.findViewById(R.id.tv_chatcontent);
	    final ImageView ivPlay = (ImageView) viewHolder.msgBgView.findViewById(R.id.iv_play_voice);
<<<<<<< HEAD
	    ImageView  ivVedio = (ImageView) viewHolder.msgBgView.findViewById(R.id.iv_vedioplay);
	    tvContent.setText(spannableString);
	    if(msg.isIfyuyin()){
	    	ivPlay.setVisibility(View.VISIBLE);
	    	ivVedio.setVisibility(View.GONE);
=======
	    tvContent.setText(spannableString);
	    if(msg.isIfyuyin()){
	    	ivPlay.setVisibility(View.VISIBLE);
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
		    //处理语音消息的单击事件
		    viewHolder.msgBgView.setOnClickListener(new View.OnClickListener() {
				@SuppressWarnings("static-access")
				public void onClick(View v) {
<<<<<<< HEAD
					try {
						if(NetWorkHelper.isMobileDataEnable(context) || NetWorkHelper.isWifiDataEnable(context)) {
							final String path = msg.getRecord_path();
							if(null!= path && !"".equals(path)){
								try {
									if(currMsgId.equals(msg.getSend_person())){
										Log.i(ContentFlag.TAG, "playService.ifThreadRun:"+playService.ifThreadRun());
									}
									if(currMsgId.equals(msg.getSend_person()) && playService.ifThreadRun()) {
										playService.stop();
										return;
									}
									//根据类型选择左右不同的动画
									final int type = getItemViewType(position);
									if(type == 0){
										ivPlay.setBackgroundResource(R.drawable.chatto_voice_play_frame);
									}else{
										ivPlay.setBackgroundResource(R.drawable.chatfrom_voice_play_frame);
									}
									final AnimationDrawable animation = (AnimationDrawable) ivPlay.getBackground();
									//播放动画
									final long recordTime = msg.getRecordTime();
									currMsgId = msg.getSend_person();
									playService.play(path, animation, ivPlay, type);
									context.getMainLooper().myQueue().addIdleHandler(new IdleHandler() {
										public boolean queueIdle() {
											timer.schedule(new RecordTimeTask(animation, ivPlay, type), recordTime);
											return false;
										}
									});
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {
								Toast.makeText(context, "网络不可用，请稍后再试", Toast.LENGTH_SHORT).show();
							}
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
	    }else if(msg.isIfvedio()){
	    	ivPlay.setVisibility(View.GONE);
	    	ivVedio.setVisibility(View.VISIBLE);
		    //处理视频消息的单击事件
		    viewHolder.msgBgView.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					try {
						if(NetWorkHelper.isMobileDataEnable(context) || NetWorkHelper.isWifiDataEnable(context)) {
							final String path = msg.getRecord_path();
							if(null!= path && !"".equals(path)){
								Intent intent = new Intent(context, RecordVedioActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
								intent.putExtra("path", path);
								context.startActivity(intent);
								((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.hold);
							}
						} else {
							Toast.makeText(context, "网络不可用，请稍后再试", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
=======
					final String path = msg.getRecord_path();
					if(null!= path && !"".equals(path)){
						try {
							if(currMsgId.equals(msg.getSend_person())){
								Log.i(ContentFlag.TAG, "playService.ifThreadRun:"+playService.ifThreadRun());
							}
							if(currMsgId.equals(msg.getSend_person()) && playService.ifThreadRun()) {
								playService.stop();
								return;
							}
							//根据类型选择左右不同的动画
							final int type = getItemViewType(position);
							if(type == 0){
								ivPlay.setBackgroundResource(R.drawable.chatto_voice_play_frame);
							}else{
								ivPlay.setBackgroundResource(R.drawable.chatfrom_voice_play_frame);
							}
							final AnimationDrawable animation = (AnimationDrawable) ivPlay.getBackground();
							//播放动画
							final long recordTime = msg.getRecordTime();
							currMsgId = msg.getSend_person();
							playService.play(path, animation, ivPlay, type);
							context.getMainLooper().myQueue().addIdleHandler(new IdleHandler() {
								public boolean queueIdle() {
									timer.schedule(new RecordTimeTask(animation, ivPlay, type), recordTime);
									return false;
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
				}
			});
	    }else{
	    	ivPlay.setVisibility(View.GONE);
<<<<<<< HEAD
	    	ivVedio.setVisibility(View.GONE);
=======
>>>>>>> 3b3581198e1fec9c4dfce8620d803bfe29827f12
	    	viewHolder.msgBgView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
				}
			});
	    }
	    viewHolder.msgBgView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return false;
			}
		});

	    if(null!= msg.getBitmap()){
	    	ImageUtil.setThumbnailView(msg.getBitmap(), viewHolder.ivUserImage, context, new ImageCallback() {
				
				@Override
				public void loadImage(Bitmap bitmap, String imagePath) {
					
						ImageView img = (ImageView) lv.findViewWithTag(imagePath);
						img.setImageBitmap(bitmap);
					
				}
			}, true);
	    }
	    return convertView;
    }
    
    private final class RecordTimeTask extends TimerTask{
		private AnimationDrawable animation;
		private ImageView ivPlay;
		private int type;
		public RecordTimeTask(AnimationDrawable animation, ImageView ivPlay, int type) {
			this.animation = animation;
			this.ivPlay = ivPlay;
			this.type = type;
		}
		public void run() {
			if(animation.isRunning()){
				animation.stop();
				Log.i(ContentFlag.TAG, "timer task:"+animation.hashCode());
				animation = null;
				android.os.Message msg = handle.obtainMessage();
				msg.obj = ivPlay;
				msg.arg1 = this.type;
				handle.sendMessage(msg);
			}
		}
    }
    
    private static Handler handle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			ImageView ivPlay = (ImageView) msg.obj;
			int type = msg.arg1;
			if(type == 0){
				ivPlay.setBackgroundResource(R.drawable.chatto_voice_playing);
			}else{
				ivPlay.setBackgroundResource(R.drawable.chatfrom_voice_playing);
			}
		}
    };
    
    private class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvUserName;
        public View msgBgView;
        public ImageView ivUserImage;
    }
}
