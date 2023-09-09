Inventory Manager

Table of Contents:
Introduction
Features
Prerequisites
Getting Started
Installation
Usage


Introduction:
The Inventory Manager Application is a JavaFX-based inventory management system designed to help businesses efficiently
manage their inventory of parts and products. This application allows users to add, modify, and delete parts and
products, search for specific items, and save their inventory data in JSON format.

Features:
User-friendly graphical user interface (GUI) for easy interaction.
Add, modify, and delete parts and products.
Search for parts and products by name or ID.
Save and load inventory data in JSON format for data persistence.
JavaFX for the GUI.
JUnit 5 for testing.
Maven for project management and build.

Prerequisites:
Java 11 or higher installed.
Maven 3.6.x or higher installed.
An integrated development environment (IDE) such as IntelliJ IDEA or Eclipse (optional).

Quick Start (Running the Project Directly without Source Code):
A JAR file is included via the following file path: out\artifacts\Inventory_Manager_jar.inventoryManager.jar
Simply start the JAR file (You must have java on your computer), and the program will open to the main menu.

Installation (For Developers):
1. Clone the project repository: git clone https://github.com/T-Bower-GH/inventory-manager.git
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
3. Build the project using Maven: mvn clean install

Usage:
Adding a Part or Product: Click the "Add" button under the appropriate table on the main menu.
Modifying an Item: Select an item in the appropriate table and click the "Modify" button.
Deleting an Item: Select an item in the appropriate table and click the "Delete" button.
Searching for Items: Use the search bar above the appropriate table to search for parts or products by name or ID number.
Associating Parts: In the "Add Product" or "Modify Product" form, select the desired part(s) from the given list of parts and use the "Add" button to associate the part(s) to the product.
Saving Inventory Data: Click the "Save All" button to save all inventory data to a JSON file.

