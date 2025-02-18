package com.ms.dlj.records;

import com.ms.dlj.customer.Address;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
