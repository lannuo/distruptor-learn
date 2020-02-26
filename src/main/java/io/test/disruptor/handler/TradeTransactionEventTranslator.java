package io.test.disruptor.handler;

import com.lmax.disruptor.EventTranslator;
import io.test.disruptor.entity.TradeTransaction;

import java.util.Random;

public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random=new Random();

    @Override
    public void translateTo(TradeTransaction tradeTransaction, long l) {
        this.generateTradeTransaction(tradeTransaction);
    }

    private TradeTransaction generateTradeTransaction(TradeTransaction trade){
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    }
}
