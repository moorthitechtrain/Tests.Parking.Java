package com.mhrglobal;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.mhrglobal.util.VehicleType;

class ParkingServiceTest {

	@Test
	void testParkingServiceChargesRunsSuccessfully() {
		ParkingService parkingService = new ParkingService();
		parkingService.processParkingProcess(VehicleType.CAR, LocalDateTime.of(2026, 11, 13, 8, 30));
	}
}
