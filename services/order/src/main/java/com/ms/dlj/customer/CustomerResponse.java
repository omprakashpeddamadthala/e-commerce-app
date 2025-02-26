package com.ms.dlj.customer;

public record CustomerResponse(
    String id,
    String firstName,
    String lastName,
    String email
) {
}
