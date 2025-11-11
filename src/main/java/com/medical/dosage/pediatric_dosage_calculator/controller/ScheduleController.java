package com.medical.dosage.pediatric_dosage_calculator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin("*")
public class ScheduleController {

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody Map<String, Object> body){

        int dosesPerDay = Integer.parseInt(body.get("dosesPerDay").toString());
        String startTime = body.get("startTime") != null ? body.get("startTime").toString() : null;

        List<String> schedule = makeSchedule(dosesPerDay, startTime);

        return ResponseEntity.ok(Map.of(
                "schedule", schedule
        ));
    }


    private List<String> makeSchedule(int dosesPerDay, String startTime){
        List<String> result = new ArrayList<>();

        LocalTime start;
        if(startTime == null || startTime.isBlank()){
            start = LocalTime.now().withSecond(0).withNano(0);
        } else {
            start = LocalTime.parse(startTime);
        }

        double interval = 24.0 / dosesPerDay;

        for(int i=0; i<dosesPerDay; i++){
            LocalTime t = start.plusMinutes((long)(interval * 60 * i));
            result.add(t.toString().substring(0,5)); // "HH:mm"
        }

        return result;
    }

}
