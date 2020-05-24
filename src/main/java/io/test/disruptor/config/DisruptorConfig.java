package io.test.disruptor.config;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.test.disruptor.entity.Order;
import io.test.disruptor.handler.ClearingEventHandler;
import io.test.disruptor.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {
    @Bean
    public Disruptor disruptor(){
        int bufferSize = 2048;

        // Construct the Disruptor
        Disruptor<Order> disruptor = new Disruptor<>(Order::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new YieldingWaitStrategy());

        // Connect the handler
        disruptor.handleEventsWith(OrderHandler::onEvent).then(new ClearingEventHandler());

        return disruptor;
    }
}
