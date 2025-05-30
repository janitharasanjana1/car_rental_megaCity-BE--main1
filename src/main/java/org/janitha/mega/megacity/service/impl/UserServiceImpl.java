package org.janitha.mega.megacity.service.impl;


import jakarta.mail.MessagingException;
import org.janitha.mega.megacity.dao.OtpDAO;
import org.janitha.mega.megacity.dao.UserDAO;
import org.janitha.mega.megacity.dto.ProfileImageDTO;
import org.janitha.mega.megacity.entity.ProfileImage;
import org.janitha.mega.megacity.entity.User;
import org.janitha.mega.megacity.service.EmailService;
import org.janitha.mega.megacity.service.UserService;
import org.janitha.mega.megacity.entity.OTP;

import java.sql.SQLException;


public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAO();
    private final OtpDAO otpDAO = new OtpDAO();
    private EmailService emailService;

    @Override
    public boolean registerUser(User user) {
        System.out.println(user+"service impl");
        return userDAO.save(user);
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = userDAO.findByEmail(email); // Get user by email
        System.out.println("authenticateUser"+email+password);
        if (user == null) {
            return null; // If no user is found, return null
        }

        // Check if password matches using BCrypt
        if (userDAO.checkPassword(user, password)) {
            System.out.println("authenticateUser BCrypt "+email+password);
            return user; // Return user if password is correct
        }
        return null; // Return null if password is incorrect
    }



    @Override
    public User getUserByUsername(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
//    @Override
//    public UserDTO getUserByUsername(String username) {
//        User user = userDAO.findByUsername(username);
//        return (user != null) ? new UserDTO(user.getUsername(), user.getEmail(), user.getPhone()) : null;
//    }



    @Override
    public boolean uploadProfileImage(String email, String imagePath) {
        return userDAO.insertProfileImage(email, imagePath);  // Save profile image to DB
    }

    @Override
    public ProfileImageDTO getProfileImage(String email) {
        ProfileImage profileImage = userDAO.getProfileImage(email);  // Retrieve profile image from DB
        if (profileImage != null) {
            ProfileImageDTO profileImageDTO = new ProfileImageDTO();
            profileImageDTO.setImageId(profileImage.getImageId());
            profileImageDTO.setUserEmail(profileImage.getUserEmail());
            profileImageDTO.setImagePath(profileImage.getImagePath());
            profileImageDTO.setUploadDate(profileImage.getUploadDate());
            return profileImageDTO;
        }
        return null;  // Return null if no profile image is found for the user
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // Generate 6-digit verification code
//        String verificationCode = String.format("%06d", new Random().nextInt(1000000));

        // Save or update OTP in the database
        OtpDAO otpDAO = new OtpDAO();
        String otpSaved = otpDAO.generateOtp(email, user.getId());  // Save the OTP in DB
        if (otpSaved==null) {
            throw new RuntimeException("Failed to save OTP for email: " + email);
        }

        // Email content
        String subject = "Password Reset Verification Code";
        String message = "Your password reset verification code is: " + otpSaved;

        // Send email with the verification code
        emailService.sendEmail(user.getEmail(), subject, message);

        // Return the verification code (OTP)
        return otpSaved;
    }


    @Override
    public boolean verifyOtp(String email, String otpCode) {
        System.out.println("In Side service ");
        boolean isValid = otpDAO.validateOtp(email, otpCode);
        if (!isValid) {
            throw new IllegalArgumentException("Invalid or expired OTP.");
        }
        return true;
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        OTP otp = otpDAO.findOtpByEmail(email);
        if (otp == null) {
            throw new IllegalArgumentException("No OTP verification found. Please verify OTP first.");
        }

        // Update Password
        boolean passwordUpdated = userDAO.updatePassword(email, newPassword);
        if (passwordUpdated) {
            otpDAO.deleteOtpByEmail(email); // Delete OTP after successful password change
        }

        return passwordUpdated;
    }

    @Override
    public User getDriverUsers() throws SQLException {
        User user = userDAO.getRandomDriver();  // Retrieve profile image from DB
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        User user = userDAO.getUserById(id);
        if(user !=null){
            return user;
        }
        return null;
    }
}
