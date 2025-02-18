package com.ms.dlj.customer;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Address {

    private String id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
