package io.test.disruptor.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.test.disruptor.entity.LongEvent;

import java.nio.ByteBuffer;

public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: "+ event));

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (int i = 0; true; i++) {
            bb.putLong(0, i);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)),bb);
            Thread.sleep(1000);
        }
    }
}
