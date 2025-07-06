package com.ndungutse.restaurant_service.service;

import com.ndungutse.restaurant_service.dto.RestaurantDto;
import com.ndungutse.restaurant_service.dto.RestaurantRequestDto;
import com.ndungutse.restaurant_service.mapper.RestaurantMapper;
import com.ndungutse.restaurant_service.model.Restaurant;
import com.ndungutse.restaurant_service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    // Create a new restaurant
    public RestaurantDto createRestaurant(RestaurantRequestDto requestDto) {
        Restaurant restaurant = restaurantMapper.toEntity(requestDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(savedRestaurant);
    }

    // Get all restaurants
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toDtoList(restaurants);
    }

    // Get restaurant by ID
    public Optional<RestaurantDto> getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDto);
    }

    // Update restaurant
    public RestaurantDto updateRestaurant(Long id, RestaurantRequestDto requestDto) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    restaurantMapper.updateEntityFromRequestDto(restaurant, requestDto);
                    Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
                    return restaurantMapper.toDto(updatedRestaurant);
                })
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    // Delete restaurant by ID
    public void deleteRestaurant(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
        } else {
            throw new RuntimeException("Restaurant not found with id: " + id);
        }
    }

    // Check if restaurant exists
    public boolean existsById(Long id) {
        return restaurantRepository.existsById(id);
    }
}
