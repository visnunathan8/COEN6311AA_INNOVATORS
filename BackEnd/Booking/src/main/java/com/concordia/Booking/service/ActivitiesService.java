package com.concordia.TravelBookingSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concordia.TravelBookingSystem.entity.Activities;
import com.concordia.TravelBookingSystem.repository.ActivitiesRepository;

@Service
public class ActivitiesService {

    private ActivitiesRepository activitiesRepository;

    @Autowired
    public ActivitiesService(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    public void addActivities(Activities activities) {
        activitiesRepository.save(activities);
    }

    public Activities findActivitiesById(int activitiesId) {
        return activitiesRepository.findById(activitiesId).orElse(null);
    }

    public List<Activities> findAllActivities() {
        return activitiesRepository.findAll();
    }

    public Activities updateActivities(Activities activities) {
        return activitiesRepository.save(activities);
    }

    public void deleteActivities(int activitiesId) {
        activitiesRepository.deleteById(activitiesId);
    }
}
