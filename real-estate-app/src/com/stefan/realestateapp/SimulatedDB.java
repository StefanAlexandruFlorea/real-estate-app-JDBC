package com.stefan.realestateapp;

import java.util.*;

public class SimulatedDB {

    private static int id = 0;
    private static final List<RealEstate> REAL_ESTATES = new ArrayList<>();

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
        while (type == null) {
            try {
                type = RealEstateType.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Type not ok, enter one of this: " + Arrays.toString(RealEstateType.values()));
            }
        }

        System.out.println("Rent value");
        int rent = scanner.nextInt();
        while (!RentValidator.isValidRent(type, rent)) {
            rent = scanner.nextInt();
        }

        System.out.println("Parking: true/false");
        Boolean parking = null;
        while (parking==null) {
            try {
                Scanner scan = new Scanner(System.in);
                parking = scan.nextBoolean();
            } catch (InputMismatchException e) {
                System.out.println("Value not ok, should be: false / true");
            }
        }


        RealEstate realEstate = new RealEstate(owner, type, rent, parking);

        realEstate.setId(++id);
        REAL_ESTATES.add(realEstate);
        System.out.println("Real estate added");
    }

    public void updateRent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id ");
        int id = scanner.nextInt();

        System.out.println("Enter rent ");
        int rent = scanner.nextInt();

        boolean found = false;
        for (RealEstate realEstate : REAL_ESTATES) {
            if (realEstate.getId() == id) {
                found = true;
                RealEstateType type = realEstate.getType();
                while (!RentValidator.isValidRent(type, rent)) {
                    System.out.println("Try again");
                    rent = scanner.nextInt();
                }
                realEstate.setRentValue(rent);
            }
        }

        if (!found) {
            System.out.println("Real estate doesn't exist");
        }
    }

    public void listRealEstates() {
        REAL_ESTATES.sort((r1, r2) -> r1.getRentValue() - r2.getRentValue());
        System.out.println("real estates: ");
        System.out.println(REAL_ESTATES);
    }

    public void listEstatesByType() {// listare estates dupa type
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter one of the following type: STUDIO, TWO_ROOM or THREE_ROOM");
        String inputType = scanner.nextLine();
        RealEstateType type = null;

        if (inputType.equalsIgnoreCase("STUDIO")) {
            type = RealEstateType.valueOf(inputType);
        } else if (inputType.equalsIgnoreCase("TWO_ROOMS")) {
            type = RealEstateType.valueOf(inputType);
        } else if (inputType.equalsIgnoreCase("THREE_ROOMS")) {
            type = RealEstateType.valueOf(inputType);
        } else {
            System.out.println("Invalid type, try again!");
        }

        if (type != null) {
            int noOfAvailable = 0;
            for (RealEstate realEstate : REAL_ESTATES) {
                if (realEstate.getType() == type) {
                    noOfAvailable++;
                }
            }

            if (noOfAvailable != 0) {
                System.out.println(type + ": " + noOfAvailable);
                for (RealEstate realEstate : REAL_ESTATES) {
                    if (realEstate.getType() == type) {
                        System.out.println(realEstate);
                    }
                }
            } else {
                System.out.println(type + ": " + noOfAvailable);
            }
        }
    }

    public void deleteRealEstate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id ");
        int id = scanner.nextInt();

        RealEstate toBeDeleted = null;

        for (RealEstate realEstate : REAL_ESTATES) {
            if (realEstate.getId() == id) {
                toBeDeleted = realEstate;
            }
        }

        if (toBeDeleted != null) {
            REAL_ESTATES.remove(toBeDeleted);
        } else {
            System.out.println("Real estate doesn't exist");
        }
    }

    public void addParking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id");
        int id = scanner.nextInt();
        RealEstate toAddParking = null;

        for (RealEstate realEstate : REAL_ESTATES) {
            if (realEstate.getId() == id) {
                toAddParking = realEstate;
            }
        }

        if (toAddParking != null) {
            toAddParking.setParking(true);
            int newRent = toAddParking.getRentValue() + 30;
            if (RentValidator.isValidRent(toAddParking.getType(), newRent)) {
                toAddParking.setRentValue(newRent);
                System.out.println("The new rent is: " + newRent);
            }
        } else {
            System.out.println("Real estate doesn't exist");
        }
    }
}
