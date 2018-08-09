package com.orcun.shoppingcart;

import java.util.Objects;
import java.util.Optional;

public class Category {
    private String title;
    private Optional<Category> parentCategory;

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<Category> getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Optional<Category> parentCategory) {
        this.parentCategory = parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
