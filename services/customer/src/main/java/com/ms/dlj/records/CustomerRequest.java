package com.ms.dlj.records;

import com.ms.dlj.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer first name required")
        String firstName,
        @NotNull(message = "Customer last name required")
        String lastName,
        @NotNull(message = "Customer email required")
        @Email(message = "Customer email is not a valid email address")
        String email,
        Address address
) {

}
