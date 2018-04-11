
package com.acmeshop.pojo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

public class Product implements Serializable {

	@SerializedName("sku")
	@Expose
	private String sku;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("price")
	@Expose
	private Double price;
	@SerializedName("upc")
	@Expose
	private String upc;
	@SerializedName("category")
	@Expose
	private List<Category> category = null;
	@SerializedName("shipping")
	@Expose
	private String shipping;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("manufacturer")
	@Expose
	private String manufacturer;
	@SerializedName("model")
	@Expose
	private String model;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("image")
	@Expose
	private String image;
	private final static long serialVersionUID = 272879787532822582L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Product() {
	}

	/**
	 * 
	 * @param shipping
	 * @param model
	 * @param category
	 * @param price
	 * @param manufacturer
	 * @param description
	 * @param name
	 * @param image
	 * @param type
	 * @param sku
	 * @param upc
	 * @param url
	 */
	public Product(String sku, String name, String type, Double price, String upc, List<Category> category,
			String shipping, String description, String manufacturer, String model, String url, String image) {
		super();
		this.sku = sku;
		this.name = name;
		this.type = type;
		this.price = price;
		this.upc = upc;
		this.category = category;
		this.shipping = shipping;
		this.description = description;
		this.manufacturer = manufacturer;
		this.model = model;
		this.url = url;
		this.image = image;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("sku", sku).append("name", name).append("type", type)
				.append("price", price).append("upc", upc).append("category", category).append("shipping", shipping)
				.append("description", description).append("manufacturer", manufacturer).append("model", model)
				.append("url", url).append("image", image).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(model).append(image).append(type).append(sku).append(upc).append(url)
				.append(shipping).append(category).append(price).append(manufacturer).append(description).append(name)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Product) == false) {
			return false;
		}
		Product rhs = ((Product) other);
		return new EqualsBuilder().append(model, rhs.model).append(image, rhs.image).append(type, rhs.type)
				.append(sku, rhs.sku).append(upc, rhs.upc).append(url, rhs.url).append(shipping, rhs.shipping)
				.append(category, rhs.category).append(price, rhs.price).append(manufacturer, rhs.manufacturer)
				.append(description, rhs.description).append(name, rhs.name).isEquals();
	}

}
