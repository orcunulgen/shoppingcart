package com.orcun.shoppingcart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ShoppingCart {
    private List<CartItem> cartItemList;
    private List<Discount> discountList;

    private DeliveryCostCalculator deliveryCostCalculator;

    public ShoppingCart() {
        this.cartItemList = new ArrayList<>();
        this.discountList = new ArrayList<>();
    }

    public ShoppingCart(List<CartItem> cartItemList, List<Discount> discountList) {
        this.cartItemList = cartItemList;
        this.discountList = discountList;
    }

    public Double getTotalAmountAfterDiscount() {
        Double amountAfterCampaignDiscount = discountCampaign(getTotalAmount());
        Double amountAfterCouponDiscount = discountCoupon(amountAfterCampaignDiscount);
        return amountAfterCouponDiscount;
    }

    public Double getSubTotal() {
        return getTotalAmountAfterDiscount() + getDeliveryCost();
    }

    public Double getDeliveryCost() {
        Optional<DeliveryCostCalculator> deliveryCostCalculatorOpt = Optional.ofNullable(getDeliveryCostCalculator());
        return deliveryCostCalculatorOpt.map(calculator -> calculator.calculateFor(this)).orElse(0.0);
    }

    private Double discountCoupon(Double amount) {
        List<Coupon> couponList = getCouponList();
        if (couponList == null || couponList.isEmpty())
            return amount;

        Double discountAmount = amount;
        for (Coupon coupon : couponList) {
            discountAmount -= calculateDiscount(discountAmount, coupon);
        }
        return discountAmount;

    }

    private Double discountCampaign(Double amount) {
        List<Campaign> campaignList = getCampaignList();

        if (campaignList == null || campaignList.isEmpty())
            return amount;

        return campaignList.stream().map(campaign -> amount - calculateDiscount(amount, campaign)).min(Double::compare).get();
    }

    private Double calculateDiscount(Double amount, Discount discount) {
        if (discount instanceof Campaign) {
            return calculateCampaignDiscount(amount, (Campaign) discount);
        } else if (discount instanceof Coupon) {
            return calculateCouponDiscount(amount, (Coupon) discount);
        } else {
            return amount;
        }
    }

    private Double calculateCouponDiscount(Double amount, Coupon coupon) {
        if (amount > coupon.getMinPurchase()) {
            if (coupon.getDiscountType().equals(DiscountType.AMOUNT)) {
                return coupon.getDiscountAmount();
            } else if (coupon.getDiscountType().equals(DiscountType.RATE)) {
                return amount * coupon.getDiscountAmount() / 100;
            }
        }
        return 0.0;
    }

    private Double calculateCampaignDiscount(Double amount, Campaign campaign) {
        Integer itemListSize = getItemListSizeByCategory(campaign.getCategory());
        if (itemListSize >= campaign.getNumberOfItems()) {
            if (campaign.getDiscountType().equals(DiscountType.AMOUNT)) {
                return campaign.getDiscountAmount();
            } else if (campaign.getDiscountType().equals(DiscountType.RATE)) {
                return amount * campaign.getDiscountAmount() / 100;
            }
        }
        return 0.0;
    }

    private int getItemListSizeByCategory(Category category) {
        return getCartItemList().stream().filter(cartItem -> cartItem.getProduct().getCategory().equals(category))
                .map(item -> item.getQuantity())
                .reduce((q1, q2) -> q1 + q2).orElse(0);

    }

    private List<Coupon> getCouponList() {
        return getDiscountList().stream().filter(discount -> discount instanceof Coupon)
                .map(discount -> (Coupon) discount)
                .collect(Collectors.toList());
    }

    private List<Campaign> getCampaignList() {
        return getDiscountList().stream().filter(discount -> discount instanceof Campaign)
                .map(discount -> (Campaign) discount)
                .collect(Collectors.toList());
    }


    public Double getTotalAmount() {
        return getCartItemList().stream()
                .map(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .collect(Collectors.summarizingDouble(i -> i)).getSum();
    }

    public void addItem(CartItem item) {
        Optional<CartItem> itemOpt = Optional.ofNullable(item);
        itemOpt.ifPresent(i -> {
            Optional<Product> productOpt = Optional.ofNullable(item.getProduct());
            Optional<Integer> quantityOpt = Optional.ofNullable(item.getQuantity());
            if (productOpt.isPresent() && quantityOpt.isPresent())
                this.cartItemList.add(i);
        });
    }

    public void addDiscount(Discount discount) {
        Optional<Discount> discountOpt = Optional.ofNullable(discount);
        discountOpt.ifPresent(d -> {
            Optional<Double> discountAmountOpt = Optional.ofNullable(d.getDiscountAmount());
            Optional<DiscountType> discountTypeOpt = Optional.ofNullable(d.getDiscountType());

            if (discountAmountOpt.isPresent() && discountTypeOpt.isPresent())
                this.discountList.add(d);
        });
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }


    public List<Discount> getDiscountList() {
        return discountList;
    }

    public Double getCouponDiscount() {
        return discountCampaign(getTotalAmount()) - getTotalAmountAfterDiscount();
    }

    public Double getCampaignDiscount() {
        return getTotalAmount() - discountCampaign(getTotalAmount());
    }

    public DeliveryCostCalculator getDeliveryCostCalculator() {
        return deliveryCostCalculator;
    }

    public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
    }


    public void print() {
        Map<Tuple, List<CartItem>> itemsGroupedByCategoryMap = getCartItemList().stream()
                .collect(Collectors.groupingBy(item -> new Tuple(item.getProduct().getCategory(), item)));

        System.out.printf("%20s %20s %20s %20s", "Category Name", "Product Name", "Unit Price", "Quantity");
        System.out.println();
        itemsGroupedByCategoryMap.forEach((tuple, cartItems) -> {
            cartItems.forEach(i -> {
                System.out.format("%20s %20s %20s %20s", i.getProduct().getCategory().getTitle(),
                        i.getProduct().getTitle(),
                        i.getProduct().getPrice().toString(),
                        i.getQuantity().toString());
                System.out.println();
            });
        });
        System.out.println();
    }

    private class Tuple {
        Category category;
        CartItem item;

        public Tuple(Category category, CartItem item) {
            this.category = category;
            this.item = item;
        }
    }
}
