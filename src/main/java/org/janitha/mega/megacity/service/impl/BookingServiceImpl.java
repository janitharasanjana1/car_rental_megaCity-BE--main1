package org.janitha.mega.megacity.service.impl;



import org.janitha.mega.megacity.dao.BookingDAO;
import org.janitha.mega.megacity.dto.BookingDTO;
import org.janitha.mega.megacity.dto.CarDTO;
import org.janitha.mega.megacity.entity.Booking;
import org.janitha.mega.megacity.entity.User;
import org.janitha.mega.megacity.service.BookingService;
import org.janitha.mega.megacity.service.CarService;
import org.janitha.mega.megacity.service.UserService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private BookingDAO bookingDAO = new BookingDAO();
    private UserService userService = new UserServiceImpl();
    private CarService carService = new CarServiceImpl();
    // Convert DTO to Entity
    // Convert DTO to Entity
    private Booking toEntity(BookingDTO dto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Booking(
                0,  // ID (auto-generated by DB)
                dto.getUserId(),
                dto.getCarId(),
                new Date(sdf.parse(dto.getStartDate()).getTime()),  // Convert String to SQL Date
                new Date(sdf.parse(dto.getEndDate()).getTime()),    // Convert String to SQL Date
                dto.getTotalAmount(),
                dto.getStatus(),
                new Date(System.currentTimeMillis()),  // Set CreatedAt
                new Date(System.currentTimeMillis()),   // Set UpdatedAt
                dto.getDriverId()
        );
    }

    @Override
    public boolean createBooking(BookingDTO bookingDTO) {
        try {
            User user =userService.getDriverUsers();
            bookingDTO.setDriverId(user.getId());
            Booking booking = toEntity(bookingDTO);
            boolean isBookingCreated = bookingDAO.createBooking(booking);
            if(isBookingCreated){
                CarDTO carDTO =carService.getCarById(booking.getCarId());
                carDTO.setStatus("Unavailable");
                carService.updateCar(carDTO);
            }
            return isBookingCreated;

        } catch (ParseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // READ Booking by ID
    @Override
    public Booking getBookingById(int id) {
        return bookingDAO.getBookingById(id);
    }

    // READ All Bookings
    @Override
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }

    // UPDATE Booking
    @Override
    public boolean updateBooking(int id, BookingDTO bookingDTO) {
        try {
            Booking existingBooking = bookingDAO.getBookingById(id);
            if (existingBooking != null) {
                existingBooking.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(bookingDTO.getStartDate())));
                existingBooking.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(bookingDTO.getEndDate())));
                existingBooking.setTotalAmount(bookingDTO.getTotalAmount());
                existingBooking.setStatus(bookingDTO.getStatus());
                return bookingDAO.updateBooking(existingBooking);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE Booking
    @Override
    public boolean deleteBooking(int id) {
        return bookingDAO.deleteBooking(id);
    }

    @Override
    public boolean updateBookingStatus(int id, String status) {
        try {
            Booking existingBooking = bookingDAO.getBookingById(id);
            if (existingBooking != null) {
                existingBooking.setStatus(status);
                return bookingDAO.updateBooking(existingBooking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getAllBookings(int id) {
        return bookingDAO.getAllBookingsById(id);
    }
}
