package com.api.nodemcu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="main_display")
public class MainModelDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private Integer imposto;

    private Integer TCimposto;

    private Integer shiftTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getImposto() {
        return imposto;
    }

    public void setImposto(Integer imposto) {
        this.imposto = imposto;
    }

    public Integer getTCimposto() {
        return TCimposto;
    }

    public void setTCimposto(Integer TCimposto) {
        this.TCimposto = TCimposto;
    }

    public Integer getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(Integer shiftTime) {
        this.shiftTime = shiftTime;
    }
}
