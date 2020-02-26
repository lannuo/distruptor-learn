package io.test.disruptor.consumer;

import com.lmax.disruptor.EventHandler;
import io.test.disruptor.entity.TradeTransaction;

public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        System.out.println("consumer: "+ tradeTransaction.getId());
    }
}
