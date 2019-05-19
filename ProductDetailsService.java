package com.retail.service;

import com.retail.model.ProductDetails;

public interface ProductDetailsService {
	
	public ProductDetails getProductDetails(int id);

	ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception;
}