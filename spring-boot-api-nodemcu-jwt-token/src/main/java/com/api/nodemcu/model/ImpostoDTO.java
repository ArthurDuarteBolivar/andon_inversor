package com.api.nodemcu.model;

import lombok.Data;

@Data
public class ImpostoDTO {
    private Integer imposto;

    private Integer TCimposto;

    private Integer shiftTime;

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
