package com.concordia.TravelBookingSystem.Activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/activities")
@CrossOrigin(origins="*")
public class ActivitiesController {

    @Autowired
    ActivitiesService activitiesService;
    

    @PostMapping(path = "/addActivity")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> addActivity(@RequestBody Activities activity) {
    	HttpHeaders headers = new HttpHeaders();
    	try {
    	    activitiesService.addActivities(activity);
    	    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(activity);
    	} catch (Exception e) {
    	    headers.add("Message", "false");
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to add activity");
    	}
    }

    @GetMapping(path = "/findActivityById")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> findActivityById(@RequestParam int activitiesId) {
    	HttpHeaders headers = new HttpHeaders();
    	try {
    	    Activities activity = activitiesService.findActivitiesById(activitiesId);
    	    if (activity != null) {
    	        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(activity);
    	    } else {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("Activity not found");
    	    }
    	} catch (Exception e) {
    	    headers.add("Message", "false");
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to find activity");
    	}
    }

    @GetMapping(path = "/findAllActivities")
    @CrossOrigin    
    @ResponseBody
    public ResponseEntity<?> findAllActivities() {
    	HttpHeaders headers = new HttpHeaders();
    	try {
    	    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(activitiesService.findAllActivities());
    	} catch (Exception e) {
    	    headers.add("Message", "false");
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to find all activities");
    	}
    }

    @PostMapping(path = "/updateActivity")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> updateActivity(@RequestBody Activities activity) {
    	HttpHeaders headers = new HttpHeaders();
    	try {
    	    Activities updatedActivity = activitiesService.updateActivities(activity);
    	    if (updatedActivity != null) {
    	        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(updatedActivity);
    	    } else {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("Activity not found");
    	    }
    	} catch (Exception e) {
    	    headers.add("Message", "false");
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to update activity");
    	}
    }
    
    @PostMapping(path = "/deleteActivity")
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> deleteActivity(@RequestParam int activitiesId) {
        HttpHeaders headers = new HttpHeaders();
        try {
            activitiesService.deleteActivities(activitiesId);
            return ResponseEntity.ok().headers(headers).body("{\"message\": \"Activity deleted successfully\"}");
        } catch (Exception e) {
            headers.add("Message", "false");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("{\"error\": \"Failed to delete activity\"}");
        }
    }
}
