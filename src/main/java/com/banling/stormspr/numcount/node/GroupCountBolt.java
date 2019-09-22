package com.banling.stormspr.numcount.node;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banling.stormspr.config.SpringContext;
import com.banling.stormspr.service.NumCountService;

/**演示分组
 * @author Ban
 *
 */
public class GroupCountBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupCountBolt.class);
	
	private OutputCollector collector;
	
	private int groupCount=0;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector=collector;
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		NumCountService numCountService=(NumCountService)SpringContext.getApplicationContext().getBean("numCountService");
		int inNum=input.getIntegerByField("inNum");
		int groupFlag=input.getIntegerByField("groupFlag");//用于分组的字段
		groupCount=numCountService.count(inNum,groupCount);
		LOGGER.info(" This Thread is {}, In data is {},GroupFlag is {}, and the curCount value is {}",
				Thread.currentThread().getName(),inNum,groupFlag,groupCount);
		Values values=new Values(groupCount,inNum);
		collector.emit(values);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("GroupCount","inNum"));
	}

}
