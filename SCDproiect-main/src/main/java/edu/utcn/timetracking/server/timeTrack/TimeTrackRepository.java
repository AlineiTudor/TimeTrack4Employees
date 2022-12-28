package edu.utcn.timetracking.server.timeTrack;

import org.apache.catalina.User;
import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TimeTrackRepository extends JpaRepository<TimeTrack, Integer> {
    @Query(value="Select ifnull(sum(timestampdiff(second,check_in,check_out))/3600,0) from time_track where check_date between ?#{[1]} and ?#{[0]} and employee_id=?#{[2]}",nativeQuery = true)
    double getHours(String from,String to,int id);
}
