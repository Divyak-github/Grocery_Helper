package com.groceryhelper.dao;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author divya
 *
 */
@Entity
@Table(name="TBL_GROCERYITEM")
public class GroceryItemEntity {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	
	@Column(name="grocery_name")
	private String groceryName;
	
	@Column(name="category_name")
	private String categoryName;
	
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private CategoryEntity category;
	
	
	public GroceryItemEntity(String groceryName, String categoryName) {
		this.groceryName = groceryName;
		this.categoryName= categoryName;
		
		
	}
	
	public GroceryItemEntity() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroceryName() {
		return groceryName;
	}
	public void setGroceryName(String groceryName) {
		this.groceryName = groceryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

	@Override
	public String toString() {
		return "GroceryItemEntity{" +
			"id=" + id +
			", groceryName='" + groceryName + '\'' +
			", categoryName='" + categoryName + '\''  +
			'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GroceryItemEntity groceryItemEntity = (GroceryItemEntity) o;
		return Objects.equals(id, groceryItemEntity.id) &&
			Objects.equals(groceryName, groceryItemEntity.groceryName);
	}

	@Override
	public int hashCode() {return Objects.hash(id, groceryName, categoryName);}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
}

