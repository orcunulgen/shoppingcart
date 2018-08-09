package com.orcun.shoppingcart;

public class Coupon extends Discount {
    private Double minPurchase;

    public Coupon(Double discountAmount, DiscountType discountType, Double minPurchase) {
        super(discountAmount, discountType);
        this.minPurchase = minPurchase;
    }

    public Double getMinPurchase() {
        return minPurchase;
    }

    public void setMinPurchase(Double minPurchase) {
        this.minPurchase = minPurchase;
    }
}
