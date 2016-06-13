package com.npc.lottery.disruptor;

import com.lmax.disruptor.EventFactory;

public class LotteryEventFactory implements EventFactory<LotteryEvent>
{

	@Override
    public LotteryEvent newInstance() {
	    return new LotteryEvent();
    }

}
