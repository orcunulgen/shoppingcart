package com.orcun.shoppingcart;

public abstract class Discount {
    private Double discountAmount;
    private DiscountType discountType;

    public Discount(Double discountAmount, DiscountType discountType) {
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
