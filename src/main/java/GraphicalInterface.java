import UtilityClasses.Goods;
import UtilityClasses.Shop;
import Service.Service;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphicalInterface {
    static TextField field = new TextField("Введите что-нибудь");
    static Service serv = null;
    public static void createGUI(Service service) {
        serv = service;
        JFrame frame = new JFrame("Управление");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font fontPlain = new Font("Courier", Font.PLAIN, 40);
        JLabel label = new JLabel("Управление");
        label.setBackground(new Color(255, 255, 255));
        label.setFont(fontPlain);
        label.setOpaque(true);
        JLabel chooseLabel = new JLabel("Выберите операцию");
        chooseLabel.setOpaque(true);
        chooseLabel.setFont(fontPlain);
        chooseLabel.setBackground(new Color(255, 255, 255));
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel add = new JPanel();
        panel2.add(label);
        JPanel panel3 = new JPanel();
        panel3.add(chooseLabel);
        JButton button = new JButton(); //добавление магазина
        button.setIcon(new ImageIcon("src//main//resources//image2.png"));
        button.setPreferredSize(new Dimension(25, 25));
        button.addActionListener(new ListenerAction());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        JButton button2 = new JButton(); //добавление товара
        button2.setIcon(new ImageIcon("src//main//resources//image2.png"));
        button2.setPreferredSize(new Dimension(25, 25));
        button2.addActionListener(new ListenerAction.ListenerAction1());
        button2.setBorderPainted(false);
        button2.setContentAreaFilled(false);

        TextField field = new TextField("Поиск по");
        field.setVisible(true);
        ButtonGroup group = new ButtonGroup();
        JRadioButton searchShop = new JRadioButton("Магазинам", false);
        group.add(searchShop);
        JRadioButton searchItem = new JRadioButton("Товарам", true);
        group.add(searchItem);

        JButton button4 = new JButton();
        button4.setIcon(new ImageIcon("src//main//resources//search.jpg"));
        button4.setPreferredSize(new Dimension(25, 25));
        button4.setBorderPainted(false);
        button4.setContentAreaFilled(false);
        button4.addActionListener(new ListenerAction.ListenerAction5(searchShop, field));
        Font fontPlainMenu = new Font("Courier", Font.PLAIN, 30);
        JButton buttonShop = new JButton("Магазины");
        buttonShop.addActionListener(new ListenerAction.ListenerAction6());
        buttonShop.setOpaque(true);
        buttonShop.setContentAreaFilled(false);
        buttonShop.setFont(fontPlainMenu);
        buttonShop.setBorderPainted(false);
        buttonShop.setContentAreaFilled(false);
        buttonShop.setBackground(new Color(255, 255, 255));
        JButton buttonItem = new JButton("Мои товары");
        buttonItem.addActionListener(new ListenerAction.ListenerAction7());
        buttonItem.setOpaque(true);
        buttonItem.setContentAreaFilled(false);
        buttonItem.setFont(fontPlainMenu);
        buttonItem.setBorderPainted(false);
        buttonItem.setContentAreaFilled(false);


        JButton buy = new JButton("<html> <u> Оформить заказ сейчас </u> </html>");
        buy.addActionListener(new ListenerAction.ListenerAction4());
        buy.setOpaque(true);
        buy.setContentAreaFilled(false);
        buy.setFont(fontPlain);
        buy.setBorderPainted(false);
        buy.setContentAreaFilled(false);
        Font fontPlainSmall = new Font("Courier", Font.PLAIN, 20);
        buy.setFont(fontPlainSmall);
        buttonShop.setBackground(new Color(255, 255, 255));
        JPanel head = new JPanel();
        GridLayout layout = new GridLayout(3, 0);
        head.setLayout(layout);
        head.setBackground(new Color(255, 255, 255));
        head.add(panel2);
        head.add(panel3);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.setBackground(new Color(255, 255, 255));
        flow.add(field);
        flow.add(searchShop);
        flow.add(searchItem);
        flow.add(button4);
        head.add(flow);
        add.add(head);

        GridLayout layout2 = new GridLayout(3, 2, 10, 10);
        JPanel menu = new JPanel();
        menu.setLayout(layout2);
        menu.setBackground(new Color(255, 255, 255));
        add.setBackground(new Color(255, 255, 255));
        menu.add(buttonShop, BorderLayout.EAST);
        menu.add(button);
        menu.add(buttonItem, BorderLayout.EAST);
        menu.add(button2);

        add.add(menu, BorderLayout.CENTER);
        add.add(panel);
        JPanel down = new JPanel();
        down.add(buy);
        down.setBackground(new Color(255, 255, 255));
        add.add(down);

        frame.add(add);


        frame.setSize(600, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    static class ListenerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JDialogTest dialog = new JDialogTest();
            dialog.addShop();
        }


        public static class JDialogTest extends JFrame {
            private static final long serialVersionUID = 1L;

            public JDialogTest() {

            }


            public void addShop() {
                // super("Создание магазина");
                // Выход из программы при закрытии
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                // Кнопки для создания диалоговых окон

                JButton button1 = new JButton("Сoздать");
                JButton button2 = new JButton("Отменить");
                final TextField name = new TextField("Название");
                final TextField id = new TextField("Введите ID");
                // Создание панели содержимого с размещением кнопок
                JPanel contents = new JPanel();
                contents.add(name);
                contents.add(id);
                contents.add(button2);
                contents.add(button1);
                setContentPane(contents);
                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        String str = name.getText().trim();
                        String str2 = id.getText().trim();
                        serv.createShop(str2, str);
                    }
                });
                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

                // Определение размера и открытие окна
                setSize(250, 100);
                setVisible(true);
            }

            public void addItem() {
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                // Кнопки для создания диалоговых окон


                JButton button1 = new JButton("Сoздать");
                JButton button2 = new JButton("Отменить");
                JLabel addShop = new JLabel("Добавление магазина");
                addShop.setFont(new Font("Courier", Font.PLAIN, 25));
                JPanel up = new JPanel();
                up.add(addShop);
                final TextField name = new TextField("Название товара");
                final TextField amount = new TextField("Количество");
                final TextField prize = new TextField("Цена");
                final TextField shopName = new TextField("Название магазина");
                // Создание панели содержимого с размещением кнопок
                JLabel labelName = new JLabel("Название товара");
                JLabel labelAmount = new JLabel("Количество");
                JLabel labelPrize = new JLabel("Цена");
                JLabel labelShopName = new JLabel("Название магазина");
                JPanel contents = new JPanel();
                contents.add(up);
                contents.add(labelName);
                contents.add(name);
                contents.add(labelAmount);
                contents.add(amount);
                contents.add(labelPrize);
                contents.add(prize);
                contents.add(labelShopName);
                contents.add(shopName);
                contents.add(button2);
                contents.add(button1);
                setContentPane(contents);
                // Определение размера и открытие окна
                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        String str = name.getText().trim();
                        String str3 = amount.getText().trim();
                        String str4 = prize.getText().trim();
                        String str2 = shopName.getText().trim();
                        serv.createGoods(str, str2, str3, str4);

                    }
                });
                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

                // Определение размера и открытие окна
                setSize(300, 210);
                setVisible(true);
            }


            public void order() {
                // super("Создание магазина");
                // Выход из программы при закрытии
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                // Кнопки для создания диалоговых окон

                JButton button1 = new JButton("Заказать");
                JButton button2 = new JButton("Отменить");
                JLabel labelName = new JLabel("Название товара");
                JLabel labelAmount = new JLabel("Количество");
                JLabel labelShop = new JLabel("Название магазина");
                final TextField name = new TextField("Введите название товара");
                final TextField shop = new TextField("Введите название магазина");
                final TextField number = new TextField("Введите название магазина");
                // Создание панели содержимого с размещением кнопок
                JPanel contents = new JPanel();

                contents.add(labelName);
                contents.add(name);
                contents.add(labelShop);
                contents.add(shop);
                contents.add(labelAmount);
                contents.add(number);
                contents.add(button2);
                contents.add(button1);
                setContentPane(contents);
                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JDialog dialog = createDialog(name, shop, number, true);
                        dialog.setVisible(true);
                        dispose();
                        serv.getGoodsPrice(name.getText(), shop.getText(), Integer.parseInt(number.getText()));
                    }
                });
                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });


                // Определение размера и открытие окна
                setSize(250, 250);
                setVisible(true);
            }
            public void showShop() {
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                HashMap<Integer, Shop> result = serv.getShops();
                JPanel panel = new JPanel();
                for (int id : result.keySet()) {
                    JLabel item = new JLabel();
                    item.setText(result.get(id).toString());
                    item.setBackground(new Color(255, 255, 255));
                    item.setOpaque(true);
                    panel.add(item);
                }
                setContentPane(panel);
                setSize(250, 250);
                setVisible(true);
            }
            public void showItems() {
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel();
                for (Goods goods : serv.getYourGoods().keySet()) {
                    JLabel item = new JLabel();
                    item.setText(goods.getName() + " в количестве " + serv.getYourGoods().get(goods));
                    item.setBackground(new Color(255, 255, 255));
                    item.setOpaque(true);
                    panel.add(item);
                }
                setContentPane(panel);
                setSize(250, 250);
                setVisible(true);
            }


            public void search(JRadioButton state, TextField field) {
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setSize(300, 210);
                ArrayList<JLabel> label = new ArrayList<JLabel>();;

                JPanel contents = new JPanel();

                if (state.isSelected()) {//magaz
                    HashMap<String, Goods> result = serv.searchByShop(field.getText());
                    for (String i : result.keySet()) {
                        JLabel item = new JLabel(result.get(i).toString());
                        item.setBackground(new Color(255, 255, 255));
                        item.setOpaque(true);
                        label.add(item);
                    }
                }
                else {//goods
                    ArrayList<Shop> result = serv.searchByGoods(field.getText());
                    for (Shop shop : result) {
                        JLabel item = new JLabel(shop.getName() + " в количестве " + shop.getStorage().get(field.getText()).getQuantity());
                        item.setBackground(new Color(255, 255, 255));
                        item.setOpaque(true);
                        label.add(item);
                    }
                }
                for (JLabel i : label ) {
                    contents.add(i);
                }
                setContentPane(contents);
                setVisible(true);
            }


            /**
             * Функция создания диалогового окна.
             *
             * @param modal - флаг модальности
             */
            private JDialog createDialog(final TextField str, final TextField str2, final TextField str3, boolean modal) {
                JDialog dialog = new JDialog(this, modal);
                dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel panel = new JPanel();
                JButton button1 = new JButton("Заказать");
                JButton button2 = new JButton("Отменить");
                button1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        serv.BuyGoods(str.getText(),str2.getText(),Integer.parseInt(str3.getText()));
                        dispose();
                    }
                });
                button2.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                JLabel label = new JLabel("Хотите подтвердить заказ?");
                panel.add(label);
                panel.add(button1);
                panel.add(button2);
                dialog.add(panel);
                dialog.setSize(250, 100);
                return dialog;
            }
        }




        static class ListenerAction1 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JDialogTest dialog = new JDialogTest();
                dialog.addItem();
            }
        }
        static class ListenerAction4 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JDialogTest dialog = new JDialogTest();
                dialog.order();
            }
        }
        static class ListenerAction5 implements ActionListener {
            private JRadioButton state;
            private TextField field;
            public ListenerAction5(JRadioButton searchShop, TextField field) {
                state = searchShop;
                this.field = field;
            }

            public void actionPerformed(ActionEvent e) {
                JDialogTest dialog = new JDialogTest();
                dialog.search(state, field);
            }
        }
        static class ListenerAction6 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JDialogTest dialog = new JDialogTest();
                dialog.showShop();
            }
        }
        static class ListenerAction7 implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JDialogTest dialog = new JDialogTest();
                dialog.showItems();
            }
        }
    }
}
