package io.test.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import io.test.disruptor.entity.TradeTransaction;

import java.util.UUID;

public class TradeTransactionDBHandler implements EventHandler<TradeTransaction>, WorkHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        this.onEvent(tradeTransaction);
    }

    @Override
    public void onEvent(TradeTransaction tradeTransaction) throws Exception {
        tradeTransaction.setId(UUID.randomUUID().toString());
        System.out.println(tradeTransaction.getId());
    }
}
