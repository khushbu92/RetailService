package com.retail.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductPrice {
    
	@Id
	private int id;
	private int value;
	private String currencyCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonIgnore
	@JsonProperty(value = "id")
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	@Override
	public String toString() {
		return "CurrentPrice [id=" + id + ", value=" + value + ", currencyCode=" + currencyCode + "]";
	}
	
	
}