package io.test.disruptor.translator;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import io.test.disruptor.entity.Order;

import java.nio.ByteBuffer;

public class OrderTranslator {
    private final RingBuffer<Order> ringBuffer;

    public OrderTranslator(RingBuffer<Order> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<Order, ByteBuffer> TRANSLATOR  =
            new EventTranslatorOneArg<Order, ByteBuffer>()
            {
                public void translateTo(Order event, long sequence, ByteBuffer bb)
                {
                    event.setId(bb.getLong(0));
                }
            };
    private static final EventTranslator<Order> TRANSLATOR1= (event, sequence) -> {
        event.setId(1);
    };

    public void onData()
    {
        ringBuffer.publishEvent(TRANSLATOR1);
    }
}
