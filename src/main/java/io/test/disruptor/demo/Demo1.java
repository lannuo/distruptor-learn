package io.test.disruptor.demo;

import com.lmax.disruptor.*;
import io.test.disruptor.entity.TradeTransaction;
import io.test.disruptor.handler.TradeTransactionDBHandler;

import java.util.concurrent.*;

public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBER = 4;

        final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(TradeTransaction::new, BUFFER_SIZE, new YieldingWaitStrategy());

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBER);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        BatchEventProcessor<TradeTransaction> transProcessor = new BatchEventProcessor<>(ringBuffer, sequenceBarrier, new TradeTransactionDBHandler());

        ringBuffer.addGatingSequences(transProcessor.getSequence());

        executors.submit(transProcessor);

        Future<?> future = executors.submit((Callable<Void>) () -> {
            long seq;
            for (int i = 0; i < 1000; i++) {
                seq = ringBuffer.next();
                ringBuffer.get(seq).setPrice(Math.random() * 9999);
                ringBuffer.publish(seq);
            }
            return null;
        });

        future.get();
        Thread.sleep(1000);
        transProcessor.halt();
        executors.shutdown();
    }
}
