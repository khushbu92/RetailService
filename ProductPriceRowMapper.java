package com.retail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.retail.model.ProductPrice;

public class ProductPriceRowMapper implements RowMapper<ProductPrice> {

	@Override
	public ProductPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductPrice productPrice = new ProductPrice();
		productPrice.setCurrencyCode(rs.getString("currencyCode"));
		productPrice.setValue(rs.getInt("value"));
		return productPrice;
	}

}
