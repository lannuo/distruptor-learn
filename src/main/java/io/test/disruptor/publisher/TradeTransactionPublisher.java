package io.test.disruptor.publisher;

import com.lmax.disruptor.dsl.Disruptor;
import io.test.disruptor.entity.TradeTransaction;
import io.test.disruptor.handler.TradeTransactionEventTranslator;

import java.util.concurrent.CountDownLatch;

public class TradeTransactionPublisher implements Runnable {
    Disruptor<TradeTransaction> disruptor;
    private CountDownLatch latch;
    private static int LOOP = 10000000;

    public TradeTransactionPublisher(CountDownLatch latch, Disruptor<TradeTransaction> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        TradeTransactionEventTranslator tradeTransloator=new TradeTransactionEventTranslator();
        for(int i=0;i<LOOP;i++){
            disruptor.publishEvent(tradeTransloator);
        }
        latch.countDown();
    }
}
