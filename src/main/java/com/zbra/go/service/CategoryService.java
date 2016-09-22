package com.zbra.go.service;

import com.zbra.go.model.Category;
import com.zbra.go.model.CategoryType;

public interface CategoryService {

    Category findByType(CategoryType type);

}
