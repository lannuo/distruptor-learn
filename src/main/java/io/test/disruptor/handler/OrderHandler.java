package io.test.disruptor.handler;

import io.test.disruptor.entity.Order;

public class OrderHandler{

    public static void onEvent(Order event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.toString());
    }
}
