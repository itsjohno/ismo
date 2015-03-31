package co.ismo.gui.controllers;

import co.ismo.objects.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnathan
 * Package: co.ismo.gui.controllers
 * Date: 31/03/2015
 * Project: ismo-client
 */
public class TillController {

    List<Item> itemList;

    public TillController() {
        itemList = new ArrayList<Item>();
    }

    public boolean addItem(String pid) {
        //TODO: Perform SQL Lookup!

        itemList.add(new Item(pid));

        for (Item i : itemList) {
            System.out.println("SKU - " + i.getSku());
            System.out.println("Name - " + i.getName());
            System.out.println("Cost - " + i.getCost());
            System.out.println("Age Rating - " + i.getAgeRating());
            System.out.println();
        }

        Double temp = (double)basketCost()/100;
        System.out.println("End of Items - Total Cost: " + temp);
        System.out.println();

        return true;
    }

    public int basketCost() {
        int total = 0;

        for (Item i : itemList) {
            total += i.getCost();
        }

        return total;
    }
}
