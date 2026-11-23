package com.mhrglobal;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mhrglobal.payment.CardPaymentService;
import com.mhrglobal.payment.PaymentService;
import com.mhrglobal.print.PaperPrintService;
import com.mhrglobal.print.PrintService;
import com.mhrglobal.util.ParkingFeeCalculator;
import com.mhrglobal.util.VehicleType;

//Implement Parking Service that makes use of the payment and printing services
public class ParkingService {

    PaymentService cardPaymentService = new CardPaymentService();
    PrintService paperPrintService = new PaperPrintService();
    ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();
    
    private Logger logger = LoggerFactory.getLogger(ParkingService.class.getName());
    
    public void processParkingProcess(VehicleType vehicleType, LocalDateTime vehicleEntryTime) {
    	try {
        	float parkingCharge = parkingFeeCalculator.calculateParkingCharge(vehicleType, vehicleEntryTime, LocalDateTime.now());
        	if (cardPaymentService.requestPayment(parkingCharge)) {
        		StringBuffer sb = new StringBuffer();
        		sb.append("Day Time Tariff :").append(vehicleType.getDayTariff()).append("\n")
        			.append("Night Time Tariff :").append(vehicleType.getNightTariff()).append("\n")
        			.append("Weekend Tariff :").append(vehicleType.getWeekendTariff()).append("\n");
        		
        		paperPrintService.requestPrinting(vehicleType.name(), sb.toString(), String.format(".2f", parkingCharge));
        	} else {
        		logger.info("Parking payment is failed");
        	}    		
    	} catch (IllegalArgumentException iae) {
    		logger.error("Invalid details are passed");
    	} catch (Exception e) {
    		logger.error("Technical error is occurred");
    	}
    }
}