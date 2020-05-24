package io.test.disruptor.entity;

import lombok.Data;

@Data
public class Order {
    private long id;
    private String name;
    private long value;
}
