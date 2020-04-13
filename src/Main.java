/*
Name:  Richard Holter
Course: CNT 4714 – Spring 2020
Assignment title: Project 1 – Event-driven Enterprise Simulation
Date: Sunday January 21, 2020
*/

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static Integer WIDTH = 900;
    public static Integer HEIGHT = 300;
    private Order order;

    public static void main(String[] args) throws IOException {
        //Store Inventory
        //region Process_Inventory

        String todayAsString = "";
        int bookCount = 1;
        boolean transactionFileExists = false;
        int one = 1;
        boolean run = true;
        boolean skip = false;
        double runningTotal = 0;
        boolean printRecipt = false;
        boolean newOrder = false;
        boolean popBox = true;
        boolean bookFound = false;
        String tempTotalStr = "";
        int confirmedBooks = 0;
        boolean viewOrder = false;
        boolean confirmBook = false;
        boolean authSearch = false;
        int bookQuantity = 0;
        boolean printOnce = true;
        boolean Enter = false;
        int orderTotal = 0;
        int bookID = 0;
        int inventoryCount = 0;
        double price = 0.0;
        String id = "";
        String title = "";
        String lineContents = "";

        String discountOne = " 0% Dis: $";
        String discountTwo = " 10% Dis: $";
        String discountThree = " 15% Dis: $";


        //Grabs our inventory text to store
        Scanner input = new Scanner(new File("inventory.txt"));

        //Re-Grab the text file to store items after array allocation
        Scanner inputTwo = new Scanner(new File("inventory.txt"));


        try {
            Scanner inputThree = new Scanner(new File("Transactions.txt"));
        } catch(FileNotFoundException e) {
            transactionFileExists = false;
            one = 0;
        }if(one == 1){
            transactionFileExists = true;
        }




        //Loop to Count inventory for Book Array
        while (input.hasNextLine() && input.nextLine() != "") {
            inventoryCount++;
        }

        DecimalFormat numberFormatTwo = new DecimalFormat("#.00");

        //Create Array of Book's called Inventory
        Book[] Inventory = new Book[inventoryCount];

        //Re-Use inventoryCount to count array Index
        inventoryCount = 0;

        //Store each Item in Inventory.txt in Inventory Array
        while (inputTwo.hasNextLine()) {
            lineContents = inputTwo.nextLine();
            lineContents = lineContents.replace("\"", "");
            id = lineContents.split(",")[0];
            title = lineContents.split(",")[1];
            price = Double.valueOf((lineContents.split(",")[2]));
            int tempID = Integer.parseInt(id);
            Inventory[inventoryCount] = new Book(tempID, title, price);
            inventoryCount++;
        }

        //endregion Process_Inventory

        //Handle GUI Operations
        // region Process_GUI

        //Create Frame
        JFrame frame = new ProjectOneGUI("Ye Olde Book Shoppe", WIDTH, HEIGHT);
        frame.setSize(WIDTH, HEIGHT);
        ((ProjectOneGUI) frame).textField1.setText("Enter An Integer Value");
        ((ProjectOneGUI) frame).textField2.setText("Enter An Integer Value");
        ((ProjectOneGUI) frame).textField3.setText("Enter An Integer Value");
        ((ProjectOneGUI) frame).submitButton.setEnabled(false);
        ((ProjectOneGUI) frame).submitButton1.setEnabled(false);
        ((ProjectOneGUI) frame).submitButton2.setEnabled(false);

        ((ProjectOneGUI) frame).confirmItemButton.setEnabled(false);
        ((ProjectOneGUI) frame).QuitOrderButton.setEnabled(true);
        ((ProjectOneGUI) frame).processItemButton.setEnabled(false);
        ((ProjectOneGUI) frame).finishOrderButton.setEnabled(false);
        ((ProjectOneGUI) frame).newOrderButton1.setEnabled(true);
        ((ProjectOneGUI) frame).viewOrderButton.setEnabled(false);

        frame.setVisible(true);


        //If the user has not entered the total order size
        while (orderTotal == 0) {

            System.out.println("Looping1.");


            //Print message to user
            if (printOnce) {
                System.out.println("Please Enter Order Total.");
                printOnce = false;
            }

            //While the user has not interacted with the total orderSize wait for interaction
            orderTotal = ((ProjectOneGUI) frame).getId();
        }


        //System.out.println("orderTotal: "+ orderTotal);

        //Prep next message to print once
        printOnce = true;

        //Create order container
        Order order = new Order("", 0, orderTotal);



        //Store Loop
        for (int i = 0; i < order.getOrderSize(); i++) {

            ((ProjectOneGUI) frame).setPrintRecipt(false);

            orderTotal = 0;
            //If the user has not entered the total order size
            while (!run && skip) {

                System.out.println("Looping In Store.");
                bookCount = 1;
                ((ProjectOneGUI) frame).orderItemNumber.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber2.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber3.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber4.setText(Integer.toString(bookCount));

                //While the user has not interacted with the total orderSize wait for interaction
                orderTotal = ((ProjectOneGUI) frame).getId();

                if(orderTotal > 0){
                    i = 0;
                    run = true;
                    skip = false;
                    order.setOrderSize(orderTotal);
                    ((ProjectOneGUI) frame).setNewOrder(false);
                    break;
                }
            }



            //We run the program in the while() BUT if we want new order we get out of while() and restart loop
            while(run & !skip){

                ((ProjectOneGUI) frame).orderItemNumber.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber2.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber3.setText(Integer.toString(bookCount));
                ((ProjectOneGUI) frame).orderItemNumber4.setText(Integer.toString(bookCount));

            //If we have interacted with the store before, wipe previous entries
            if (i > 0) {
               // ((ProjectOneGUI) frame).textField1.setText(order.getOrderSize().toString());
                ((ProjectOneGUI) frame).textField1.setText(order.getOrderSize().toString());
                ((ProjectOneGUI) frame).textField2.setText("Enter An Integer Value");
                ((ProjectOneGUI) frame).textField3.setText("Enter An Integer Value");
                ((ProjectOneGUI) frame).textField4.setText("");
                ((ProjectOneGUI) frame).textField5.setText("");
            }

            //Reset values that control the flow of the store
            ((ProjectOneGUI) frame).setBookQuantity(0);
            ((ProjectOneGUI) frame).setBookID(0);
            ((ProjectOneGUI) frame).setAuthSearch(false);
            ((ProjectOneGUI) frame).setConfirmItem(false);

            //Get BookID
            while (bookID == 0) {

                System.out.println("Looping2.");

                //Handles View Order after initial book added to cart
                viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                popBox = ((ProjectOneGUI) frame).isPopBox();
                if (((ProjectOneGUI) frame).isViewOrder()) {
                    if (popBox) {
                        infoBox(order.titles, "You Current Order");
                        ((ProjectOneGUI) frame).setPopBox(false);
                    }
                }

                //Handles New Order Controls
                newOrder = ((ProjectOneGUI) frame).isNewOrder();

                if (((ProjectOneGUI) frame).isNewOrder()) {
                    run = false;
                    break;
                }

                if (printOnce) {
                    System.out.println("Please Enter Book ID.");
//                    ((ProjectOneGUI) frame).orderItemNumber.setText(Integer.toString(bookCount));
//                    ((ProjectOneGUI) frame).orderItemNumber2.setText(Integer.toString(bookCount));
//                    ((ProjectOneGUI) frame).orderItemNumber3.setText(Integer.toString(bookCount));
//                    ((ProjectOneGUI) frame).orderItemNumber4.setText(Integer.toString(bookCount));
                    printOnce = false;
                }

                //While the user has not interacted with the current book ID search
                bookID = ((ProjectOneGUI) frame).getBookID();
            }

                //If we press new order we set run to false above and break
                if(!run){
                    continue;
                }

            //System.out.println("Got Book ID: " + bookID);
            printOnce = true;

            //Hold book ID and Get Quantity
            while (bookID != 0 && bookQuantity == 0) {

                System.out.println("Looping3.");

                //Handles View Order after initial book added to cart
                viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                popBox = ((ProjectOneGUI) frame).isPopBox();
                if (((ProjectOneGUI) frame).isViewOrder()) {
                    if (popBox) {
                        infoBox(order.titles, "You Current Order");
                        ((ProjectOneGUI) frame).setPopBox(false);
                    }
                }

                //Handles New Order Controls
                newOrder = ((ProjectOneGUI) frame).isNewOrder();

                if (((ProjectOneGUI) frame).isNewOrder()) {
                    run = false;
                    break;
                }

                if (printOnce) {
                    System.out.println("Please Enter Book Quantity.");
                    printOnce = false;
                }
                bookQuantity = ((ProjectOneGUI) frame).getBookQuantity();
            }

                //If we press new order we set run to false above and break
                if(!run){
                    continue;
                }

            System.out.println("Turn on.");
            ((ProjectOneGUI) frame).processItemButton.setEnabled(true);

            //Got All info, wait for authorized search
            while (!authSearch) {
                System.out.println("Looping4.");

                //Handles View Order after initial book added to cart
                viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                popBox = ((ProjectOneGUI) frame).isPopBox();
                if (((ProjectOneGUI) frame).isViewOrder()) {
                    if (popBox) {
                        infoBox(order.titles, "You Current Order");
                        ((ProjectOneGUI) frame).setPopBox(false);
                    }
                }

                //Handles New Order Controls
                newOrder = ((ProjectOneGUI) frame).isNewOrder();
                if (((ProjectOneGUI) frame).isNewOrder()) {
                    run = false;
                    break;
                }

                authSearch = ((ProjectOneGUI) frame).authSearch;
            }

                //If we press new order we set run to false above and break
                if(!run){
                    continue;
                }

            //If we have permission to search
            if (authSearch) {
                //Disable process button after press
                ((ProjectOneGUI) frame).processItemButton.setEnabled(false);

                //search our inventory for matching ID
                for (int j = 0; j < Inventory.length; j++) {
                    System.out.println("Searching For Book: " + bookID);

                    bookFound = false;

                    //Once we find the matching book set up text fields for client info
                    if (Inventory[j].id == bookID) {

                        System.out.println("Book Located");
                        bookFound = true;

                        //Handle book discounts based on quantity available
                        //1-4 = 0% , 5-9 = 10% , 10-15 = 15%
                        if (bookQuantity >= 1 && bookQuantity <= 4) {
                            tempTotalStr = String.valueOf(Inventory[j].price * bookQuantity);
                            ((ProjectOneGUI) frame).textField4.setText(Inventory[j].title + " " + bookQuantity + " @" + discountOne + Inventory[j].price);
                            ((ProjectOneGUI) frame).textField5.setText(numberFormatTwo.format(Double.valueOf(tempTotalStr)));
                        }
                        if (bookQuantity >= 5 && bookQuantity <= 9) {
                            tempTotalStr = String.valueOf((Inventory[j].price - (Inventory[j].price * 0.10)) * bookQuantity);
                            ((ProjectOneGUI) frame).textField4.setText(Inventory[j].title + " " + bookQuantity + " @" + discountTwo + Inventory[j].price);
                            ((ProjectOneGUI) frame).textField5.setText(numberFormatTwo.format(Double.valueOf(tempTotalStr)));
                        }
                        if (bookQuantity >= 10 && bookQuantity <= 15) {
                            tempTotalStr = String.valueOf((Inventory[j].price - (Inventory[j].price * 0.15)) * bookQuantity);
                            ((ProjectOneGUI) frame).textField4.setText(Inventory[j].title + " " + bookQuantity + " @" + discountThree + Inventory[j].price);
                            ((ProjectOneGUI) frame).textField5.setText(numberFormatTwo.format(Double.valueOf(tempTotalStr)));
                        }

                        //Enable button to "Add to cart"
                        ((ProjectOneGUI) frame).confirmItemButton.setEnabled(true);

                        //After we found the book wait for confirmation
                        while (!confirmBook) {
                            System.out.println("Looping5.");

                            //Handles View Order after initial book added to cart
                            viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                            popBox = ((ProjectOneGUI) frame).isPopBox();
                            if (((ProjectOneGUI) frame).isViewOrder()) {
                                if (popBox) {
                                    infoBox(order.titles, "You Current Order");
                                    ((ProjectOneGUI) frame).setPopBox(false);
                                }
                            }

                            //Handles New Order Controls
                            newOrder = ((ProjectOneGUI) frame).isNewOrder();
                            if (((ProjectOneGUI) frame).isNewOrder()) {
                                run = false;
                                break;
                            }

                            confirmBook = ((ProjectOneGUI) frame).isConfirmItem();
                        }

                        //If we press new order we set run to false above and break
                        if(!run){
                            break;
                        }

                        //Add to our order list
                        if (confirmBook) {
                            infoBox("Book: " + bookID + " Added To Order", "Success");
                            ((ProjectOneGUI) frame).confirmItemButton.setEnabled(false);

                            if (bookQuantity >= 0 && bookQuantity <= 4) {
                                order.titles = order.titles + Inventory[j].title + " " + bookQuantity + " @" + discountOne + (numberFormatTwo.format(Double.valueOf(tempTotalStr))) + ",";
                                order.setTotalPrice(order.getTotalPrice() + (Inventory[j].price * bookQuantity));
                                runningTotal = runningTotal + (Inventory[j].price * bookQuantity);
                            }
                            if (bookQuantity >= 5 && bookQuantity <= 9) {
                                order.titles = order.titles + Inventory[j].title + " " + bookQuantity + " @" + discountTwo + (numberFormatTwo.format(Double.valueOf(tempTotalStr))) + ",";
                                order.setTotalPrice(order.getTotalPrice() + (Inventory[j].price * bookQuantity));
                                runningTotal = runningTotal + ((Inventory[j].price - (Inventory[j].price * 0.1)) * bookQuantity);
                            }
                            if (bookQuantity >= 10 && bookQuantity <= 15) {
                                order.titles = order.titles + Inventory[j].title + " " + bookQuantity + " @" + discountThree + (numberFormatTwo.format(Double.valueOf(tempTotalStr))) + ",";
                                order.setTotalPrice(order.getTotalPrice() + (Inventory[j].price * bookQuantity));
                                runningTotal = runningTotal + ((Inventory[j].price - (Inventory[j].price * 0.15)) * bookQuantity);
                            }

                            order.totalPrice = order.totalPrice + Inventory[j].price;
                            bookCount++;

                            confirmedBooks++;
                            System.out.println("Book Added To Cart");

                            ((ProjectOneGUI) frame).textField2.setText("Enter An Integer Value");
                            ((ProjectOneGUI) frame).textField3.setText("Enter An Integer Value");
                            ((ProjectOneGUI) frame).textField1.setText(order.getOrderSize().toString());

                            //Allow the user to view their current order
                        }
                        break;
                    } else {

                        //Display on last inventory item since it wont be found
                        if (j == Inventory.length - 1 && bookFound == false) {
                            infoBox("Book: " + bookID + " Does Not Exist In Inventory", "Error");
                            //Disable confirm button if no match in Inventory
                            ((ProjectOneGUI) frame).confirmItemButton.setEnabled(false);
                            bookID = bookQuantity = 0;
                            //Reset store fields
                            ((ProjectOneGUI) frame).textField2.setText("Enter An Integer Value");
                            ((ProjectOneGUI) frame).textField3.setText("Enter An Integer Value");
                            ((ProjectOneGUI) frame).textField1.setText(order.getOrderSize().toString());
                            //I automatically increments on loop, this is the end of the loop when we have not found the book
                            //We want to continue to loop until user can find at least one book.
                            i = i - 1;
                        }
                    }

                }
                //Reset vars used to run the store
                bookID = 0;
                bookQuantity = 0;
                confirmBook = false;
                bookFound = false;
                printOnce = true;
                Enter = false;
                popBox = true;
                bookID = 0;
                price = 0.0;
                id = "";
                title = "";
                lineContents = "";
                authSearch = false;

                //If we have added the max items to the cart
                if (confirmedBooks > 0) {
                    ((ProjectOneGUI) frame).viewOrderButton.setEnabled(true);
                }
            }

            //If were done shopping
            if (confirmedBooks == order.getOrderSize()) {

                ((ProjectOneGUI) frame).setDisableButtons(true);
                ((ProjectOneGUI) frame).finishOrderButton.setEnabled(true);
                ((ProjectOneGUI) frame).textField1.setText("Please View & Complete Your Order");
                ((ProjectOneGUI) frame).textField2.setText("");
                ((ProjectOneGUI) frame).textField3.setText("");
                ((ProjectOneGUI) frame).textField4.setText("");

                ((ProjectOneGUI) frame).textField1.setEditable(false);
                ((ProjectOneGUI) frame).textField2.setEditable(false);
                ((ProjectOneGUI) frame).textField3.setEditable(false);
                ((ProjectOneGUI) frame).textField4.setEditable(false);
                ((ProjectOneGUI) frame).textField5.setEditable(false);

                ((ProjectOneGUI) frame).submitButton.setEnabled(false);
                ((ProjectOneGUI) frame).submitButton1.setEnabled(false);
                ((ProjectOneGUI) frame).submitButton2.setEnabled(false);
                ((ProjectOneGUI) frame).processItemButton.setEnabled(false);


                double tempOrdrTotal = order.getTotalPrice();
                String tempTotal = Double.toString(tempOrdrTotal);
                ((ProjectOneGUI) frame).textField5.setText("$" + runningTotal);

                while (!printRecipt) {
                    System.out.println("Looping6.");

                    //Handles View Order after initial book added to cart
                    viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                    popBox = ((ProjectOneGUI) frame).isPopBox();
                    if (((ProjectOneGUI) frame).isViewOrder()) {
                        if (popBox) {
                            infoBox(order.titles, "You Current Order");
                            ((ProjectOneGUI) frame).setPopBox(false);
                        }
                    }

                        //Handles New Order Controls
                        newOrder = ((ProjectOneGUI) frame).isNewOrder();
                        if (((ProjectOneGUI) frame).isNewOrder()) {
                            run = false;
                            break;
                        }

                         printRecipt = ((ProjectOneGUI) frame).isPrintRecipt();
                }
                    if (printRecipt && run){

                        DecimalFormat numberFormat = new DecimalFormat("#.00");
                        Date date = new Date();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH-mm-ss");

                        //We need to create the file
                        if(!transactionFileExists){
                            File file = new File("Transactions.txt");
                            BufferedWriter out = new BufferedWriter(new FileWriter(file));
                            String tempFormat = dateFormat.format(date) + "," + order.titles.toString() + " Order Subtotal: $" + numberFormat.format(runningTotal) + " Tax Rate 6%" + " Tax Amount: $" + numberFormat.format(runningTotal * 0.06) + " Order Grand Total: $" + numberFormat.format((runningTotal * 0.06) + runningTotal);
                            out.write("\n"+tempFormat);
                            out.close();
                        }else{
                            FileWriter fstream = new FileWriter("Transactions.txt", true); //true tells to append data.
                            BufferedWriter out = new BufferedWriter(fstream);
                            String tempFormat = dateFormat.format(date) + "," + order.titles.toString() + " Order Subtotal: $" + numberFormat.format(runningTotal) + " Tax Rate 6%" + " Tax Amount: $" + numberFormat.format(runningTotal * 0.06) + " Order Grand Total: $" + numberFormat.format((runningTotal * 0.06) + runningTotal);
                            out.write("\n" + tempFormat);
                            out.close();
                        }


                        infoBox(" Items In Order: " + orderTotal + "\n" + order.titles.toString().replaceAll(",", "\n") + "\n Order Subtotal: $" + numberFormat.format(runningTotal) + "\n" + " Tax Rate 6%\n" + " Tax Amount: $" + numberFormat.format(runningTotal * 0.06) + "\n" + " Order Grand Total: $" + numberFormat.format((runningTotal * 0.06) + runningTotal) + "\n Thanks For Shopping At Ye Old Book Shoppe!", "Thanks!");
                        ((ProjectOneGUI) frame).finishOrderButton.setEnabled(false);

                        while(printRecipt){

                            System.out.println("Looping7.");

                            //Handles View Order after initial book added to cart
                            viewOrder = ((ProjectOneGUI) frame).isViewOrder();
                            popBox = ((ProjectOneGUI) frame).isPopBox();
                            if (((ProjectOneGUI) frame).isViewOrder()) {
                                if (popBox) {
                                    infoBox(" Items In Order: " + orderTotal + "\n" + order.titles.toString().replaceAll(",", "\n") + "\n Order Subtotal: $" + numberFormat.format(runningTotal) + "\n" + " Tax Rate 6%\n" + " Tax Amount: $" + numberFormat.format(runningTotal * 0.06) + "\n" + " Order Grand Total: $" + numberFormat.format((runningTotal * 0.06) + runningTotal) + "\n Thanks For Shopping At Ye Old Book Shoppe!", "Thanks!");
                                    ((ProjectOneGUI) frame).setPopBox(false);
                                }
                            }

                            //Handles New Order Controls
                            newOrder = ((ProjectOneGUI) frame).isNewOrder();
                            if (((ProjectOneGUI) frame).isNewOrder()) {
                                run = false;
                                printRecipt = false;
                                break;
                            }
                        }

                    }

                    //If we press new order we set run to false above and break
                    if(!run){
                        continue;
                    }

                }
            }

            if (!run){
                //Reset All values like we do at end of loop
                confirmBook = bookFound = Enter = authSearch = newOrder = false;
                id = title = lineContents = "";
                bookID = bookQuantity = 0;
                orderTotal = -1;
                popBox = printOnce = true;
                price = 0.0;
                runningTotal = 0;
                confirmedBooks = 0;
                i = -5;
                skip = true;
                bookCount = 1;

                //Reset the function
                ((ProjectOneGUI) frame).setNewOrder(false);

                //Reset our Cart/Order
                order.setTotalPrice(0);
                //for loop has to be < orderSize
                order.setOrderSize(5);
                order.setTitles("");

                //((ProjectOneGUI) frame).textField1.setText("Enter An Integer Value");
                ((ProjectOneGUI) frame).textField2.setText("Enter An Integer Value");
                ((ProjectOneGUI) frame).textField3.setText("Enter An Integer Value");
                ((ProjectOneGUI) frame).submitButton.setEnabled(false);
                ((ProjectOneGUI) frame).submitButton1.setEnabled(false);
                ((ProjectOneGUI) frame).submitButton2.setEnabled(false);

                ((ProjectOneGUI) frame).confirmItemButton.setEnabled(false);
                ((ProjectOneGUI) frame).QuitOrderButton.setEnabled(true);
                ((ProjectOneGUI) frame).processItemButton.setEnabled(false);
                ((ProjectOneGUI) frame).finishOrderButton.setEnabled(false);
                ((ProjectOneGUI) frame).newOrderButton1.setEnabled(true);
                ((ProjectOneGUI) frame).viewOrderButton.setEnabled(false);

                ((ProjectOneGUI) frame).textField1.setEditable(true);
                ((ProjectOneGUI) frame).textField2.setEditable(true);
                ((ProjectOneGUI) frame).textField3.setEditable(true);
                ((ProjectOneGUI) frame).textField4.setEditable(true);
                ((ProjectOneGUI) frame).textField5.setEditable(true);

                ((ProjectOneGUI) frame).submitButton.setEnabled(true);
                ((ProjectOneGUI) frame).submitButton1.setEnabled(false);
                ((ProjectOneGUI) frame).submitButton2.setEnabled(false);
                ((ProjectOneGUI) frame).setDisableButtons(false);

                ((ProjectOneGUI) frame).setId(0);
                ((ProjectOneGUI) frame).setBookQuantity(0);
                ((ProjectOneGUI) frame).setBookID(0);
                ((ProjectOneGUI) frame).setConfirmItem(false);
                ((ProjectOneGUI) frame).setDisableButtons(false);
                ((ProjectOneGUI) frame).setAuthSearch(false);
                //((ProjectOneGUI) frame).setNewOrder(false);
                ((ProjectOneGUI) frame).setPrintRecipt(false);
                ((ProjectOneGUI) frame).setViewOrder(false);
                ((ProjectOneGUI) frame).setConfirmItem(false);
            }

        }
        //endregion Process_GUI
    }


    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}

