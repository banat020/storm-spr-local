package com.banling.stormspr.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.banling.stormspr.service.NumInService;

@Service("numInService")
@Lazy(false) //建议加上，否则在Storm的节点中，可能会无法通过Spring容器取得这个javabean
public class NumInServiceImpl implements NumInService {
	
	private final int MAX_NUM=50;
	
	private int cur_num=0;

	@Override
	public synchronized int getNum() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(100);//产生数据不要太快了
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(cur_num<=MAX_NUM) {
			return cur_num++;
		}else {
			return -1;
		}
	}

}
