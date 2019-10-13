package com.groceryhelper.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.groceryhelper.dao.CategoryEntity;
import com.groceryhelper.dao.GroceryItemEntity;
import com.groceryhelper.exception.RecordNotFoundException;
import com.groceryhelper.service.GroceryHelperService;



/**
 * @author divya
 *
 */
@Controller
@RequestMapping("/")
public class GroceryHelperController
{
	
    @Autowired
    GroceryHelperService service;
 
    public static String selectedCat ="All"; 
    /**
     * @param model
     * @return
     */
    @RequestMapping
	public String getAllGroceries(Model model) 
	{
		List<GroceryItemEntity> list = service.getAllGroceries();
		model.addAttribute("groceries", list);
		model.addAttribute("categories", service.getAllCategories());
		CategoryEntity entity = new CategoryEntity();
		model.addAttribute("categoryItem", entity);
		return "groceries";
	}
    
   	/**
   	 * @param model
   	 * @param id
   	 * @return
   	 * @throws RecordNotFoundException
   	 */
   	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editGroceryItemById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			GroceryItemEntity entity = service.getGroceryItemById(id.get());
			model.addAttribute("groceryItem", entity);
		} else {
			model.addAttribute("groceryItem", new GroceryItemEntity());
		}
		return "add-edit-groceryItem";
	}
	
	/**
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@RequestMapping(path = "/delete/{id}")
	public String deleteGroceryItemById(Model model, @PathVariable("id") Long id) 
							throws RecordNotFoundException 
	{
		
		try
		{
			service.deleteGroceryItemById(id);
		}
		catch(Exception e)
		{
			return "error";
		}
		
		return "redirect:/";
	}
	
	
    /**
     * @param groceryItem
     * @return
     * @throws RecordNotFoundException
     */
    @RequestMapping(path = "/createGroceryItem", method = RequestMethod.POST)
    public String createOrUpdateGroceryItem(GroceryItemEntity groceryItem) throws RecordNotFoundException
    {
        service.createOrUpdateGroceryItem(groceryItem);
        return "redirect:/";
    }
    
   
    /**
     * @param model
     * @param id
     * @return
     * @throws RecordNotFoundException
     * Pre-Tag
     */
    @RequestMapping(path = {"/tag", "/tag/{id}"})
	public String tagGroceryItemById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			GroceryItemEntity entity = service.getGroceryItemById(id.get());
			model.addAttribute("groceryItem", entity);
		} else {
			model.addAttribute("groceryItem", new GroceryItemEntity());
		}
		model.addAttribute("categories", service.getAllCategories());
		return "tag-category";
	}
    
   
    /**
     * @param groceryItem
     * @return
     * @throws RecordNotFoundException
     * Post Tag
     */
    @RequestMapping(path = "/tagGroceryItem", method = RequestMethod.POST)
    public String tagGroceryItem(GroceryItemEntity groceryItem) throws RecordNotFoundException
    {
        service.createOrUpdateGroceryItem(groceryItem);
        return "redirect:/";
    }
    
     
      /**
     * @param model
     * @param categoryEntity
     * @param redirectAttributes
     * @return
     */
    @SuppressWarnings("static-access")
      @RequestMapping(path = {"/searchByCategory"}) 
	  public String getGroceriesByCategory(Model model,CategoryEntity categoryEntity, RedirectAttributes redirectAttributes)
	  { 
    	 try 
    	 {
	    	 if(categoryEntity != null) { 
				  categoryEntity =
						  service.getCategoryByName(categoryEntity.getCategoryName()); }
		   	  this.selectedCat =(
		   			  (categoryEntity.getCategoryName()==null ||categoryEntity.getCategoryName().equalsIgnoreCase("0"))?"":categoryEntity.getCategoryName()); 	  
		   	  redirectAttributes.addFlashAttribute("sC", categoryEntity.getCategoryName());
    	 }
	   	catch(Exception e)
		{
			return "error";
		}
		  return "redirect:/"; 
	}
	 
   
}