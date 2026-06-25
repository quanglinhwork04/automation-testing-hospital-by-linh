package com.booking_care.model;

import javax.persistence.*;

@Entity
@Table(name = "chi_tiet_toa_thuoc")
public class ChiTietToaThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "toa_thuoc_id", nullable = false)
    private ToaThuoc toaThuoc;

    @ManyToOne
    @JoinColumn(name = "thuoc_id", nullable = false)
    private Thuoc thuoc;

    @Column(name = "don_vi_tinh", nullable = false)
    private String donViTinh;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "hdsd", nullable = false)
    private String hdsd;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public ToaThuoc getToaThuoc() { return toaThuoc; }
    public void setToaThuoc(ToaThuoc toaThuoc) { this.toaThuoc = toaThuoc; }
    public Thuoc getThuoc() { return thuoc; }
    public void setThuoc(Thuoc thuoc) { this.thuoc = thuoc; }
    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public String getHdsd() { return hdsd; }
    public void setHdsd(String hdsd) { this.hdsd = hdsd; }
}