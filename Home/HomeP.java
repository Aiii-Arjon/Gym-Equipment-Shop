package Home;
import Accounts.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HomeP extends JFrame implements ActionListener, MouseListener {

    JButton backBtn, cartBtn, exitBtn;
    JButton[] buyButtons = new JButton[10];
    JSpinner[] quantitySpinners = new JSpinner[10];
    JPanel productPanel;
    ArrayList<CartItem> cart = new ArrayList<>();

    String[] productNames = {
        "Dumbbell Set", "Treadmill", "Exercise Bike", "Yoga Mat", "Kettlebell",
        "Pull-Up Bar", "Bench Press", "Rowing Machine", "Smith Machine", "Resistance Bands"
    };

    String[] productPrices = {
        "350", "800", "700", "80", "120",
        "60", "1500", "700", "2000", "15"
    };

    String[] productImages = {
        "./logpic/p1.jpg", "./logpic/p2.jpg", "./logpic/p3.jpg", "./logpic/p4.jpg", "./logpic/p5.jpg",
        "./logpic/p6.jpg", "./logpic/p7.jpg", "./logpic/p8.jpg", "./logpic/p9.jpg", "./logpic/p10.jpg"
    };

    public HomeP() {
        super("Gym Equipment Store");
        this.setSize(1100, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);  // absolute layout like FrameDemo

        // Top banner
        JLabel titleLabel = new JLabel("Gym Equipment Store", JLabel.CENTER);
        titleLabel.setBounds(0, 0, 1100, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.DARK_GRAY);
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);

        // Product panel
        productPanel = new JPanel();
        productPanel.setLayout(null);
        productPanel.setBounds(20, 60, 1050, 550);

        int x = 20, y = 20;
        for (int i = 0; i < 10; i++) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(null);
            itemPanel.setBounds(x, y, 200, 250);
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Product image
            ImageIcon icon = new ImageIcon(productImages[i]);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setBounds(25, 10, 150, 150);
            itemPanel.add(imgLabel);

            // Name and price
            JLabel namePriceLabel = new JLabel(productNames[i] + " - $" + productPrices[i]);
            namePriceLabel.setBounds(10, 170, 180, 25);
            itemPanel.add(namePriceLabel);

            // Quantity spinner
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            spinner.setBounds(10, 200, 60, 25);
            quantitySpinners[i] = spinner;
            itemPanel.add(spinner);

            // Buy button
            JButton buyBtn = new JButton("Buy");
            buyBtn.setBounds(80, 200, 100, 25);
            buyBtn.addActionListener(this);
            buyBtn.setActionCommand(String.valueOf(i));
            buyButtons[i] = buyBtn;
            itemPanel.add(buyBtn);

            productPanel.add(itemPanel);

            x += 210;
            if ((i + 1) % 5 == 0) {
                x = 20;
                y += 260;
            }
        }

        this.add(productPanel);

        // Bottom buttons
        backBtn = new JButton("Back to Login");
        backBtn.setBounds(300, 630, 150, 40);
        backBtn.addActionListener(this);
        backBtn.addMouseListener(this);
        this.add(backBtn);

        cartBtn = new JButton("View Cart");
        cartBtn.setBounds(470, 630, 150, 40);
        cartBtn.addActionListener(this);
        cartBtn.addMouseListener(this);
        this.add(cartBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBounds(640, 630, 150, 40);
        exitBtn.addActionListener(this);
        exitBtn.addMouseListener(this);
        this.add(exitBtn);
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();

        // Buy buttons
        for (int i = 0; i < buyButtons.length; i++) {
            if (src == buyButtons[i]) {
                int qty = (int) quantitySpinners[i].getValue();
                int price = Integer.parseInt(productPrices[i]);
                cart.add(new CartItem(productNames[i], price, qty));
                JOptionPane.showMessageDialog(this, "Added to cart:\n" + productNames[i] + " x " + qty);
                return;
            }
        }

        // Bottom buttons
        if (src == backBtn) {
            this.dispose();
            new GES().setVisible(true);
        } else if (src == cartBtn) {
            showCart();
        } else if (src == exitBtn) {
            System.exit(0);
        }
    }

    void showCart() {
        JFrame cartFrame = new JFrame("Cart");
        cartFrame.setSize(400, 400);
        cartFrame.setLayout(null);

        int y = 20;
        int total = 0;
        for (CartItem item : cart) {
            JLabel lbl = new JLabel(item.name + " x " + item.quantity + " = $" + (item.price * item.quantity));
            lbl.setBounds(20, y, 350, 25);
            cartFrame.add(lbl);
            total += item.price * item.quantity;
            y += 30;
        }

        JLabel totalLbl = new JLabel("Total: $" + total);
        totalLbl.setBounds(20, y + 10, 350, 25);
        cartFrame.add(totalLbl);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(150, y + 50, 100, 30);
        closeBtn.addActionListener(this);
        closeBtn.setActionCommand("CloseCart");
        cartFrame.add(closeBtn);

        cartFrame.setVisible(true);
    }

    public void mouseClicked(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {
        if (me.getSource() == backBtn || me.getSource() == cartBtn || me.getSource() == exitBtn) {
            ((JButton) me.getSource()).setBackground(Color.BLUE);
            ((JButton) me.getSource()).setForeground(Color.WHITE);
        }
    }
    public void mouseExited(MouseEvent me) {
        if (me.getSource() == backBtn || me.getSource() == cartBtn || me.getSource() == exitBtn) {
            ((JButton) me.getSource()).setBackground(null);
            ((JButton) me.getSource()).setForeground(Color.BLACK);
        }
    }

    class CartItem {
        String name;
        int price;
        int quantity;
        CartItem(String n, int p, int q) {
            name = n;
            price = p;
            quantity = q;
        }
    }

}

