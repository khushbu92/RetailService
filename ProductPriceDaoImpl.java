package com.retail.dao;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.retail.model.ProductPrice;

@Repository
public class ProductPriceDaoImpl implements ProductPriceDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public ProductPrice findById(int id) {
		return (ProductPrice) jdbcTemplate.queryForObject(
	            "select value, currency_code from Product_Price where id=?",
	            new Object[]{id}, new ProductPriceRowMapper());
		
	}

	@Override
	public ProductPrice updatePoductPrice(int value,String currency,int id) {
		String query = "update Product_Price set value=? and currency_code=? from Product_Price where id=?";
	        Object[] params = {value, currency, id};
	        int[] types = {Types.INTEGER, Types.CHAR, Types.INTEGER};

	        int rows = jdbcTemplate.update(query, params, types);
	        System.out.println(rows + " row(s) updated.");
			return null;
			
	}
   
}
