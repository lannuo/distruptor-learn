package io.test.disruptor.demo;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import io.test.disruptor.consumer.TradeTransactionVasConsumer;
import io.test.disruptor.entity.TradeTransaction;
import io.test.disruptor.handler.TradeTransactionDBHandler;
import io.test.disruptor.handler.TradeTransactionJMSNotifyHandler;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo3 {
    private final static int BUFFER_SIZE = 1024;
    private final static int THREAD_NUMBER = 4;

    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBER);

        Instant start = Instant.now();

        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger index = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
            }
        };

        Disruptor<TradeTransaction> disruptor = new Disruptor<TradeTransaction>(TradeTransaction::new, BUFFER_SIZE, threadFactory, ProducerType.SINGLE, new BusySpinWaitStrategy());

        EventHandlerGroup<TradeTransaction> handlerGroup = disruptor.handleEventsWith(new TradeTransactionVasConsumer(), new TradeTransactionDBHandler());

        TradeTransactionJMSNotifyHandler jmsConsumer = new TradeTransactionJMSNotifyHandler();
        handlerGroup.then(jmsConsumer);

        disruptor.start();


    }
}
