package com.banling.stormspr.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.banling.stormspr.service.NumCountService;

@Service("numCountService")
@Lazy(false)//建议加上，否则在Storm的节点中，可能会无法通过Spring容器取得这个javabean
public class NumCountServiceImpl implements NumCountService {

	@Override
	public int count(int in,int curNum) {
		// TODO Auto-generated method stub
		return in+curNum;
	}

}
