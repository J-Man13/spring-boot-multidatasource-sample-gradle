package com.fuad.muldatasource.repository.booking;

import com.fuad.muldatasource.model.entities.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Long> {

    List findByCreatedBy(Long userId);
}
