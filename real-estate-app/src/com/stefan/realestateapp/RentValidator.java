package com.stefan.realestateapp;

import java.util.Arrays;
import java.util.Scanner;

public class RentValidator {

	public static boolean isValidRent(RealEstateType estateType, int rentValue) {
		if (rentValue < 0) {
			System.out.println("Rent should be a positive number");
			return false;
		}

		boolean valid = false;
		switch (estateType) {
		case STUDIO:
			valid = rentValue <= 350;
			if (!valid) {
				System.out.println("rent value should be <= 350");
			}
			return valid;
		case TWO_ROOMS:
			valid = rentValue <= 450;
			if (!valid) {
				System.out.println("rent value should be <= 450");
			}
			return valid;
		case THREE_ROOMS:
			valid = rentValue >= 455;
			if (!valid) {
				System.out.println("rent value should be >=455");
			}
			return valid;
		default:
			return false;
		}
	}
}
