package com.agamilabs.repository;

import com.agamilabs.model.CustomerOrder;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CustomerOrder,Long>{

}
