package UtilityClasses;

import java.util.Objects;

public class Goods {
    private String name;
    private int quantity;
    private float price;
    private int shopId;

    public Goods(String name, int shopId, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public int getShopId() {
        return shopId;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return  "\n" + "Goods{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", shopId=" + shopId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return quantity == goods.quantity &&
                Float.compare(goods.price, price) == 0 &&
                shopId == goods.shopId &&
                name.equals(goods.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, price, shopId);
    }
}
