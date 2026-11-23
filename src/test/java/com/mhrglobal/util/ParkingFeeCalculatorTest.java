package com.mhrglobal.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


public class ParkingFeeCalculatorTest {

	@Test
	public void givenVehicleDetailsWhenBlankVehicleTypeIsPassedThenThrowException() {
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        assertThrows(IllegalArgumentException.class, () -> {
        	parkingFeeCalculator.calculateParkingCharge(
                    null,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(2));
        });
	}
	
	@Test
	public void givenVehicleTypeWhenEntryTimeIsEmptyThenThrowException() {
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        assertThrows(IllegalArgumentException.class, () -> {
        	parkingFeeCalculator.calculateParkingCharge(
                    VehicleType.CAR,
                    null,
                    LocalDateTime.now().plusMinutes(2));
        });
	}
	
	@Test
	public void givenVehicleTypeWhenExitTimeIsEmptyThenThrowException() {
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        assertThrows(IllegalArgumentException.class, () -> {
        	parkingFeeCalculator.calculateParkingCharge(
                    VehicleType.CAR,
                    LocalDateTime.now(),
                    null);
        });
	}
	
	@Test
	public void givenVehicleTypeWhenExitTimeGreaterThanEntryTimeThenThrowException() {
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        assertThrows(IllegalArgumentException.class, () -> {
        	parkingFeeCalculator.calculateParkingCharge(
                    VehicleType.CAR,
                    LocalDateTime.now(),
                    LocalDateTime.now().minusMinutes(2));
        });
	}
	
	@Test
	void givenVehicleTypeWhenDetailsContainsDayNightAndWeekendRatesThenCalculateParkingCharge() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 13, 8, 30);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 16, 11, 20);

        VehicleType vehicleType = VehicleType.CAR;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("95.00", String.format("%.2f", parkingCharge));
	}

	@Test
	void givenVanVehicleTypeWhenDetailsContainsDayNightAndWeekendRatesThenCalculateParkingCharge() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 13, 8, 30);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 16, 11, 20);

        VehicleType vehicleType = VehicleType.VAN;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("190.00", String.format("%.2f", parkingCharge));
	}

	@Test
	void givenMotorBikeVehicleTypeWhenDetailsContainsDayNightAndWeekendRatesThenCalculateParkingCharge() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 13, 8, 30);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 16, 11, 20);

        VehicleType vehicleType = VehicleType.MOTORBIKE;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("47.50", String.format("%.2f", parkingCharge));
	}
	
	@Test
	void givenVehicleTypeWhenEnteredNightTimeLessAndExitDayTimeMoreForOneHourThenCalculateParkingChargeForDayRate() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 23, 8, 40);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 23, 9, 30);

        VehicleType vehicleType = VehicleType.CAR;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("2.00", String.format("%.2f", parkingCharge));
	}

	@Test
	void givenVehicleTypeWhenEnteredNightTimeMoreAndExitDayTimeLessForOneHourThenCalculateParkingChargeForNightRate() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 23, 16, 40);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 23, 17, 30);

        VehicleType vehicleType = VehicleType.CAR;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("1.50", String.format("%.2f", parkingCharge));
	}

	@Test
	void givenVehicleTypeWhenEnteredNightTimeAndExitDayTimeEqualForOneHourThenCalculateParkingChargeForDayRate() {
		LocalDateTime entryTime =
                LocalDateTime.of(2026, 11, 23, 16, 40);

        LocalDateTime exitTime =
                LocalDateTime.of(2026, 11, 23, 17, 20);

        VehicleType vehicleType = VehicleType.CAR;
        ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
        float parkingCharge = parkingFeeCalculator.calculateParkingCharge(
                vehicleType,
                entryTime,
                exitTime);
        assertEquals("2.00", String.format("%.2f", parkingCharge));
	}

}
