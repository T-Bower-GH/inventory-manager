package com.trevorBower.inventoryManager;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import static com.trevorBower.inventoryManager.Inventory.lookupPart;

/**
 * @author Trevor Bower
 */
public class Product {
 private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
 private int id;
 private String name;
 private double price;
 private final int stock;
 private int min;
 private int max;

 public Product(int id, String name, double price, int stock, int min, int max) {
  this.id = id;
  this.name = name;
  this.price = price;
  this.stock = stock;
  this.min = min;
  this.max = max;
 }

 // Setters
 public void setId(int id) {
  this.id = id;
 }
 public void setName(String name) {
  this.name = name;
 }
 public void setPrice(double price) {
  this.price = price;
 }
 public void setMin(int min) {
  this.min = min;
 }
 public void setMax(int max) {
  this.max = max;
 }

 // Getters
 public int getId() { return id; }
 public String getName() { return name; }
 public double getPrice() { return price; }
 public int getStock() { return stock; }
 public int getMin() { return min; }
 public int getMax() { return max; }
 public ObservableList<Part> getAllAssociatedParts() {
  ObservableList<Part> upToDateAssociatedParts = FXCollections.observableArrayList();
  for (Part part : associatedParts) {
   int partID = part.getId();
   Part upToDatePart = lookupPart(partID);
   if (upToDatePart != null) {
    upToDateAssociatedParts.add(upToDatePart);
   }
  }
  return upToDateAssociatedParts;
 }

 public void addAssociatedPart(Part newPart) {associatedParts.add(newPart);}
 public void addAssociatedParts(ObservableList<Part> newParts) { associatedParts.addAll(newParts); }

}
