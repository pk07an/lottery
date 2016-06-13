package com.npc.lottery.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.npc.lottery.disruptor.DisruptorObject;
import com.npc.lottery.disruptor.LotteryEvent;
import com.npc.lottery.disruptor.LotteryEventHandler;
import com.npc.lottery.util.SpringUtil;

public class LotteryListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(LotteryListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		long startTime = System.currentTimeMillis();
		DisruptorObject.bjDisruptor.shutdown();
		DisruptorObject.cqDisruptor.shutdown();
		DisruptorObject.gdDisruptor.shutdown();
		DisruptorObject.ncDisruptor.shutdown();
		DisruptorObject.jssbDisruptor.shutdown();
		log.info("Disruptor关闭成功,耗时:" + (System.currentTimeMillis() - startTime) + "ms");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		EventHandler<LotteryEvent> eventHandler = (LotteryEventHandler) SpringUtil.getBean("lotteryEventHandler");
		IgnoreExceptionHandler ignoreExceptionHandler = new IgnoreExceptionHandler();
		
		//需在前面设置
		DisruptorObject.bjDisruptor.handleExceptionsWith(ignoreExceptionHandler);
		DisruptorObject.bjDisruptor.handleEventsWith(eventHandler);
		DisruptorObject.bjDisruptor.start();
		
		//需在前面设置
		DisruptorObject.cqDisruptor.handleExceptionsWith(ignoreExceptionHandler);
		DisruptorObject.cqDisruptor.handleEventsWith(eventHandler);
		DisruptorObject.cqDisruptor.start();

		//需在前面设置
		DisruptorObject.gdDisruptor.handleExceptionsWith(ignoreExceptionHandler);
		DisruptorObject.gdDisruptor.handleEventsWith(eventHandler);
		DisruptorObject.gdDisruptor.start();

		//需在前面设置
		DisruptorObject.ncDisruptor.handleExceptionsWith(ignoreExceptionHandler);
		DisruptorObject.ncDisruptor.handleEventsWith(eventHandler);
		DisruptorObject.ncDisruptor.start();

		//需在前面设置
		DisruptorObject.jssbDisruptor.handleExceptionsWith(ignoreExceptionHandler);
		DisruptorObject.jssbDisruptor.handleEventsWith(eventHandler);
		DisruptorObject.jssbDisruptor.start();

	}

}
