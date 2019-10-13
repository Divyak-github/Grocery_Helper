package com.groceryhelper.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.groceryhelper.dao.CategoryEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

	
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findCategorywhenEmptyIdTest() {
        Optional<CategoryEntity> CategoryEntity = categoryRepository.findById((long) 0);
        assertThat(CategoryEntity.isPresent()).isEqualTo(false);
    }
    
    @Test
    public void findCategorywhenNullIdTest() {
        Optional<CategoryEntity> CategoryEntity = categoryRepository.findById((long) -1);
        assertThat(CategoryEntity.isPresent()).isEqualTo(false);
    }
    
    @Test
    public void findCategorywhenValidIdTest() {
        Optional<CategoryEntity> CategoryEntity = categoryRepository.findById((long) 1);
        assertThat(CategoryEntity.isPresent()).isEqualTo(true);
    }

    @Test
    public void findAllCatTest() {
        Iterable<CategoryEntity> CategoryEntity  = categoryRepository.findAll();
        assertThat(CategoryEntity != null).isEqualTo(true);

    }

   
}