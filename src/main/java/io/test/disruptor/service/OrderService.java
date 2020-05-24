package io.test.disruptor.service;

import com.lmax.disruptor.dsl.Disruptor;
import io.test.disruptor.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private Disruptor<Order> disruptor;

    @PostConstruct
    public void start() {
        log.info("START1.......");
        disruptor.start();
    }

    @PreDestroy
    public void stop() {
        log.info("STOP1.............");
        disruptor.shutdown();
    }

    public void addOrder(){
        // Get the ring buffer from the Disruptor to be used for publishing.
//        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();
//        OrderTranslator orderTranslator=new OrderTranslator(ringBuffer);
//        for (long l = 0; true; l++)
//        {
//            ringBuffer.publishEvent(orderTranslator.onData());
//        }
    }

}
