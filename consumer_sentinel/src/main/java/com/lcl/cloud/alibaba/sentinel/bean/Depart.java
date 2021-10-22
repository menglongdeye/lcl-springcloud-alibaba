package com.lcl.cloud.alibaba.sentinel.bean;

import lombok.*;

//Lombok注解
@Data//作用相当于：@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Builder
public class Depart {
    private Integer id;
    private String name;
}