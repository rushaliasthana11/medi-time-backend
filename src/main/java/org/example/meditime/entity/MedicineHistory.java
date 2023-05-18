package org.example.meditime.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "medicine_history")
public class MedicineHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "medicine_id")
    int medicineId;

    @Column(name = "progress")
    String progress;

    @Column(name = "dose_date")
    Date doseDate;

    @Column(name = "in_tray_hour")
    Integer inTrayHour;

    @Column(name = "dose_done_hour")
    Integer doseDoneHour;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;

}
