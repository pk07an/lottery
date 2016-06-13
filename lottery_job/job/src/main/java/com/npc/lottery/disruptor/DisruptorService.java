package com.npc.lottery.disruptor;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.npc.lottery.common.Constant;

@Service
public class DisruptorService {

	private static final Logger log = Logger.getLogger(DisruptorService.class);

	public boolean publishEvent(String playType, Map<String, List<Integer>> resultMap) {
		boolean flag = true;
		Disruptor<LotteryEvent> disruptor = null;
		if (StringUtils.isEmpty(playType)) {
			flag = false;
		} else {
			if (Constant.LOTTERY_TYPE_BJSC.equalsIgnoreCase(playType)) {
				disruptor = DisruptorObject.bjDisruptor;
			} else if (Constant.LOTTERY_TYPE_CQSSC.equalsIgnoreCase(playType)) {
				disruptor = DisruptorObject.cqDisruptor;
			} else if (Constant.LOTTERY_TYPE_GDKLSF.equalsIgnoreCase(playType)) {
				disruptor = DisruptorObject.gdDisruptor;
			} else if (Constant.LOTTERY_TYPE_K3.equalsIgnoreCase(playType)) {
				disruptor = DisruptorObject.jssbDisruptor;
			} else if (Constant.LOTTERY_TYPE_NC.equalsIgnoreCase(playType)) {
				disruptor = DisruptorObject.ncDisruptor;
			} else {
				flag = false;
			}

			if (null != disruptor) {
				// 发布事件；
				RingBuffer<LotteryEvent> ringBuffer = disruptor.getRingBuffer();
				long sequence = ringBuffer.next();// 请求下一个事件序号；

				try {
					LotteryEvent event = ringBuffer.get(sequence);// 获取该序号对应的事件对象；
					event.setPlayType(playType);// 获取要通过事件传递的业务数据；
					event.setResultMap(resultMap);
				} finally {
					ringBuffer.publish(sequence);// 发布事件；
					log.info("Disruptor 事件发布完成,playType:" + playType + "|resultMap:" + resultMap + "|sequence:" + sequence);
				}
			} else {
				flag = false;
			}

		}
		return flag;
	}
}
