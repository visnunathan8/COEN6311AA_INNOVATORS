package com.concordia.TravelBookingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.concordia.TravelBookingSystem.entity.Activities;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Integer> {
}
