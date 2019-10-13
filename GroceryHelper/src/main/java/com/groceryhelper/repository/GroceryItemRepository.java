
package com.groceryhelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.groceryhelper.dao.GroceryItemEntity;


/**
 * @author divya
 *
 */

@Repository
public interface GroceryItemRepository extends CrudRepository <GroceryItemEntity, Long> {



	

}
