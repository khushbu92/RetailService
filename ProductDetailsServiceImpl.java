package com.retail.service;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.controller.MyRetailController;
import com.retail.dao.ProductPriceDao;
import com.retail.model.ProductDetails;
import com.retail.model.ProductPrice;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService{
	
	private final Logger log = Logger.getLogger(MyRetailController.class.getName());
	
	@Value("target.restUrl1")
	private String url1;
	
	@Value("target.restUrl2")
	private String url2;
	
	@Autowired
	private ProductPriceDao productPricedao;
	@Autowired
	private RestTemplate restTemplate;
	

	@Override
	public ProductDetails getProductDetails(int id) {
		log.info("in  getProductDetails ");
		log.debug("id: "+id);
		String productName = null;
		try {
			productName = getProductName(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.debug("productName: "+productName);
		ProductPrice prodPrice=getProductPrice(id);
		if(prodPrice==null){
			throw new RuntimeException("price details for product with id="+id+" not found");
		}
		ProductDetails prodDetails= new ProductDetails(id,productName,prodPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;
		
	}
	
	public ProductPrice getProductPrice(int id){
		log.info("in getProductPrice");
		log.debug("id : "+id);
		ProductPrice prodPrice = productPricedao.findById(id);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	public ProductPrice updateProductPrice(int id,ProductDetails newProduct){
		log.info("in updateProductPrice");
		ProductPrice newProductPrice=newProduct.getProductPrice();
		newProductPrice.setId(id);
		if(productPricedao.findById(newProductPrice.getId())!=null){
			newProductPrice=productPricedao.updatePoductPrice(newProduct.getProductPrice().getValue(),newProduct.getProductPrice().getCurrencyCode(),newProduct.getId());
		}else{
			
			throw new RuntimeErrorException(null, "price details for product with id="+id+" not found");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}
	
	@Override
	public ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception{
		log.info("in putProductDetails");
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		ProductPrice newProductPrice=newProduct.getProductPrice();
		if(newProductPrice.getCurrencyCode()==null || newProductPrice.getValue()== 0){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=updateProductPrice(id,newProduct);
		
		newProduct.setProductPrice(newProductPrice);
		return newProduct;
	}
   
	private String getProductName(int id) throws IOException{
		log.info("in getProductName");
		String url=	url1+id+url2;
		ResponseEntity<String> response= restTemplate.getForEntity(url, String.class);
		ObjectMapper mapper = new ObjectMapper();
		String productName="";
		try {
			JsonNode root=null;
			String jsonString=response.getBody();
			if(jsonString!=null||!"".equals(jsonString)){
				root = mapper.readTree(jsonString);
				if(root.findValue("product")!=null){
					root=root.findValue("product");
					if(root.findValue("item")!=null){
						root=root.findValue("item");
						if(root.findValue("product_description")!=null){
							root=root.findValue("product_description");
							if(root.findValue("title")!=null){
								productName=root.findValue("title").asText();
							}
						}
					}
				}
			}
			log.debug("productName : "+productName);
		} 
		catch (IOException e) {
			log.error("Parsing failed IOException "+e.getMessage());
			throw new IOException(e.getMessage());
		}
		return productName;
	}
	
}
