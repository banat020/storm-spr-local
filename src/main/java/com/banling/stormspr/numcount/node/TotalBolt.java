package com.banling.stormspr.numcount.node;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banling.stormspr.config.SpringContext;
import com.banling.stormspr.service.NumCountService;

/**设计这个Bolt为了演示：经过前面的分组计算后，是否有序得到输入的数据。
 * 
 * @author Ban
 *
 */
public class TotalBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TotalBolt.class);

	private OutputCollector collector;
	private int totalCount=0;

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
		totalCount=numCountService.count(totalCount, inNum);
		LOGGER.info(" The inNum is {}, the Total is {} for now ", inNum,totalCount);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
