package com.retail.dao;

import org.springframework.stereotype.Repository;

import com.retail.model.ProductPrice;

@Repository
public interface ProductPriceDao{

	public ProductPrice findById(int id);
	
	public ProductPrice updatePoductPrice(int value,String currency,int id);

}
