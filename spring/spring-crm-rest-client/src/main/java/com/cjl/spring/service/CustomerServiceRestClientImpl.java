package com.cjl.spring.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cjl.spring.model.Customer;

@Service
public class CustomerServiceRestClientImpl implements CustomerService {

	private RestTemplate restTemplate;

	private String crmRestUrl;
		
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public CustomerServiceRestClientImpl(RestTemplate restTemplate, @Value("${crm.rest.url}") String url) {
		this.restTemplate = restTemplate;
		crmRestUrl = url;
		logger.info(">> Logging... Loaded property: crm.rest.url = " + crmRestUrl);
	}
	
	@Override
	public List<Customer> getCustomers() {
		logger.info(">> Logging... getting list of customers");
		
		// Make REST call
		ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(crmRestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Customer>>() {});
		
		List<Customer> customers = responseEntity.getBody();
		
		logger.info(">> Logging... customer list: " + customers);
		
		return customers;
	}

	@Override
	public Customer getCustomer(int id) {

		logger.info(">> Logging... getting a customer with id: " + id);

		// Make REST call
		Customer customer = restTemplate.getForObject(crmRestUrl + "/" + id, Customer.class);

		logger.info(">> Logging... customer: " + customer);
		
		return customer;
	}

	@Override
	public void saveCustomer(Customer customer) {

		logger.info(">> Logging... saving customer");
		
		int customerId = customer.getId();

		// Make REST call
		if (customerId == 0) {
			// Add
			restTemplate.postForEntity(crmRestUrl, customer, String.class);
		} else {
			// Update
			restTemplate.put(crmRestUrl, customer);
		}

		logger.info(">> Logging... successfully saved customer: " + customer);	
	}

	@Override
	public void deleteCustomer(int id) {

		logger.info(">> Logging... deleting customer with id: " + id);

		// Make REST call
		restTemplate.delete(crmRestUrl + "/" + id);

		logger.info(">> Logging... successfully deleted customer with id: " + id);
	}

}
