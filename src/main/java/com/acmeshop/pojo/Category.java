
package com.acmeshop.pojo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

public class Category implements Serializable {

	@SerializedName("id")
	@Expose
	private String id;
	@SerializedName("name")
	@Expose
	private String name;
	private final static long serialVersionUID = 8887889962656783329L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Category() {
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Category) == false) {
			return false;
		}
		Category rhs = ((Category) other);
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).isEquals();
	}

}
