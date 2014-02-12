package com.tee686.service.base;

import com.tee686.entity.Message;


/**
 * 接收并处理消息的接口
 * @author Administrator
 */
public interface IhandleMessge {
	public void handleMsg(Message msg);
}
