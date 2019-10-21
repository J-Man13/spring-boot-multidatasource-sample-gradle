package com.fuad.muldatasource.service;

import com.fuad.muldatasource.model.entities.user.UserEntity;
import com.fuad.muldatasource.repository.booking.BookingRepository;
import com.fuad.muldatasource.repository.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired(required = true)
    private UsersRepository usersRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List findUserBookings(String emailId)
    {
        UserEntity userEntity = usersRepository.findByEmail(emailId);
        List bookings = bookingRepository.findByCreatedBy(userEntity.getId());
        return bookings;
    }
}
