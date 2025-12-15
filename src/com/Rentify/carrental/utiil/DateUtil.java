package com.Rentify.carrental.utiil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static int daysRented(LocalDate startDate, LocalDate returnDate){
        int days = (int) ChronoUnit.DAYS.between(startDate, returnDate);
        return days;
    }

}
