package com.npc.lottery.disruptor;

import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorObject {
	// RingBuffer 大小，必须是 2 的 N 次方
	public final static int RINGBUFFER_SIZE = 1024 * 1024;
	public final static EventFactory<LotteryEvent> eventFactory = new LotteryEventFactory();
	public final static WaitStrategy waitStrategy = new SleepingWaitStrategy();
	public final static Disruptor<LotteryEvent> bjDisruptor = new Disruptor<LotteryEvent>(eventFactory, RINGBUFFER_SIZE, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);

	public final static Disruptor<LotteryEvent> cqDisruptor = new Disruptor<LotteryEvent>(eventFactory, RINGBUFFER_SIZE, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);
	public final static Disruptor<LotteryEvent> gdDisruptor = new Disruptor<LotteryEvent>(eventFactory, RINGBUFFER_SIZE, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);
	public final static Disruptor<LotteryEvent> ncDisruptor = new Disruptor<LotteryEvent>(eventFactory, RINGBUFFER_SIZE, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);
	public final static Disruptor<LotteryEvent> jssbDisruptor = new Disruptor<LotteryEvent>(eventFactory, RINGBUFFER_SIZE, Executors.newCachedThreadPool(), ProducerType.SINGLE, waitStrategy);

}
