package com.retail.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.retail.model.ProductDetails;
import com.retail.service.ProductDetailsServiceImpl;

@RestController
public class MyRetailController {
	
	private final Logger log = Logger.getLogger(MyRetailController.class.getName());
	
	@Autowired
	ProductDetailsServiceImpl productDetailsService;
	
	
	@GetMapping(value="product/{id}")
	public ProductDetails getProductDetails(@PathVariable int id){
		log.info("in controller getProductDetails id :"+id);
		ProductDetails prodDetails=null;
		prodDetails=productDetailsService.getProductDetails(id);
		log.info(" return productDetails :"+prodDetails);
		return prodDetails;
	}
	
	@PutMapping(value="product/{id}")
	public ProductDetails putProductDetails(@PathVariable int id,@RequestBody ProductDetails prodDetails) throws Exception{
		log.info("in controller putProductDetails id :"+id);
		log.info("in controller putProductDetails requestBody :"+prodDetails);
		ProductDetails updatedProductDetails=productDetailsService.putProductDetails(id, prodDetails);
		log.info(" updated putProductDetails :"+updatedProductDetails);
		return updatedProductDetails;
	}
}
