package com.orcun.shoppingcart;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        Category cat1 = new Category("cat1");
        Category cat2 = new Category("cat2");
        Category cat3 = new Category("cat3");
        cat3.setParentCategory(Optional.ofNullable(cat2));

        Product p1 = new Product("p1", 10.0, cat1);
        Product p2 = new Product("p2", 20.0, cat2);
        Product p3 = new Product("p3", 30.0, cat3);

        CartItem cartItem1 = new CartItem(p1, 2);
        CartItem cartItem2 = new CartItem(p2, 3);
        CartItem cartItem3 = new CartItem(p3, 1);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(cartItem1);
        cart.addItem(cartItem2);
        cart.addItem(cartItem3);

        Campaign campaign1 = new Campaign(5.0, DiscountType.AMOUNT, cat1, 2);
        Campaign campaign2 = new Campaign(20.0, DiscountType.RATE, cat2, 2);
        Campaign campaign3 = new Campaign(50.0, DiscountType.RATE, cat3, 2);
        Coupon coupon1 = new Coupon(20.0, DiscountType.RATE, 70.0);
        Coupon coupon2 = new Coupon(5.0, DiscountType.AMOUNT, 70.0);
        Coupon coupon3 = new Coupon(50.0, DiscountType.AMOUNT, 70.0);
        cart.addDiscount(campaign1);
        cart.addDiscount(campaign2);
        cart.addDiscount(campaign3);
        cart.addDiscount(coupon1);
        cart.addDiscount(coupon2);
        cart.addDiscount(coupon3);

        DeliveryCostCalculator calculator = new DeliveryCostCalculator(2.0, 2.0);
        cart.setDeliveryCostCalculator(calculator);

        cart.print();
        System.out.println("Total Amount : " + cart.getTotalAmount());
        System.out.println("Campaign Discount : " + cart.getCampaignDiscount());
        System.out.println("Coupon Discount : " + cart.getCouponDiscount());
        System.out.println("Delivery Cost : " + cart.getDeliveryCost());
        System.out.println("Total Amount After Discount : " + cart.getTotalAmountAfterDiscount());
        System.out.println("Sub-Total : " + cart.getSubTotal());


    }
}
