package com.banling.stormspr.numcount.node;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.banling.stormspr.config.SpringContext;
import com.banling.stormspr.service.NumInService;

/**数据输入
 * @author Ban
 *
 */
public class NumInSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector=collector;
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		NumInService numInService=(NumInService)SpringContext.getApplicationContext().getBean("numInService");
		int curNum=numInService.getNum();
		if(curNum==-1) {//计算已经结束
			return;
		}
		int groupFlag=curNum%10;//用于分组
		Values values = new Values(curNum,groupFlag);
		collector.emit(values);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("inNum","groupFlag"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		
		Map<String, Object> map=new HashMap<String, Object>();
		//仅仅是测试，因此不要跑得太快了，设置为每50ms发送一次数据
		map.put("topology.sleep.spout.wait.strategy.time.ms", 50);
	    return map;
	}
}
