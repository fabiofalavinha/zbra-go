package com.zbra.go.persistence;

import com.zbra.go.model.Category;
import com.zbra.go.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Category findByType(CategoryType type);

}
