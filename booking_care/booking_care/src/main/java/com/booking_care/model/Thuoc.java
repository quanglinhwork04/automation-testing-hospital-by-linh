package com.booking_care.model;

import javax.persistence.*;

@Entity
@Table(name = "thuoc")
public class Thuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_thuoc", nullable = false)
    private String tenThuoc;

    @Column(name = "don_vi")
    private String donVi;

    @Column(name = "mo_ta")
    private String moTa;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTenThuoc() { return tenThuoc; }
    public void setTenThuoc(String tenThuoc) { this.tenThuoc = tenThuoc; }
    public String getDonVi() { return donVi; }
    public void setDonVi(String donVi) { this.donVi = donVi; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}