package io.test.disruptor.demo;

import com.lmax.disruptor.*;
import io.test.disruptor.entity.TradeTransaction;
import io.test.disruptor.handler.TradeTransactionDBHandler;

import java.util.concurrent.*;

public class Demo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;

        RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(() -> new TradeTransaction(), BUFFER_SIZE);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);

        WorkHandler<TradeTransaction> workHandlers = new TradeTransactionDBHandler();

        WorkerPool<TradeTransaction> workerPool = new WorkerPool<>(ringBuffer,sequenceBarrier, new IgnoreExceptionHandler(), workHandlers);

        workerPool.start(executors);

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
        workerPool.halt();
        executors.shutdown();
    }
}
