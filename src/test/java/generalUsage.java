import Service.Service;
import UtilityClasses.Shop;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

@Test()
public class generalUsage {

    @DataProvider
    public Object[][] data() {
        return new String[][] {new String[] {"data1"}};
    }


    @Test(dataProvider = "data")
    public void testNoBackup(String d) throws Exception {
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
        System.out.println("Shops:\n" + Shops.toString());
        System.out.println("--------------------------------------------------");
        System.out.println("service.getShops():\n" + service.getShops().toString());
        Assert.assertNotEquals(service.getShops(), Shops);
        service.applyBackup();
    }

    @Test(dataProvider = "data")
    public void testBackup(String d) throws Exception {
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
        System.out.println("Shops:\n" + Shops.toString());
        System.out.println("--------------------------------------------------");
        System.out.println("service.getShops():\n" + service.getShops().toString());
        Assert.assertEquals(service.getShops(), Shops);
    }


}