package com.groceryhelper.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.groceryhelper.dao.GroceryItemEntity;

/**
 * Created by Divya.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroceryItemRepositoryTest {

	
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Test
    public void findGIwhenEmptyIdTest() {
        Optional<GroceryItemEntity> groceryItemEntity = groceryItemRepository.findById((long) 0);
        assertThat(groceryItemEntity.isPresent()).isEqualTo(false);
    }
    
    @Test
    public void findGIwhenNullIdTest() {
        Optional<GroceryItemEntity> groceryItemEntity = groceryItemRepository.findById((long) -1);
        assertThat(groceryItemEntity.isPresent()).isEqualTo(false);
    }
    
    @Test
    public void findGIwhenValidIdTest() {
        Optional<GroceryItemEntity> groceryItemEntity = groceryItemRepository.findById((long) 1);
        assertThat(groceryItemEntity.isPresent()).isEqualTo(false);
    }

    @Test
    public void findAllGIsTest() {
        Iterable<GroceryItemEntity> groceryItemEntity  = groceryItemRepository.findAll();
        assertThat(groceryItemEntity != null).isEqualTo(true);

    }

   
    @After
    public void cleanUp() {
        groceryItemRepository.deleteAll();
    }

}