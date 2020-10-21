package Service;

import Dao.*;
import UtilityClasses.Goods;
import UtilityClasses.Pair;
import UtilityClasses.Shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Service {
    private Dao dao;
    private HashMap<Goods, Integer> purchasedGoods = new HashMap<Goods, Integer>();

    public Service(boolean mode, String url, String driver) throws Exception {
        if (driver.contains("microsoft.sqlserver.jdbc")) {
            dao = new DaoMSQL(mode, url, driver);
        }
        else {
            throw new Exception("Unsupported DB");
        }
    }


    public HashMap<Goods, Integer> getYourGoods() {
        return purchasedGoods;
    }
    public Shop getShopById(int id) {
        return dao.getShops().get(id);
    }
    public void createBackup() {
        dao.createBackup();
    }
    public void applyBackup() {
        dao.applyBackup();
    }
    public ArrayList<Shop> searchByGoods(String goodsName) {
        return dao.searchByGoods(goodsName);
    }

    public HashMap<String, Goods> searchByShop(String shopName) {
        return dao.searchByShop(shopName);
    }

    public HashMap<Integer, Shop> getShops() {
        return dao.getShops();
    }
    public Shop getShop (String shopName) {
        return dao.getShop(shopName);
    }

    public ArrayList<HashMap<String, Goods>> getGoods() {
        HashMap<Integer, Shop> shops = dao.getShops();
        ArrayList<HashMap<String, Goods>> allGoods = new ArrayList<HashMap<String, Goods>>();
        for (int i : shops.keySet()) {
            Shop shop = shops.get(i);
            HashMap<String, Goods> goods = shop.getStorage();
            allGoods.add(goods);
        }
        return allGoods;
    }
    public void createShop(String str1, String str2) {
        dao.createShop(new Shop(Integer.parseInt(str1), str2));
    }
    public boolean createGoods(String str1, String str2, String str3, String str4 ) {
        boolean flag = dao.createGoods(str1, str2, Integer.parseInt(str3), Float.parseFloat(str4));
        return flag;
    }

    /**
     * Legacy code
     */

    public String getCheapest(String str) {
        return dao.getCheapest(str);
    }

    public String getGoodsByBudget(String str) {
        String line[]=str.split(",");
        return dao.getGoodsByBudget(Integer.parseInt(line[0]), Float.parseFloat(line[1]));
    }

    public float getShipment(int shopId, HashMap<String,Integer> shipment) {
        return dao.getShipment(shopId, shipment);
    }
    public int getAverageCheapest(HashMap<String,Integer> shipment) {
        return dao.getAverageCheapest(shipment);
    }

    public float buyGoods(String goodsName, String goodsShop, int quantity) {
        return dao.buyGoods(goodsName, goodsShop, quantity);
    }
    public boolean confirmOperation(String goodsName, String goodsShop, int quantity) {
        Pair response = dao.confirmOperation(goodsName, goodsShop, quantity);
        purchasedGoods.put(response.getValue(), quantity);
        System.out.println(purchasedGoods);
        return response.getKey();
    }
}
