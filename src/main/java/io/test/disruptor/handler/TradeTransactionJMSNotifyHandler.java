package io.test.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import io.test.disruptor.entity.TradeTransaction;

public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {
    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        System.out.println("send to message : "+tradeTransaction.getId());
    }
}
