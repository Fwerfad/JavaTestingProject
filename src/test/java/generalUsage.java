import Service.Service;
import UtilityClasses.Shop;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

@Test()
public class generalUsage {
    @DataProvider
    public Object[][] Goods() {
        return new String[][] {new String[] {"пачка пельменей,Ашан,5,180"}, new String[] {"пачка макарон,H&H,5,60"}};
    }
    @DataProvider
    public Object[][] Shop() {
        return new String[][] {new String[] {"H*H"}, new String[] {"HH"}, new String[] {"HHH"}};
    }


    @Test()
    public void testNoBackup() throws Exception {
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\ini.property"));
        Scanner scanner = new Scanner(file);
        ArrayList<String> parameters = new ArrayList<String>();
        while(scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            Pattern pattern = Pattern.compile("!");
            String[] strings = pattern.split(parsedString);
            parameters.add(strings[1]);
        }
        final Service service = new Service(Boolean.parseBoolean(parameters.get(0)), parameters.get(1), parameters.get(2));
        service.createBackup();
        HashMap<Integer, Shop> Shops = (HashMap<Integer, Shop>) service.getShops().clone();
        service.createShop("11", "2H&H");
        service.createGoods("testGoods", "2H&H", "10", "100.0");
        Assert.assertNotEquals(service.getShops(), Shops);
        service.applyBackup();
    }

    @Test
    public void testBackup() throws Exception {
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\ini.property"));
        Scanner scanner = new Scanner(file);
        ArrayList<String> parameters = new ArrayList<String>();
        while(scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            Pattern pattern = Pattern.compile("!");
            String[] strings = pattern.split(parsedString);
            parameters.add(strings[1]);
        }
        final Service service = new Service(Boolean.parseBoolean(parameters.get(0)), parameters.get(1), parameters.get(2));
        service.createBackup();
        HashMap<Integer, Shop> Shops = (HashMap<Integer, Shop>) service.getShops().clone();
        service.createShop("10", "2H&H");
        service.createGoods("testGoods", "2H&H", "10", "100.0");
        service.applyBackup();
        Assert.assertEquals(service.getShops(), Shops);
    }

    @Test(dataProvider = "Shop")
    public void testAddShopToDB(String d) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\ini.property"));
        Scanner scanner = new Scanner(file);
        ArrayList<String> parameters = new ArrayList<String>();
        while(scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            Pattern pattern = Pattern.compile("!");
            String[] strings = pattern.split(parsedString);
            parameters.add(strings[1]);
        }
        final Service service = new Service(Boolean.parseBoolean(parameters.get(0)), parameters.get(1), parameters.get(2));
        service.createShop("900", d);
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;database=prog_project;integratedSecurity=true";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultShopsSet = statement.executeQuery("SELECT * from Shops");
        boolean flag = false;
        while (resultShopsSet.next()) {
            if (resultShopsSet.getString(2).split(" {2}")[0].equals(d))
                flag = true;
        }
        statement.close();
        connection.close();
        service.applyBackup();
        Assert.assertTrue(flag);
    }
    @Test(dataProvider = "Goods")
    public void testAddGoodsToDB(String d) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader("src\\main\\resources\\ini.property"));
        Scanner scanner = new Scanner(file);
        ArrayList<String> parameters = new ArrayList<String>();
        while(scanner.hasNextLine()) {
            String parsedString = scanner.nextLine();
            Pattern pattern = Pattern.compile("!");
            String[] strings = pattern.split(parsedString);
            parameters.add(strings[1]);
        }
        final Service service = new Service(Boolean.parseBoolean(parameters.get(0)), parameters.get(1), parameters.get(2));
        String[] str = d.split(",");
        service.createGoods(str[0], str[1], str[2], str[3]);
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost;database=prog_project;integratedSecurity=true";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultShopsSet = statement.executeQuery("SELECT * from Goods");
        boolean flag = false;
        while (resultShopsSet.next()) {
            if (resultShopsSet.getString(1).split("  ")[0].equals(str[0])) {
                flag = true;
                break;
            }
        }
        statement.close();
        connection.close();
        service.applyBackup();
        Assert.assertTrue(flag);
    }
}