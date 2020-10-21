package Dao;

import UtilityClasses.Goods;
import UtilityClasses.Pair;
import UtilityClasses.Shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Dao {
    void createShop(Shop shop);
    boolean createGoods(String name, String shopName, int quantity, float price);
    void createBackup();
    HashMap<String, Goods> searchByShop(String shopName);
    ArrayList<Shop> searchByGoods(String goodsName);
    float buyGoods(String goodsName, String goodsShop, int quantity);
    Pair confirmOperation(String goodsName, String goodsShop, int quantity);
    void applyBackup();
    String getCheapest(String name);
    String getGoodsByBudget(int shopId, float cash);
    float getShipment(int shopId, HashMap<String,Integer> shipment);
    int getAverageCheapest(HashMap<String,Integer> shipment);
    Shop getShop(String shopName);
    HashMap<Integer, Shop> getShops();


}
