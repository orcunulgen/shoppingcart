package com.orcun.shoppingcart;

public class Campaign extends Discount {

    private Category category;
    private Integer numberOfItems;

    public Campaign(Double discountAmount, DiscountType discountType, Category category, Integer numberOfItems) {
        super(discountAmount, discountType);
        this.category = category;
        this.numberOfItems = numberOfItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Integer numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}

