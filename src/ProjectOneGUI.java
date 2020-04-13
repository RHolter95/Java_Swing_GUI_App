import javax.swing.*;
import java.awt.event.*;

public class ProjectOneGUI extends JFrame {

    private JPanel mainPanel;
    public JButton QuitOrderButton;
    public JButton processItemButton;
    public JButton finishOrderButton;
    public JButton newOrderButton1;
    public JButton confirmItemButton;
    public JButton viewOrderButton;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JTextField textField4;
    public JTextField textField5;
    public JLabel orderItemNumber;
    public JLabel orderItemNumber2;
    public JLabel orderItemNumber3;
    public JLabel orderItemNumber4;
    public JButton submitButton;
    public JButton submitButton1;
    public JButton submitButton2;



    public int orderSize = 0;
    public String title = "";
    public int width = 0;
    public int height = 0;
    public int id = 0;
    public int bookID = 0;
    public int bookQuantity = 0;
    public boolean popBox = true;
    public boolean authSearch = false;
    public boolean confirmItem = false;
    public boolean viewOrder = false;
    public boolean printRecipt = false;
    public boolean newOrder = false;
    public boolean disableButtons = false;

    public ProjectOneGUI(String _title, Integer _width, Integer _height){
    super(_title);
    title = _title;
    width = _width;
    height = _height;



    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(mainPanel);
    this.pack();

        // region EnterListener

        //Wipe preset text fields on click
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(disableButtons){
                    submitButton.setEnabled(false);
                    textField1.setText("Please View & Complete Your Order");
                }else {
                    textField1.setText("");
                    submitButton.setEnabled(true);
                }
            }
        });

        textField2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(disableButtons){
                    submitButton1.setEnabled(false);
                    textField2.setText("");
                }else {
                    textField2.setText("");
                    textField4.setText("");
                    textField5.setText("");
                    submitButton1.setEnabled(true);
                }
            }
        });

        textField3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                if(disableButtons){
                    submitButton2.setEnabled(false);
                    textField3.setText("Please View & Complete Your Order");
                }else {
                    textField3.setText("");
                    submitButton2.setEnabled(true);
                }
            }
        });
        //endregion EnterListener



        //Submit Button for text field
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    id = Integer.parseInt(textField1.getText());
                    submitButton.setEnabled(false);
            }
        });

        submitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookID = Integer.parseInt(textField2.getText());
                submitButton1.setEnabled(false);
            }
        });

        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookQuantity = Integer.parseInt(textField3.getText());
                submitButton2.setEnabled(false);
            }
        });

        processItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAuthSearch(true);
            }
        });

        confirmItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConfirmItem(true);
                textField4.setText("Book Confirmed");
                textField5.setText("Book Confirmed");
            }
        });
        QuitOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        viewOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPopBox(true);
                setViewOrder(true);

            }
        });
        finishOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPrintRecipt(true);
            }
        });
        newOrderButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setNewOrder(true);
                setBookID(0);
                setAuthSearch(false);
                setConfirmItem(false);
                setNewOrder(false);
                setPopBox(true);
                setAuthSearch(false);
                setConfirmItem(false);
                setViewOrder(false);
                setPrintRecipt(false);
                setNewOrder(false);
                setBookQuantity(0);

                textField1.setText("Enter An Integer Value");
                textField2.setText("Enter An Integer Value");
                textField3.setText("Enter An Integer Value");
                textField4.setText("");
                textField5.setText("");
            }
        });
    }

    public void setConfirmItem(boolean confirmItem) {
        this.confirmItem = confirmItem;
    }

    public boolean isConfirmItem() {
        return confirmItem;
    }

    public boolean isAuthSearch() {
        return authSearch;
    }

    public void setBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public void setAuthSearch(boolean authSearch) {
        this.authSearch = authSearch;
    }

    public int getBookQuantity() {
        return bookQuantity;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getBookID() {
        return bookID;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JLabel getOrderItemNumber() {
        return orderItemNumber;
    }

    public JLabel getOrderItemNumber2() {
        return orderItemNumber2;
    }

    public JLabel getOrderItemNumber3() {
        return orderItemNumber3;
    }

    public JLabel getOrderItemNumber4() {
        return orderItemNumber4;
    }



    public void setOrderItemNumber(JLabel orderItemNumber) {
        this.orderItemNumber = orderItemNumber;
    }

    public void setOrderItemNumber2(JLabel orderItemNumber2) {
        this.orderItemNumber2 = orderItemNumber2;
    }

    public void setOrderItemNumber3(JLabel orderItemNumber3) {
        this.orderItemNumber3 = orderItemNumber3;
    }

    public void setOrderItemNumber4(JLabel orderItemNumber4) {
        this.orderItemNumber4 = orderItemNumber4;
    }

    public boolean isViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(boolean viewOrder) {
        this.viewOrder = viewOrder;
    }

    public boolean isPopBox() {
        return popBox;
    }

    public void setPopBox(boolean popBox) {
        this.popBox = popBox;
    }

    public boolean isPrintRecipt() {
        return printRecipt;
    }

    public void setPrintRecipt(boolean printRecipt) {
        this.printRecipt = printRecipt;
    }

    public boolean isNewOrder() {
        return newOrder;
    }

    public void setNewOrder(boolean newOrder) {
        this.newOrder = newOrder;
    }

    public boolean isDisableButtons() {
        return disableButtons;
    }

    public void setDisableButtons(boolean disableButtons) {
        this.disableButtons = disableButtons;
    }

    public void setId(int id) {
        this.id = id;
    }


}
