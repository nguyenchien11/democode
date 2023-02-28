package com.coderc.ltsn.service;

import com.coderc.ltsn.models.Category;
import com.coderc.ltsn.models.request.AddCategoryRequest;
import com.coderc.ltsn.models.request.EditCategoryRequest;
import com.coderc.ltsn.models.response.AddCategoryResponse;

import java.util.List;

public interface CategoryService {

    Category addCategory(AddCategoryRequest request);

    boolean deleteCategory(long id);

    Category editCategory(EditCategoryRequest request , long id);

    List<Category> listCate();

    Category getCategoryById(long id);


}
