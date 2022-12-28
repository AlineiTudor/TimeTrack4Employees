package edu.utcn.timetracking.server.timeTrack;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class TimeTrackService {
    @Autowired
    private TimeTrackRepository timeTrackRepository;

    @Transactional
    public TimeTrack create(TimeTrack timeTrack) {
        return timeTrackRepository.save(timeTrack);
    }

    public List<TimeTrack> findAllTimeTracks() {
        return timeTrackRepository.findAll();
    }

    public double getWorkedHours(LocalDate from,LocalDate to,int id){
        int hours=(int)(timeTrackRepository.getHours(from.toString(), to.toString(), id)*10);
        return (double)hours/10;
    }
}
