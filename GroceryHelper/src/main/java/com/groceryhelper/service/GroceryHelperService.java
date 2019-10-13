package com.groceryhelper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groceryhelper.controller.GroceryHelperController;
import com.groceryhelper.dao.CategoryEntity;
import com.groceryhelper.dao.GroceryItemEntity;
import com.groceryhelper.exception.RecordNotFoundException;
import com.groceryhelper.repository.CategoryRepository;
import com.groceryhelper.repository.GroceryItemRepository;

/**
 * @author divya
 *
 */
@Service
public class GroceryHelperService {

	@Autowired
	CategoryRepository categoryRepository;
	
	
	@Autowired
	GroceryItemRepository groceryItemRepository;
	
	public GroceryHelperService() {
	}
	
	
	/**
	 * @return
	 */
	public List<GroceryItemEntity> getAllGroceries()
	{
		List<GroceryItemEntity> result = (List<GroceryItemEntity>) groceryItemRepository.findAll();
		result = this.getGroceriesToLoad(result);
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<GroceryItemEntity>();
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	public GroceryItemEntity getGroceryItemById(Long id) throws RecordNotFoundException 
	{
		Optional<GroceryItemEntity> groceryItem = groceryItemRepository.findById(id);
		if(groceryItem.isPresent()) {
			return groceryItem.get();
		} else {
			throw new RecordNotFoundException("No grocery item exist for given id");
		}
	}
	
	/**
	 * @param entity
	 * @return
	 * @throws RecordNotFoundException
	 */
	public GroceryItemEntity createOrUpdateGroceryItem(GroceryItemEntity entity) throws RecordNotFoundException 
	{
		
		CategoryEntity cEntity;
		if(entity.getCategory()!=null) 
			cEntity = entity.getCategory();
		else
			cEntity = getCategoryByName(entity.getCategoryName());
			if(entity.getId()  == null) 
		{
			//new entry
			GroceryItemEntity newEntry = new GroceryItemEntity(entity.getGroceryName(),entity.getCategoryName());
			newEntry.setCategory(cEntity);
			entity = groceryItemRepository.save(newEntry);
			return entity;
		} 
		else 
		{
			Optional<GroceryItemEntity> groceryItem = groceryItemRepository.findById(entity.getId());
			if(groceryItem.isPresent()) 
			{
				GroceryItemEntity newEntity = groceryItem.get();
				newEntity.setGroceryName(entity.getGroceryName());
				newEntity.setCategoryName(entity.getCategoryName());
				if(newEntity.getCategory() != null)
					newEntity.setCategory(null);
				newEntity.setCategory(cEntity);
				newEntity = groceryItemRepository.save(newEntity);
				return newEntity;
			} else {
				entity = groceryItemRepository.save(entity);
				
				return entity;
			}
		}
	} 
	
	/**
	 * @param id
	 * @throws RecordNotFoundException
	 */
	public void deleteGroceryItemById(Long id) throws RecordNotFoundException 
	{
		Optional<GroceryItemEntity> groceryItem = groceryItemRepository.findById(id);
		
		if(groceryItem.isPresent()) 
		{

			groceryItem.get().setCategory(null);
			groceryItemRepository.save(groceryItem.get());
			groceryItemRepository.deleteById(id);
		
		} else {
			throw new RecordNotFoundException("No groceryItem record exist for given id");
		}
	}

	

	
	
	/**
	 * @param id
	 * @return
	 */
	public List<GroceryItemEntity> getGroceriesByCategory(Long id)
	{
		
		List<GroceryItemEntity> result = (List<GroceryItemEntity>) groceryItemRepository.findAll();
		Optional<CategoryEntity> cIE = categoryRepository.findById(id);
		List<GroceryItemEntity> finalResult  = result.stream()
		.filter(d -> d.getCategoryName().equalsIgnoreCase(cIE.get().getCategoryName()))
        .collect(Collectors.toList());

		if(finalResult.size() > 0) {
			return finalResult;
		} else {
			return new ArrayList<GroceryItemEntity>();
		}
	} 
	
	/**
	 * @param input
	 * @return
	 */
	public List<GroceryItemEntity> getGroceriesToLoad(List<GroceryItemEntity> input)
	{
		List<GroceryItemEntity> output = input;
		if(
				GroceryHelperController.selectedCat != null && 
				!GroceryHelperController.selectedCat.isEmpty() &&
				!GroceryHelperController.selectedCat.isBlank() &&
				!GroceryHelperController.selectedCat.equalsIgnoreCase("0") &&
				!GroceryHelperController.selectedCat.equalsIgnoreCase("") &&
				!GroceryHelperController.selectedCat.equalsIgnoreCase("All"))
		{
			System.out.println(getCategoryByName(GroceryHelperController.selectedCat).getId());
			System.out.println(getGroceriesByCategory(getCategoryByName(GroceryHelperController.selectedCat).getId()));
			if(getCategoryByName(GroceryHelperController.selectedCat).getId() > 0)
				output = getGroceriesByCategory(getCategoryByName(GroceryHelperController.selectedCat).getId());
		}
		return output;
	}
	
	
	/**
	 * @return
	 */
	public List<CategoryEntity> getAllCategories() {
	
		List<CategoryEntity> result = (List<CategoryEntity>) categoryRepository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<CategoryEntity>();
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	public CategoryEntity getCategoryById(Long id) throws RecordNotFoundException 
	{
		Optional<CategoryEntity> category = categoryRepository.findById(id);
		
		if(category.isPresent()) {
			return category.get();
		} else {
			throw new RecordNotFoundException("No category exists for given id");
		}
	}
	
	/**
	 * @param categoryName
	 * @return
	 */
	public CategoryEntity getCategoryByName(String categoryName)
	{
		// TODO Auto-generated method stub
		List<CategoryEntity> result = (List<CategoryEntity>) categoryRepository.findAll();
		CategoryEntity finalResult= result.stream()
				  .filter(x -> categoryName.equals(x.getCategoryName()))
				  .findAny()
				  .orElse(null);

		if(finalResult != null) {
			return finalResult;
		} else 
			return new CategoryEntity(categoryName);
		
	} 
	
}
