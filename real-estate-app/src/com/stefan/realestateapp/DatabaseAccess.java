package com.stefan.realestateapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabaseAccess {
	static Connection con = DBConnection.getConnection();

	public void addRealEstate() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter owner name ");
		String owner = scanner.nextLine();

		while (owner.length() > 200) {
			System.out.println("Invalid name, enter a name <200 characters");
			owner = scanner.nextLine();
		}

		System.out.println("Enter type: STUDIO, TWO_ROOMS or THREE_ROOMS");
		RealEstateType type = null;

		do {
			try {
				scanner = new Scanner(System.in);
				type = RealEstateType.valueOf(scanner.nextLine().toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.println("Type not ok, enter one of this: " + Arrays.toString(RealEstateType.values()));
			}
		} while (type == null);

		System.out.println("Enter rent value");
		int rent;

		do {
			while (!scanner.hasNextInt()) {
				System.out.println("This is not a number");
				scanner.next();
			}
			rent = scanner.nextInt();
			if (rent < 1) {
				System.out.println("Please enter a positive no >0");
			}
		} while (rent < 1);

		while (!RentValidator.isValidRent(type, rent)) {
			rent = scanner.nextInt();
		}

		System.out.println("Has parking: true/false");
		Boolean parking = null;

		do {
			try {
				parking = scanner.nextBoolean();
			} catch (InputMismatchException e) {
				System.out.println("Value not ok, should be: true/false");
				scanner.next();
			}
		} while (parking == null);

		RealEstate realEstate = new RealEstate(owner, type, rent, parking);
		int id;

		try {
			String SQL = "INSERT INTO estates_tbl (owner_name, type, rentValue, parking)" + "VALUES (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, realEstate.getOwnerName());
			ps.setString(2, realEstate.getType().toString());
			ps.setInt(3, realEstate.getRentValue());
			ps.setBoolean(4, realEstate.isParking());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
					realEstate.setId(id);
				}
				System.out.println("real estate was added " + realEstate);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateRent() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter id for rent update");
		int id = 0;

		RealEstate realEstate = null;

		LABEL_1: do {
			scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				System.out.println("This is not a number");
				scanner.next();
			}
			id = scanner.nextInt();
			if (id < 1) {
				System.out.println("Please enter a positive no");
				continue LABEL_1;
			}

			try {
				String searchId = "select * from estates_tbl where id = ?";
				PreparedStatement ps = con.prepareStatement(searchId, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					id = rs.getInt(1);
					String owner = rs.getString(2);
					RealEstateType type = RealEstateType.valueOf(rs.getString(3));
					int rent = rs.getInt(4);
					boolean parking = rs.getBoolean(5);

					realEstate = new RealEstate(owner, type, rent, parking);
					realEstate.setId(id);
					System.out.println("id found: " + realEstate);
				} else {
					System.out.println("inexistent id, try again");
				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} while (realEstate == null);

		System.out.println("Enter new rent ");
		int rent = 0;

		LABEL_2: do {
			scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				System.out.println("This is not a number");
				scanner.next();
			}

			rent = scanner.nextInt();

			if (rent < 1) {
				System.out.println("Enter a positive no >0");
			} else {
				if (!RentValidator.isValidRent(realEstate.getType(), rent)) {
					rent = 0;
					System.out.println("Try again");
					continue LABEL_2;

				} else {
					try {
						String updateRent = "update estates_tbl set rentValue = ? WHERE id = ?";
						PreparedStatement ps = con.prepareStatement(updateRent);
						ps.setInt(1, rent);
						ps.setInt(2, id);

						int rowsAffected = ps.executeUpdate();
						if (rowsAffected > 0) {
							System.out.println("rent updated");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} while (rent < 1);

	}

	public void listRealEstates() {

		List<RealEstate> estateList = new ArrayList<>();
		RealEstate realEstate = null;

		try {
			Statement st = con.createStatement();
			ResultSet resultSet = st.executeQuery("select * from estates_tbl");

			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String owner = resultSet.getString("owner_name");
				RealEstateType type = RealEstateType.valueOf(resultSet.getString("type"));
				int rent = resultSet.getInt("rentValue");
				boolean parking = resultSet.getBoolean("parking");

				realEstate = new RealEstate(owner, type, rent, parking);
				realEstate.setId(id);

				estateList.add(realEstate);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		estateList.sort((r1, r2) -> r1.getRentValue() - r2.getRentValue());
		System.out.println("real estates ordered by rent values are : ");
		estateList.forEach(System.out::println);
	}

	public void listEstatesByType() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter one of the following type: " + Arrays.toString(RealEstateType.values()));
		String inputType = null;
		RealEstateType type = null;

		while (type == null) {
			inputType = scanner.nextLine().toUpperCase();
			if (inputType.equals("STUDIO")) {
				type = RealEstateType.valueOf(inputType);
			} else if (inputType.equals("TWO_ROOMS")) {
				type = RealEstateType.valueOf(inputType);
			} else if (inputType.equals("THREE_ROOMS")) {
				type = RealEstateType.valueOf(inputType);
			} else {
				System.out.println("Invalid type, try again!");
			}
		}

		int noOfAvailable = 0;

		List<RealEstate> estateList = new ArrayList<>();

		try {
			String SQL = "SELECT * FROM estates_tbl WHERE type = ? ";
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.setString(1, inputType);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				noOfAvailable++;
				RealEstate re = new RealEstate(resultSet.getString("owner_name"),
						RealEstateType.valueOf(resultSet.getString("type")), resultSet.getInt("rentValue"),
						resultSet.getBoolean("parking"));
				int id = resultSet.getInt(1);
				re.setId(id);

				estateList.add(re);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(type.toString() + " : " + noOfAvailable);
		estateList.forEach(System.out::println);
	}

	public void addParking() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter id to add parking");
		RealEstate realEstate = null;

		int id;
		String owner;
		RealEstateType type;
		int rent;
		boolean parking;

		do {
			scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				System.out.println("wrong input, enter an integer");
				scanner.next();
			}
			id = scanner.nextInt();
			if (id < 1) {
				System.out.println("enter a positive value > 0");
			} else {
				try {
					String searchId = "select * from estates_tbl where id = ?";
					PreparedStatement ps = con.prepareStatement(searchId, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, id);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {

						id = rs.getInt(1);
						owner = rs.getString(2);
						type = RealEstateType.valueOf(rs.getString(3));
						rent = rs.getInt(4);
						parking = rs.getBoolean(5);

						realEstate = new RealEstate(owner, type, rent, parking);
						realEstate.setId(id);
						System.out.println("id found: " + realEstate);
					} else {
						System.out.println("inexistent id, try again");
					}

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}

				if (realEstate == null) {
					System.out.println("Id doesn't exist, try again");
				}
			}

		} while (realEstate == null);

		if (!realEstate.isParking()) {
			realEstate.setParking(true);
			System.out.println("parking added");
			int newRent = realEstate.getRentValue() + 30;
			if (RentValidator.isValidRent(realEstate.getType(), newRent)) {
				realEstate.setRentValue(newRent);
				System.out.println("The new rent is: " + newRent);
			} else {
				System.out.println("Parking was set to true, but rent remains " + realEstate.getRentValue());
			}
		} else {
			System.out.println("parking can't be set, is already true");
		}

		try {
			String SQL = "update estates_tbl set rentValue = ?, parking = ? where id = ?";
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.setInt(1,realEstate.getRentValue());
			ps.setBoolean(2, realEstate.isParking());
			ps.setInt(3, realEstate.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRealEstate() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter id to delete estate");
		RealEstate realEstate = null;
		

		int id = 0;
		String owner;
		RealEstateType type;
		int rent;
		boolean parking;

		do {
			scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				System.out.println("wrong input, enter an integer");
				scanner.next();
			}
			id = scanner.nextInt();
			if (id < 1) {
				System.out.println("enter a positive value > 0");
			} else {
				
				try {
					String searchId = "select * from estates_tbl where id = ?";
					String deleteId = "delete from estates_tbl where id = ?";
					PreparedStatement ps = con.prepareStatement(searchId, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, id);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {

						id = rs.getInt(1);
						owner = rs.getString(2);
						type = RealEstateType.valueOf(rs.getString(3));
						rent = rs.getInt(4);
						parking = rs.getBoolean(5);

						realEstate = new RealEstate(owner, type, rent, parking);
						realEstate.setId(id);
						System.out.println("id found ");
						
						ps = con.prepareStatement(deleteId);
						ps.setInt(1, id);
						ps.executeUpdate();
					} else {
						System.out.println("inexistent id, try again");
					}

				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}

				if (realEstate == null) {
					System.out.println("Id doesn't exist, enter again");
				}
			}

		} while (realEstate == null);

		System.out.println("Real estate deleted: " + realEstate);
	}
}
