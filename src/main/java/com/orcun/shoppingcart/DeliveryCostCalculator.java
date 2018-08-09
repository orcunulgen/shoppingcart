package com.orcun.shoppingcart;

import java.util.stream.Collectors;

public class DeliveryCostCalculator {
    private Double costPerDelivery;
    private Integer numberOfDeliveries;
    private Double costPerProduct;
    private Integer numberOfProducts;
    private Double fixedCost;

    public DeliveryCostCalculator(Double costPerDelivery, Double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = 2.99;
    }

    public DeliveryCostCalculator(Double costPerDelivery, Double costPerProduct, Double fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public Double calculateFor(ShoppingCart cart) {
        this.numberOfDeliveries = cart.getCartItemList().stream().map(item -> item.getProduct().getCategory()).distinct().collect(Collectors.toList()).size();
        this.numberOfProducts = cart.getCartItemList().stream().map(item -> item.getProduct()).distinct().collect(Collectors.toList()).size();

        return (getCostPerDelivery() * getNumberOfDeliveries()) + (getCostPerProduct() * getNumberOfProducts()) + getFixedCost();

    }

    public Double getCostPerDelivery() {
        return costPerDelivery;
    }

    public Integer getNumberOfDeliveries() {
        return numberOfDeliveries;
    }

    public Double getCostPerProduct() {
        return costPerProduct;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public Double getFixedCost() {
        return fixedCost;
    }

}
