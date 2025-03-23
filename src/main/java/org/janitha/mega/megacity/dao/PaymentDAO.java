package org.janitha.mega.megacity.dao;



import org.janitha.mega.megacity.entity.Payment;

import java.util.List;

public interface PaymentDAO {
    boolean createPayment(Payment payment);
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    void updatePayment(Payment payment);
    void deletePayment(int id);
    Payment createPaymentV2(Payment payment);
}
