package com.banling.stormspr.numcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.banling.stormspr.numcount.node.GroupCountBolt;
import com.banling.stormspr.numcount.node.NumInSpout;
import com.banling.stormspr.numcount.node.TotalBolt;

public class LocalStorm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalStorm.class);
	
	public static void startStorm() {
		
		final String NUM_IN_SPOUT="NumInSpout";
		
		final String HALF_COUNT_BOLT="HalfCountBolt";
		
		final String TOTAL_COUNT_BOLT="TatalCountBolt";
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout(NUM_IN_SPOUT, new NumInSpout());
		
		//如果是多线程，那么，HalfCountBolt处理数据总体是无序的。
		//不过，可以实现分组有序处理，就像本例子一样；但是，这样一个计算节点TotalBolt接收到的数据就是无序的了。
		builder.setBolt(HALF_COUNT_BOLT, new GroupCountBolt(),10).fieldsGrouping(NUM_IN_SPOUT, new Fields("groupFlag"));
		
		builder.setBolt(TOTAL_COUNT_BOLT, new TotalBolt()).localOrShuffleGrouping(HALF_COUNT_BOLT);
		
		Config config=StormLocalConfig.config();
		
		//local模式
		LocalCluster cluster = new LocalCluster();
		LOGGER.info(" Storm Run In Local Way ... ");
    	cluster.submitTopology("LOCAL_TOPOLOGY", config, builder.createTopology());
	}
}
