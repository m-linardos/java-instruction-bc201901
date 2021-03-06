package ui;

import java.util.List;

import business.Product;
import db.DAO;
import db.ProductDB;
//import db.ProductTextFile;
import util.Console;
import util.StringUtils;

public class ProductIOManagerApp {

	//private static DAO<Product> productDAO = new ProductTextFile();
	private static DAO<Product> productDAO = new ProductDB();
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Product Manager - Text File Edition");
		
		displayMenu();
		String action = "";
		while(!action.equalsIgnoreCase("exit")) {
			// get input from user
			action = Console.getRequiredString("Enter a command:  ");
			
			if (action.equalsIgnoreCase("list")) {
				// get a list of products and print to screen
				displayAllProducts();
				
			}
			else if (action.equalsIgnoreCase("add")) {
				// add a product
				addProduct();
			}
			else if (action.equalsIgnoreCase("del")) {
				// delete a product
				deleteProduct();
			}
			else if (action.equalsIgnoreCase("help")) {
				displayMenu();
			}
			else {
				System.out.println("Invalid command.");
			}
		}
		
		
		System.out.println("Bye");
	}
	
	public static void displayMenu() {
		System.out.println("COMMAND MENU");
		System.out.println("=======================");
		System.out.println("list	- list all products");
		System.out.println("add		- add a product");
		System.out.println("del		- delete a product");
		System.out.println("help	- show this menu");
		System.out.println("exit	- exit the app");

	}

	private static void displayAllProducts() {
		System.out.println("PRODUCT LIST:");
		System.out.println("====================");
		List<Product> products = productDAO.getAll();
		StringBuilder sb = new StringBuilder();
		for (Product p: products) {
			sb.append(StringUtils.padWithSpaces(p.getCode(), 8));
			sb.append(StringUtils.padWithSpaces(p.getDescription(), 40));
			sb.append(p.getPrice());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	private static void addProduct() {
		String code = Console.getRequiredString("Enter product code:  ");
		String description = Console.getRequiredString("Enter product description:  ");
		double price = Console.getDouble("Enter product price:  ");
		
		Product p = new Product(code, description, price);
		if (productDAO.add(p)) {
			System.out.println("Product "+p.getCode()+" successfully added.");
		}
		else {
			System.out.println("Error adding product.");
		}
	}
	
	private static void deleteProduct() {
		System.out.println("!!! Delete Product !!!");
		String code = Console.getRequiredString("Enter product code to delete:  ");
		// get a product for the code
		Product p = productDAO.get(code);
		if (p == null) {
			System.out.println("Invalid code.");
		}
		else {
			if (productDAO.delete(p)) {
				System.out.println("Delete success");
			}
			else {
				System.out.println("Error deleting product.");
			}
		}
	}
	
	
	
	
}
