package com.fuad.muldatasource.service;

import java.util.List;

public interface BookingService {

    List findUserBookings(String emailId);
}
