package com.fuad.muldatasource.controller;

import com.fuad.muldatasource.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity findUserBookings(@PathVariable(name = "email", value = "email")String email)
    {
        List bookings = bookingService.findUserBookings(email);
        return new ResponseEntity(bookings, HttpStatus.OK);
    }
}
