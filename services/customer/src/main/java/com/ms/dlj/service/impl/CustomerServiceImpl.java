package com.ms.dlj.service.impl;

import com.ms.dlj.customer.Customer;
import com.ms.dlj.exceptions.CustomerNotFoundException;
import com.ms.dlj.mapper.CustomerMapper;
import com.ms.dlj.records.CustomerRequest;
import com.ms.dlj.records.CustomerResponse;
import com.ms.dlj.repository.CustomerRepository;
import com.ms.dlj.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public String createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save( customerMapper.toCustomer( customerRequest ) );
        return customer.getId();
    }

    @Override
    public void updateCustomer(CustomerRequest customerRequest) {

        Customer customer = customerRepository.findById( customerRequest.id() )
                .orElseThrow( () -> new CustomerNotFoundException(
                        String.format( "Cannot update customer :: No customer found with provided ID :: %s", customerRequest.id() ) ) );

        mergeCustomer( customer, customerRequest );
        customerRepository.save( customer );
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map( customerMapper::fromCustomer ).
                collect( Collectors.toList() );

    }

    @Override
    public CustomerResponse getCustomerById(String customerId) {
        Customer customer = customerRepository.findById( customerId )
                .orElseThrow( () -> new CustomerNotFoundException(
                        String.format( "No customer found with provided customerId :: %s", customerId ) ) );
        return customerMapper.fromCustomer( customer );
    }

    @Override
    public void deleteCustomerById(String customerId) {
        customerRepository.deleteById( customerId );
    }

    @Override
    public Boolean existsCustomerById(String customerId) {
        return customerRepository.findById( customerId ).isPresent();
    }

    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
        if (StringUtils.isNotBlank( customerRequest.firstName() )) customer.setFirstName( customerRequest.firstName() );
        if (StringUtils.isNotBlank( customerRequest.lastName() )) customer.setLastName( customerRequest.lastName() );
        if (StringUtils.isNotBlank( customerRequest.email() )) customer.setEmail( customerRequest.email() );
        if (customerRequest.address() != null) customer.setAddress( customerRequest.address() );
    }
}
