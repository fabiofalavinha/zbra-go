package com.zbra.go.service;

import com.zbra.go.log.Log;
import com.zbra.go.log.LogFactory;
import com.zbra.go.model.Category;
import com.zbra.go.model.CategoryType;
import com.zbra.go.persistence.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultCategoryService implements CategoryService {

    @Autowired
    private LogFactory logFactory;

    @Autowired
    private CategoryRepository categoryRepository;

    private List<Category> createDefaultCategories() {
        List<Category> categories = new ArrayList<>();

        Category restaurants = new Category();
        restaurants.setType(CategoryType.RESTAURANTS);
        restaurants.setScore(1);
        categories.add(restaurants);

        Category knownLocations = new Category();
        knownLocations.setType(CategoryType.KNOWN_LOCATIONS);
        knownLocations.setScore(2);
        categories.add(knownLocations);

        Category zbraOffices = new Category();
        zbraOffices.setType(CategoryType.ZBRA_OFFICES);
        zbraOffices.setScore(3);
        categories.add(zbraOffices);

        return categories;
    }

    @Transactional
    @PostConstruct
    public void seed() {
        final Log log = logFactory.createLog(this.getClass());
        log.info("Seeding categories...");
        createDefaultCategories().forEach(c -> {
            log.info("Seeding category [name=%s, score=%d]...", c.getType(), c.getScore());
            Category category = categoryRepository.findByType(c.getType());
            if (category == null) {
                log.info("Creating category [name=%s, score=%d]...", c.getType(), c.getScore());
                categoryRepository.save(c);
            } else {
                log.info("Category already on repository [name=%s, score=%d]", c.getType(), c.getScore());
            }
        });
    }

    @Transactional(readOnly = true)
    @Override
    public Category findByType(CategoryType type) {
        return categoryRepository.findByType(type);
    }
}
