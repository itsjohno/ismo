package co.ismo.object.type;

import java.util.*;

/**
 * Created by Johnathan
 * Package: co.ismo.object.type
 * Date: 20/04/2015
 * Project: ismo-fxml-client
 */
public class Transaction {

    private String storeID;
    private String tillID;
    private String transactionID;
    private int operatorID;
    private int customerID;
    private int totalCost;
    private Date datetime;
    private boolean suspended;
    private boolean cancelled;
    private Map<Product, Integer> products;
    private List<Tender> tenders;

    public Transaction(String storeID, String tillID, String transactionID) {
        this.storeID = storeID;
        this.tillID = tillID;
        this.transactionID = transactionID;
        this.products = new HashMap<Product, Integer>();
        this.tenders = new ArrayList<Tender>();
    }

    public Transaction(String storeID, String tillID, String transactionID, int operatorID, int customerID, int totalCost, Date datetime, boolean suspended, boolean cancelled, Map<Product, Integer> products, List<Tender> tenders) {
        this.storeID = storeID;
        this.tillID = tillID;
        this.transactionID = transactionID;
        this.operatorID = operatorID;
        this.customerID = customerID;
        this.totalCost = totalCost;
        this.datetime = datetime;
        this.suspended = suspended;
        this.cancelled = cancelled;
        this.products = products;
        this.tenders = tenders;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getTillID() {
        return tillID;
    }

    public void setTillID(String tillID) {
        this.tillID = tillID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(int operatorID) {
        this.operatorID = operatorID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public List<Tender> getTenders() {
        return tenders;
    }

    public void setTenders(List<Tender> tenders) {
        this.tenders = tenders;
    }
}
