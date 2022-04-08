package com.stefan.realestateapp;

import java.util.Scanner;

public class Application {

	public static void main(String[] args) {

		DatabaseAccess simulatedDB = new DatabaseAccess();

		Scanner scanner = new Scanner(System.in);

		int option = -1;

		while (option != 0) {
			System.out.println("""

					Enter one of the commands:\s
					1: addRealEstate
					2: updateRent
					3: listAllRealEstates
					4: listRealEstateByType
					5: addParking
					6: deleteRealEstate
					0: exit""");
			option = scanner.nextInt();

			switch (option) {
			case 1:
				simulatedDB.addRealEstate();
				break;
			case 2:
				simulatedDB.updateRent();
				break;
			case 3:
				simulatedDB.listRealEstates();
				break;
			case 4:
				simulatedDB.listEstatesByType();
				break;
			case 5:
				simulatedDB.addParking();
				break;
			case 6:
				simulatedDB.deleteRealEstate();
				break;
			case 0:
				System.out.println("The application has been closed");
				break;
			default:
				System.out.println("Invalid command ");
			}
		}
	}

}
