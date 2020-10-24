import Service.Service;
import UtilityClasses.Shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String args[]) throws Exception {

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
        if (parameters.get(3).equals("true")) {
            GraphicalInterface.createGUI(service);
        }
        else {
            scanner = new Scanner(System.in);
            System.out.println("Добро пожаловать в наш магазин. Для работы, введите номер выбранного пункта в консоль.");
            while(true) {
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("1.Вывести список магазинов с товарами");
                System.out.println("2.Найти желаемый товар в магазинах");
                System.out.println("3.Приобрести товар");
                System.out.println("4.Добавить магазин");
                System.out.println("5.Добавить товар в магазин");
                System.out.println("Q - для выхода из системы.");
                System.out.println("----------------------------------------------------------------------------------");
                String input = scanner.nextLine();
                if (input.equals("Q")) {
                    break;
                }
                else {
                    try {
                        String name, shopName, goodsName;
                        switch (Integer.parseInt(input)) {
                            case(1):
                                HashMap<Integer, Shop> hM = service.getShops();
                                for (Shop x : hM.values()) {
                                    System.out.println(x);
                                }
                                break;
                            case(2):
                                input = scanner.nextLine();
                                ArrayList<Shop> arr = service.searchByGoods(input);
                                if (arr.size() == 0) {
                                    System.out.println("К сожалению, в магазинах нашей системы нет товара " + input);
                                    break;
                                }
                                for (Shop x : arr) {
                                    System.out.println(
                                            x.getName()
                                            + ", цена:"
                                            + x.getGoodsPrice(input)
                                            + ", количество:"
                                            + x.getGoodsQuantity(input));
                                }
                                break;
                            case(3):
                                System.out.println("Введите название товара");
                                goodsName = scanner.nextLine();
                                System.out.println("Введите название магазина");
                                shopName = scanner.nextLine();
                                System.out.println("Введите количество");
                                int quantity = Integer.parseInt(scanner.nextLine());
                                float price = service.getGoodsPrice(goodsName, shopName, quantity);
                                if (price == -1.0) {
                                    System.out.println("Товара " + goodsName + " нет в " + shopName);
                                }
                                else {
                                    if (price == -2.0) {
                                        System.out.println("В системе нет такого магазина:" + shopName);
                                    }
                                    else {
                                        System.out.println("Итоговая цена за " + quantity + " " + goodsName +":" + price);
                                        System.out.print("Подтвердить операцию? Y");
                                        if (scanner.nextLine().equals("Y")) {
                                            service.BuyGoods(goodsName, shopName, quantity);
                                            System.out.println("С вас " + price + " уе");
                                        }
                                        else {
                                            System.out.println("Отмена операции");
                                        }
                                    }
                                }
                                break;
                            case(4):
                                System.out.println("Для создания магазина, введи сначала айди, затем введите название, через ентер");
                                String id = scanner.nextLine();
                                while (service.getShops().containsKey(Integer.parseInt(id))) {
                                    System.out.println("Этот айди занят, выберите другой");
                                    id = scanner.nextLine();
                                };
                                name = scanner.nextLine();
                                service.createShop(id, name);
                                System.out.println("Успешно создан магазин " + name + " с айди " + id);
                                break;
                            case(5):
                                System.out.println("Введи название магазина, название товара, количество и цену за штуку, через ентер");
                                shopName = scanner.nextLine();
                                if (service.getShop(shopName) == null) {
                                    System.out.println("В системе нет такого магазина " + shopName);
                                }
                                else {
                                    goodsName = scanner.nextLine();
                                    String quantityS = scanner.nextLine();
                                    String priceS = scanner.nextLine();
                                    service.createGoods(goodsName, shopName, quantityS, priceS);
                                    System.out.println("Успешно создан товар " + goodsName
                                            + " в количестве " + quantityS
                                            + " по цене " + priceS
                                            + " в магазине " + shopName
                                    );
                                }
                                break;
                            default:
                                System.out.println("Неизвестная команда. Список команд:");
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода. Повторите запрос.");
                    }
                }
            }
        }

        //графический интерфейс
        //пример того как не надо прогать
//        service.createBackup();
//        service.confirmOperation("сигареты ПП1", "Ашан", 5);
//        Scanner input = new Scanner(System.in);
//        String cont = input.nextLine();
//        while(!cont.equals(" ")) {
//            cont = input.nextLine();
//        }
//        service.applyBackup();
//        System.out.println("SSSSS");

    }
}

   