package org.janitha.mega.megacity.service;



import org.janitha.mega.megacity.dto.CarDTO;

import java.util.List;

public interface CarUserFilteringService {
    List<CarDTO> getAllCars();
    List<CarDTO> filterCarsByStatus(String status);
    List<CarDTO> filterCarsByModel(String model);

    List<CarDTO> filterCarsByStatusAndModel(String status, String model);
}
