package org.janitha.mega.megacity.service;



import org.janitha.mega.megacity.entity.Payment;

import java.util.List;

public interface PaymentService {
    void processPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    void updatePaymentStatus(int paymentId, String status);
    void deletePayment(int id);
}
