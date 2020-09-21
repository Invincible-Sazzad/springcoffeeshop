package com.agamilabs.controller;

import com.agamilabs.model.Customer;
import com.agamilabs.model.CustomerOrder;
import com.agamilabs.model.Product;
import com.agamilabs.repository.CustomerRepository;
import com.agamilabs.repository.OrderRepository;
import com.agamilabs.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;



@Controller
public class OrdersController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String productsList(Model model){
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("orders", orderRepository.findAll());
        return "orders";
    }

    @RequestMapping(value="/createorder", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrder(@RequestParam String firstName, @RequestParam String lastName, @RequestParam(value="productIds[]") Long[] productIds){

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customerRepository.save(customer);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customerRepository.findOne(customer.getCustomerId()));
        Set<Product> productSet = new HashSet<Product>();
        for (Long productId:productIds){
            productSet.add(productRepository.findOne(productId));
        }
        customerOrder.setProducts(productSet);
        Double total = 0.0;
        for (Long productId:productIds){
            total = total + (productRepository.findOne(productId).getProductPrice());
        }
        customerOrder.setTotal(total);
        orderRepository.save(customerOrder);

        return customerOrder.getOrderId().toString();
    }

    @RequestMapping(value = "/removeorder", method = RequestMethod.POST)
    @ResponseBody
    public String removeOrder(@RequestParam Long Id){
        orderRepository.delete(Id);
        return Id.toString();
    }
}