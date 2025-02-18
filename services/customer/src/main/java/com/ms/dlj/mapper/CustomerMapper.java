package com.ms.dlj.mapper;


import com.ms.dlj.customer.Customer;
import com.ms.dlj.records.CustomerRequest;
import com.ms.dlj.records.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {


    public Customer toCustomer(CustomerRequest customerRequest) {

        if( customerRequest == null ) return null;

        return Customer.builder()
                .id( customerRequest.id() )
                .firstName( customerRequest.firstName() )
                .lastName( customerRequest.lastName() )
                .email( customerRequest.email() )
                .address( customerRequest.address() ).
                build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId() ,
                customer.getFirstName() ,
                customer.getLastName() ,
                customer.getEmail() ,
                customer.getAddress() );
    }
}
