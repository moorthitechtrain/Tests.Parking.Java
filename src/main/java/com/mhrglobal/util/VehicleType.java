package com.mhrglobal.util;

public enum VehicleType {

    CAR(2.0f, 1.5f, 1.0f),
    MOTORBIKE(1.0f, 0.75f, 0.50f),
    VAN(4.0f, 3.0f, 2.0f);

    private final float dayTariff;
    private final float nightTariff;
    private final float weekendTariff;

    VehicleType(float dayTariff,
                float nightTariff,
                float weekendTariff) {

        this.dayTariff = dayTariff;
        this.nightTariff = nightTariff;
        this.weekendTariff = weekendTariff;
    }

    public float getTariff(ParkingPeriod period) {

        switch (period) {

            case DAY:
                return dayTariff;

            case NIGHT:
                return nightTariff;

            case WEEKEND:
                return weekendTariff;

            default:
                throw new IllegalArgumentException(
                        "Invalid parking period");
        }
    }

    public float getDayTariff() {
        return dayTariff;
    }

    public float getNightTariff() {
        return nightTariff;
    }

    public float getWeekendTariff() {
        return weekendTariff;
    }
}
