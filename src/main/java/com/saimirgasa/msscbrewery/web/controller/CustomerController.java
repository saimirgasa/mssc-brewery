package com.saimirgasa.msscbrewery.web.controller;

import com.saimirgasa.msscbrewery.services.CustomerService;
import com.saimirgasa.msscbrewery.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

/**
 * Created by jt on 2019-04-21.
 */

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId")  UUID customerId){

        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@Validated @RequestBody CustomerDto customerDto){
        CustomerDto savedDto = customerService.saveNewCustomer(customerDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/api/v1/customer/" + savedDto.getId().toString());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleUpdate(@PathVariable("customerId") UUID customerId, @Validated @RequestBody CustomerDto customerDto){
        customerService.updateCustomer(customerId, customerDto);
    }

    @DeleteMapping("/{customerId}")
    public void deleteById(@PathVariable("customerId")  UUID customerId){
        customerService.deleteById(customerId);
    }
}
