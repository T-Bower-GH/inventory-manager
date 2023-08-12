package com.example.software1project;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * @author Trevor Bower
 */

public class Product {

 private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
 private int id;
 private String name;
 private double price;
 private int stock;
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

 /**
  * Sets product ID
  * @param id product ID being set
  */
 public void setId(int id) {
  this.id = id;
 }

 /**
  * Sets product name
  * @param name product name being set
  */
 public void setName(String name) {
  this.name = name;
 }

 /**
  * Sets product price
  * @param price product price being set
  */
 public void setPrice(double price) {
  this.price = price;
 }

 /**
  * Sets product stock
  * @param stock product stock being set
  */
 public void setStock(int stock) {
  this.stock = stock;
 }

 /**
  * Sets product min
  * @param min product min being set
  */
 public void setMin(int min) {
  this.min = min;
 }

 /**
  * Sets product max
  * @param max product max being set
  */
 public void setMax(int max) {
  this.max = max;
 }


 // Getters

 /**
  * Gets product ID
  * @return id of product
  */
 public int getId() {
  return id;
 }

 /**
  * Gets product name
  * @return name of product
  */
 public String getName() {
  return name;
 }

 /**
  * Gets product price
  * @return price of product
  */
 public double getPrice() {
  return price;
 }

 /**
  * Gets product stock
  * @return stock of product
  */
 public int getStock() {
  return stock;
 }

 /**
  * Gets product min
  * @return min of product
  */
 public int getMin() {
  return min;
 }

 /**
  * Gets product max
  * @return max of product
  */
 public int getMax() {
  return max;
 }

 /**
  * Adds part to a product's list of associated parts
  * @param part Part being added to product's associated parts
  */
 public void addAssociatedPart(Part part) {
  associatedParts.add(part);
 }

 /**
  * Deletes selected part from a product's list of associated parts
  * @param selectedAssociatedPart Part being deleted from product's associated parts
  */
 public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
  return associatedParts.remove(selectedAssociatedPart);
 }

 /**
  * Gets all of a product's associated parts
  * @return All of a product's associated parts
  */
 public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}

 /**
  * Adds multiple parts to product's list of associated parts
  * @param newParts List of all associated parts being added
  */
 public void addAssociatedPart(ObservableList<Part> newParts) {
  associatedParts.addAll(newParts);
 }
}
