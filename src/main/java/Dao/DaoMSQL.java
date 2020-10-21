package Dao;

import UtilityClasses.Goods;
import UtilityClasses.Pair;
import UtilityClasses.Shop;
import Dao.Dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DaoMSQL implements Dao{
    private HashMap<Integer, Shop> shops;
    private boolean mode;
    private String url;
    private String driver;

    public DaoMSQL(boolean mode, String url, String driver) throws IOException {
        shops = new HashMap<Integer, Shop>();
        this.mode = mode;
        this.url = url;
        this.driver = driver;
        if (!mode) {
            this.insertShops();
            this.insertGoods();
        }
        else {
            try {
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                ResultSet resultShopsSet = statement.executeQuery("SELECT * from Shops");
                while (resultShopsSet.next()) {
                    Shop shop = new Shop(resultShopsSet.getInt(1), resultShopsSet.getString(2).split("  ")[0]);
                    shops.put(shop.getId(), shop);
                }
                ResultSet resultGoodsSet = statement.executeQuery(("SELECT * FROM Goods"));
                while (resultGoodsSet.next()) {
                    Goods goods = new Goods(resultGoodsSet.getString(1).split("  ")[0], resultGoodsSet.getInt(4), resultGoodsSet.getInt(2), resultGoodsSet.getFloat(3));
                    shops.get(resultGoodsSet.getInt(4)).insertGoods(goods);
                }
                resultShopsSet.close();
                resultGoodsSet.close();
                statement.close();
                connection.close();
                this.createBackup();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertShops() throws IOException {
        Pattern delimeter = Pattern.compile(",");
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\Shops.csv"));
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            String[] parameters = delimeter.split(parsedString);
            Shop shop = new Shop(Integer.parseInt(parameters[0]), parameters[1]);
            shops.put(shop.getId(), shop);
        }
    }

    private void insertGoods() throws IOException {
        Pattern delimiter = Pattern.compile(",");
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\Goods.csv"));
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            String[] parameters = delimiter.split(parsedString);
            shops.get(Integer.parseInt(parameters[1])).insertGoods(new Goods(parameters[0], Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Float.parseFloat(parameters[3])));
        }
    }

    public void createShop(Shop shop) {
        if (mode) {
            try {
                Class.forName(driver);
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                String sqlQuery = "insert into Shops VALUES (" + shop.getId() + ",'" + shop.getName()  + "')";
                statement.executeUpdate(sqlQuery);
                statement.close();
                connection.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        shops.put(shop.getId(), shop);
    }
    public Shop getShop(String shopName) {
        for (Shop shop : shops.values()) {
            if (shop.getName().equals(shopName)) {
                return shop;
            }
        }
        return null;
    }

    public boolean createGoods(String name, String shopName, int quantity, float price) {
        boolean flag = false;
        int shopId = -1;
        for (int i : shops.keySet()) {
            if (shops.get(i).getName().equals(shopName)) {
                shopId = i;
            }
        }
        if (shopId != 1) {
            Goods goods = new Goods(name, shopId, quantity, price);
            if (mode) {
                try {
                    Class.forName(driver);
                    Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();
                    String sqlQuery = "insert into Goods VALUES ('" + goods.getName() + "'," + goods.getQuantity() + "," + goods.getPrice() + "," + goods.getShopId() + ")";
                    statement.executeUpdate(sqlQuery);
                    statement.close();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            shops.get(goods.getShopId()).insertGoods(goods);
            flag = true;
        }
        return flag;
    }

    public HashMap<Integer, Shop> getShops() {
        return shops;
    }

    public void createBackup() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sqlQuery = "TRUNCATE TABLE  Shops_backup";
            statement.executeUpdate(sqlQuery);
            sqlQuery = "TRUNCATE TABLE  Goods_backup";
            statement.executeUpdate(sqlQuery);
            for (int i : shops.keySet()) {
                Shop shop = shops.get(i);
                sqlQuery = "insert into Shops_backup VALUES (" + shop.getId() + ",'" + shop.getName()  + "')";
                statement.executeUpdate(sqlQuery);
                for (String j: shop.getStorage().keySet()) {
                    Goods goods = shop.getStorage().get(j);
                    sqlQuery = "insert into Goods_backup VALUES ('" + goods.getName() + "'," + goods.getQuantity() + "," + goods.getPrice() + "," + goods.getShopId() + ")";
                    statement.executeUpdate(sqlQuery);
                }
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Goods> searchByShop(String shopName) {
        HashMap<String, Goods> resultArray = new HashMap<String, Goods>();
        for (int i : shops.keySet()) {
            Shop shop = shops.get(i);
            if (shop.getName().equals(shopName)) {
                resultArray = shop.getStorage();
                break;
            }
        }
        return resultArray;
    }

    public ArrayList<Shop> searchByGoods(String goodsName) {
        ArrayList<Shop> resultArray = new ArrayList<Shop>();
        for (int i : shops.keySet()) {
            Shop shop = shops.get(i);
            for (String j : shop.getStorage().keySet())
                if (goodsName.equals(j)) {
                    resultArray.add(shop);
                }
        }
        return resultArray;
    }

    public float buyGoods(String goodsName, String goodsShop, int quantity) {
        float sum = 0;
        for (int i : shops.keySet()) {
            Shop shop = shops.get(i);
            if (shop.getName().equals(goodsShop)) {
                for (String name : shop.getStorage().keySet()) {
                    Goods goods = shop.getStorage().get(name);
                    if (goods.getName().equals(goodsName)) {
                        if (goods.getQuantity() >= quantity) {
                            sum = quantity * goods.getPrice();
                            break;
                        }
                        else {
                            sum = -3;
                        }
                    }
                    else {
                        sum = -1;
                    }
                }
            }
            if (sum != 0) {
                break;
            }
            else {
                sum = -2;
            }
        }
        return sum;
    }

    public Pair confirmOperation(String goodsName, String goodsShop, int quantity) {
        Pair response = new Pair(false, null);
        for (int i : shops.keySet()) {
            Shop shop = shops.get(i);
            if (shop.getName().equals(goodsShop)) {
                for (String name : shop.getStorage().keySet()) {
                    Goods goods = shop.getStorage().get(name);
                    if (goods.getName().equals(goodsName)) {
                        if (goods.getQuantity() >= quantity) {
                            response = new Pair(true, goods);
                            goods.decreaseQuantity(quantity);
                            try {
                                Class.forName(driver);
                                Connection connection = DriverManager.getConnection(url);
                                Statement statement = connection.createStatement();
                                String sqlQuery = "update Goods set quantity = quantity - " + quantity + " where name = '" + goodsName + "' and shopId = " + shop.getId();
                                statement.executeUpdate(sqlQuery);
                                statement.close();
                                connection.close();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return response;
    }
    public void applyBackup() {
        shops = new HashMap<Integer, Shop>();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultShopsSet = statement.executeQuery("SELECT * from Shops_backup");
            while (resultShopsSet.next()) {
                Shop shop = new Shop(resultShopsSet.getInt(1), resultShopsSet.getString(2).split("  ")[0]);
                shops.put(shop.getId(), shop);
            }
            ResultSet resultGoodsSet = statement.executeQuery(("SELECT * FROM Goods_backup"));
            while (resultGoodsSet.next()) {
                Goods goods = new Goods(resultGoodsSet.getString(1).split("  ")[0], resultGoodsSet.getInt(4), resultGoodsSet.getInt(2), resultGoodsSet.getFloat(3));
                shops.get(resultGoodsSet.getInt(4)).insertGoods(goods);
            }
            resultShopsSet.close();
            resultGoodsSet.close();
            statement.close();
            connection.close();
            this.updateDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateDB() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            String sqlQuery = "TRUNCATE TABLE  Shops";
            statement.executeUpdate(sqlQuery);
            sqlQuery = "TRUNCATE TABLE  Goods";
            statement.executeUpdate(sqlQuery);
            for (int i : shops.keySet()) {
                Shop shop = shops.get(i);
                sqlQuery = "insert into Shops VALUES (" + shop.getId() + ",'" + shop.getName()  + "')";
                statement.executeUpdate(sqlQuery);
                for (String j: shop.getStorage().keySet()) {
                    Goods goods = shop.getStorage().get(j);
                    sqlQuery = "insert into Goods VALUES ('" + goods.getName() + "'," + goods.getQuantity() + "," + goods.getPrice() + "," + goods.getShopId() + ")";
                    statement.executeUpdate(sqlQuery);
                }
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Legacy code
     */
    public String getCheapest(String name) {
        String shopName = null;
        float cheapest = Float.MAX_VALUE;
        for ( Shop shop : shops.values()) {
            float current = shop.getGoodsPrice(name);
            if (current != -1)
                if (cheapest > current)  {
                    cheapest = current;
                    shopName = shop.getName();
                }
        }
        return shopName;
    }

    public String getGoodsByBudget(int shopId, float cash) {
        if (shops.get(shopId) != null)
            return shops.get(shopId).getGoodsByBudget(cash);
        return null;
    }

    public float getShipment(int shopId, HashMap<String,Integer> shipment) {
        if (shops.get(shopId) != null)
            return shops.get(shopId).getShipment(shipment);
        return -1;
    }
    public int getAverageCheapest(HashMap<String,Integer> shipment) {
        float minPrice = Float.MAX_VALUE;
        int Id = -1;
        for (Shop shop : shops.values()) {
            float current = shop.getShipment(shipment);
            if (current != -1)
                if (minPrice > current) {
                    minPrice = current;
                    Id = shop.getId();
                }
        }
        return Id;
    }

}