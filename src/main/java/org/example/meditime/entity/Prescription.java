package org.example.meditime.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    Integer medicineId;

    @Column(name = "medicine_name")
    String medicineName;

    @Column(name = "medicine_composition")
    String medicineComposition;

    @Column(name = "stock_count")
    Integer stockCount;

    @Column(name = "medication_hour")
    String medicationHour;

    @Column(name = "medication_duration")
    Integer medicationDuration;

    @Column(name = "food_intake")
    String foodIntake;

    @Column(name = "medication_dose")
    Integer medicationDose;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt;

}
