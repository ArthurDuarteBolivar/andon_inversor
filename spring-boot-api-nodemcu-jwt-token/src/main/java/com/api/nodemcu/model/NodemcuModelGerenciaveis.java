package com.api.nodemcu.model;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@Data
@Table(name="thdados_gerenciaveis")
public class NodemcuModelGerenciaveis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinTable(name = "name_id_gerenciaveis")
    private OperationModelGerenciaveis nameId;

    @ManyToOne
    @JoinTable(name = "fontesId")
    private FontesModel fontesId;

    private Integer count;

    private Integer time;

    private String state;

    private Date data;

    private Integer currentTC;

    private Integer maintenance;

    private Integer firtlastTC;

    private Integer secondtlastTC;

    private Integer thirdlastTC;

    private Integer shortestTC;

    private Integer QtdeTCexcedido;

    private Integer TCmedio;

    private Integer localTC;

    @PrePersist
    protected void prePersist() {
        if (this.data == null){
            TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
            data = new Date();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OperationModelGerenciaveis getNameId() {
        return nameId;
    }

    public void setNameId(OperationModelGerenciaveis nameId) {
        this.nameId = nameId;
    }

    public FontesModel getFontesId() {
        return fontesId;
    }

    public void setFontesId(FontesModel fontesId) {
        this.fontesId = fontesId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getCurrentTC() {
        return currentTC;
    }

    public void setCurrentTC(Integer currentTC) {
        this.currentTC = currentTC;
    }

    public Integer getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Integer maintenance) {
        this.maintenance = maintenance;
    }

    public Integer getFirtlastTC() {
        return firtlastTC;
    }

    public void setFirtlastTC(Integer firtlastTC) {
        this.firtlastTC = firtlastTC;
    }

    public Integer getSecondtlastTC() {
        return secondtlastTC;
    }

    public void setSecondtlastTC(Integer secondtlastTC) {
        this.secondtlastTC = secondtlastTC;
    }

    public Integer getThirdlastTC() {
        return thirdlastTC;
    }

    public void setThirdlastTC(Integer thirdlastTC) {
        this.thirdlastTC = thirdlastTC;
    }

    public Integer getShortestTC() {
        return shortestTC;
    }

    public void setShortestTC(Integer shortestTC) {
        this.shortestTC = shortestTC;
    }

    public Integer getQtdeTCexcedido() {
        return QtdeTCexcedido;
    }

    public void setQtdeTCexcedido(Integer qtdeTCexcedido) {
        QtdeTCexcedido = qtdeTCexcedido;
    }

    public Integer getTCmedio() {
        return TCmedio;
    }

    public void setTCmedio(Integer TCmedio) {
        this.TCmedio = TCmedio;
    }

    public Integer getLocalTC() {
        return localTC;
    }

    public void setLocalTC(Integer localTC) {
        this.localTC = localTC;
    }
}

