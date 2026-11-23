package com.mhrglobal.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkingFeeCalculator {

	private static Logger logger = LoggerFactory.getLogger(ParkingFeeCalculator.class.getName());
    public float calculateParkingCharge(
            VehicleType vehicleType,
            LocalDateTime entryTime,
            LocalDateTime exitTime) {

        if (vehicleType == null ||
        		entryTime == null ||
            exitTime == null ||
            !exitTime.isAfter(entryTime)) {

            throw new IllegalArgumentException(
                    "Invalid parking details");
        }

        // Calculate actual parking duration
        long totalMinutes = ChronoUnit.MINUTES.between(
                entryTime, exitTime);

        // Round total duration up to the next hour
        long totalHours = (totalMinutes + 59) / 60;

        float totalAmount = 0.0f;

        LocalDateTime current = entryTime;

        logger.info("Vehicle Type : {}" , vehicleType);
        logger.info("Entry Time   : {}" , entryTime);
        logger.info("Exit Time    : {}" , exitTime);
        logger.info("Total Minutes: {}" , totalMinutes);
        logger.info("Chargeable Hours: {}" , totalHours);

        for (int hour = 1; hour <= totalHours; hour++) {

            // Calculate actual minutes in this charging block
            long remainingMinutes = ChronoUnit.MINUTES.between(
                    current, exitTime);

            long blockMinutes = Math.min(60, remainingMinutes);

            // Count minutes belonging to each parking category
            Map<ParkingPeriod, Long> periodMinutes =
                    new EnumMap<>(ParkingPeriod.class);

            for (ParkingPeriod period : ParkingPeriod.values()) {
                periodMinutes.put(period, 0L);
            }

            LocalDateTime blockEnd =
                    current.plusMinutes(blockMinutes);

            LocalDateTime minute = current;

            while (minute.isBefore(blockEnd)) {

                ParkingPeriod period =
                        ParkingPeriod.getPeriod(minute);

                periodMinutes.put(
                        period,
                        periodMinutes.get(period) + 1
                );

                minute = minute.plusMinutes(1);
            }

            // Identify the applicable tariff
            ParkingPeriod selectedPeriod = null;
            long maxMinutes = -1;
            float highestTariff = -1.0f;

            for (ParkingPeriod period : ParkingPeriod.values()) {

                long minutes = periodMinutes.get(period);

                float tariff = vehicleType.getTariff(period);

                if (minutes > maxMinutes ||
                    (minutes == maxMinutes &&
                     minutes > 0 &&
                     tariff > highestTariff)) {

                    maxMinutes = minutes;
                    highestTariff = tariff;
                    selectedPeriod = period;
                }
            }
            // Calculate amount for this charging block
            float amount = highestTariff;

            totalAmount += amount;

            logger.info(
                    "Hour: {} | Period: {} | Minutes: {} | Amount: {}", hour , selectedPeriod, blockMinutes , String.format("%.2f", amount));

            current = blockEnd;
        }
        logger.info(
                "TOTAL PARKING AMOUNT: {}", String.format("%.2f", totalAmount));
        return totalAmount;
    }
}