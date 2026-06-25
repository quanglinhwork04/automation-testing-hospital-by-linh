package com.booking_care.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "toa_thuoc")
public class ToaThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lich_kham_id", nullable = false)
    private LichKham lichKham;

    @ManyToOne
    @JoinColumn(name = "benh_nhan_id", nullable = false)
    private BenhNhan benhNhan;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LichKham getLichKham() { return lichKham; }
    public void setLichKham(LichKham lichKham) { this.lichKham = lichKham; }
    public BenhNhan getBenhNhan() { return benhNhan; }
    public void setBenhNhan(BenhNhan benhNhan) { this.benhNhan = benhNhan; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}