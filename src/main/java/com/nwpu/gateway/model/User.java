package com.nwpu.gateway.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String name;
    private String id;
    private int age;
}
