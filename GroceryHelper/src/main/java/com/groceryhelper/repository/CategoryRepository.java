package com.groceryhelper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.groceryhelper.dao.CategoryEntity;


/**
 * @author divya
 *
 */

@Repository
public interface CategoryRepository extends CrudRepository <CategoryEntity, Long>{

}
