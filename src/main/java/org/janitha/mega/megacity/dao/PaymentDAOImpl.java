package org.janitha.mega.megacity.dao;

import org.janitha.mega.megacity.config.DBConfig;
import org.janitha.mega.megacity.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

//    private static final String INSERT_PAYMENT = "INSERT INTO payments (booking_id, user_id, payment_amount, currency, payment_method, payment_status, transaction_id, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//    private static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id = ?";
//    private static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payments";
//    private static final String UPDATE_PAYMENT = "UPDATE payments SET payment_status = ? WHERE id = ?";
//    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE id = ?";
private static final String INSERT_PAYMENT = "INSERT INTO payments (bookingId, userId, paymentAmount, currency, paymentMethod, paymentStatus, transactionId, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id = ?";
    private static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payments";
    private static final String UPDATE_PAYMENT = "UPDATE payments SET payment_status = ? WHERE id = ?";
    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE id = ?";


//    @Override
//    public void createPayment(Payment payment) {
//        // Check if the booking_id exists in the bookings table
//        boolean bookingExists = false;
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement checkStmt = conn.prepareStatement("SELECT 1 FROM bookings WHERE id = ?")) {
//
//            checkStmt.setInt(1, payment.getBookingId());
//            ResultSet rs = checkStmt.executeQuery();
//
//            if (rs.next()) {
//                bookingExists = true; // Booking exists
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (!bookingExists) {
//            throw new IllegalArgumentException("Invalid booking ID: " + payment.getBookingId());
//        }
//
//        // Proceed with inserting the payment if the booking_id is valid
//        try (Connection conn = DBConfig.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {
//
//            stmt.setInt(1, payment.getBookingId());
//            stmt.setInt(2, payment.getUserId());
//            stmt.setDouble(3, payment.getPaymentAmount());
//            stmt.setString(4, payment.getCurrency());
//            stmt.setString(5, payment.getPaymentMethod());
//            stmt.setString(6, payment.getPaymentStatus());
//            stmt.setString(7, payment.getTransactionId());
//            stmt.setString(8, payment.getRemarks());
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean createPayment(Payment payment) {
        // Check if the booking_id exists in the bookings table
        boolean bookingExists = false;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT 1 FROM bookings WHERE id = ?")) {

            checkStmt.setInt(1, payment.getBookingId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                bookingExists = true; // Booking exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there's an error checking booking existence
        }

        if (!bookingExists) {
            throw new IllegalArgumentException("Invalid booking ID: " + payment.getBookingId());
        }

        // Proceed with inserting the payment if the booking_id is valid
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setInt(2, payment.getUserId());
            stmt.setDouble(3, payment.getPaymentAmount());
            stmt.setString(4, payment.getCurrency());
            stmt.setString(5, payment.getPaymentMethod());
            stmt.setString(6, payment.getPaymentStatus());
            stmt.setString(7, payment.getTransactionId());
            stmt.setString(8, payment.getRemarks());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if insertion fails
        }
    }

    @Override
    public Payment getPaymentById(int id) {
        Payment payment = null;
        System.out.println("Fetching payment with ID: " + id);  // Log the ID being fetched

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PAYMENT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payment = new Payment(
//                        rs.getInt("id"),
//                        rs.getInt("booking_id"),
//                        rs.getInt("user_id"),
//                        rs.getDouble("payment_amount"),
//                        rs.getString("currency"),
//                        rs.getString("payment_method"),
//                        rs.getTimestamp("payment_date"),
//                        rs.getString("payment_status"),
//                        rs.getString("transaction_id"),
//                        rs.getString("remarks"),
//                        rs.getTimestamp("created_at"),
//                        rs.getTimestamp("updated_at")
                        rs.getInt("id"),
                        rs.getInt("bookingId"),
                        rs.getInt("userId"),
                        rs.getDouble("paymentAmount"),
                        rs.getString("currency"),
                        rs.getString("paymentMethod"),
                        rs.getTimestamp("paymentDate"),
                        rs.getString("paymentStatus"),
                        rs.getString("transactionId"),
                        rs.getString("remarks"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                );
                System.out.println("Payment found: " + payment);  // Log the payment object fetched
            } else {
                System.out.println("No payment found with ID: " + id);  // Log if no payment is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }


    @Override
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_PAYMENTS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
//                        rs.getInt("id"),
//                        rs.getInt("booking_id"),
//                        rs.getInt("user_id"),
//                        rs.getDouble("payment_amount"),
//                        rs.getString("currency"),
//                        rs.getString("payment_method"),
//                        rs.getTimestamp("payment_date"),
//                        rs.getString("payment_status"),
//                        rs.getString("transaction_id"),
//                        rs.getString("remarks"),
//                        rs.getTimestamp("created_at"),
//                        rs.getTimestamp("updated_at")
                        rs.getInt("id"),
                        rs.getInt("bookingId"),
                        rs.getInt("userId"),
                        rs.getDouble("paymentAmount"),
                        rs.getString("currency"),
                        rs.getString("paymentMethod"),
                        rs.getTimestamp("paymentDate"),
                        rs.getString("paymentStatus"),
                        rs.getString("transactionId"),
                        rs.getString("remarks"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public void updatePayment(Payment payment) {
        if (payment.getPaymentStatus() == null || payment.getPaymentStatus().isEmpty()) {
            throw new IllegalArgumentException("Payment status cannot be null or empty.");
        }

        System.out.println("Updating payment with ID: " + payment.getId() + " to status: " + payment.getPaymentStatus());

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PAYMENT)) {

            stmt.setString(1, payment.getPaymentStatus());
            stmt.setInt(2, payment.getId());

            // Print SQL query for debugging
            System.out.println("Executing update query: " + stmt.toString());

            int rowsUpdated = stmt.executeUpdate();

            // Log the number of affected rows
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void deletePayment(int id) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_PAYMENT)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public Payment createPaymentV2(Payment payment) {
//        return null;
//    }

    @Override
    public Payment createPaymentV2(Payment payment) {
        // Check if the booking_id exists in the bookings table
        boolean bookingExists = false;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT 1 FROM bookings WHERE id = ?")) {

            checkStmt.setInt(1, payment.getBookingId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                bookingExists = true; // Booking exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking booking existence", e); // Throw an exception in case of error
        }

        if (!bookingExists) {
            throw new IllegalArgumentException("Invalid booking ID: " + payment.getBookingId());
        }

        // Proceed with inserting the payment if the booking_id is valid
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getBookingId());
            stmt.setInt(2, payment.getUserId());
            stmt.setDouble(3, payment.getPaymentAmount());
            stmt.setString(4, payment.getCurrency());
            stmt.setString(5, payment.getPaymentMethod());
            stmt.setString(6, payment.getPaymentStatus());
            stmt.setString(7, payment.getTransactionId());
            stmt.setString(8, payment.getRemarks());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setId(generatedKeys.getInt(1)); // Set the generated payment ID
                    }
                }
                return payment; // Return the payment object with the generated ID
            } else {
                throw new SQLException("Failed to insert payment, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting payment", e); // Throw an exception if insertion fails
        }
    }

}
