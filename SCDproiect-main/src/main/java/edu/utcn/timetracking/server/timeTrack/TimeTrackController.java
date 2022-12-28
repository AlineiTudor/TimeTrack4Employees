package edu.utcn.timetracking.server.timeTrack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/timetracking")
public class TimeTrackController {
    @Autowired
    private TimeTrackService timeTrackService;

    @GetMapping
    public List<TimeTrack> getAllTimeTrackings() {
        return timeTrackService.findAllTimeTracks();
    }

    @PostMapping
    public TimeTrack create(@RequestBody TimeTrack timeTrack) {
        return timeTrackService.create(timeTrack);
    }

    @GetMapping("/{id}")
    public double getHoursForEmployee(
            @PathVariable("id") int id,
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {

        return timeTrackService.getWorkedHours(to, from, id);
    }


}
