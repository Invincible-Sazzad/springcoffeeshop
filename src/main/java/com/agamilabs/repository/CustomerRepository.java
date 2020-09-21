package com.agamilabs.repository;

import com.agamilabs.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
