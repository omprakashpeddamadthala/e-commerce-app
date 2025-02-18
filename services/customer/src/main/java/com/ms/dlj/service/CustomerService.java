package com.ms.dlj.service;

import com.ms.dlj.records.CustomerRequest;
import com.ms.dlj.records.CustomerResponse;

import java.util.List;

public interface CustomerService {
    String createCustomer(CustomerRequest customerRequest);

    void updateCustomer(CustomerRequest customerRequest);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(String customerId);

    void deleteCustomerById(String customerId);

    Boolean existsCustomerById(String customerId);
}
