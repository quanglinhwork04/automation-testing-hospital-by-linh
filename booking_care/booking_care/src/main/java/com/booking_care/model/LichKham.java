package com.booking_care.model;

import com.booking_care.constant.ewewwewe.Status;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "lich_kham")
public class LichKham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer chuyenKhoaId;

	private String moTaTrieuChung;

	private Date thoiGianDk;

	private Date ngayKham;

	private Status status;

	private Integer khungGioKham;

	private Integer tienKham;

	private boolean isPaid;

	@ManyToOne
	@JoinColumn(name = "id_bac_sy")
	private BacSy bacSy;

	@ManyToOne
	@JoinColumn(name = "id_benh_nhan")
	private BenhNhan benhNhan;

	@Column(name = "chan_doan")
	private String chanDoan;

	@PrePersist
	void prePresit() {
		this.thoiGianDk = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChuyenKhoaId() {
		return chuyenKhoaId;
	}

	public void setChuyenKhoaId(Integer chuyenKhoaId) {
		this.chuyenKhoaId = chuyenKhoaId;
	}

	public String getMoTaTrieuChung() {
		return moTaTrieuChung;
	}

	public void setMoTaTrieuChung(String moTaTrieuChung) {
		this.moTaTrieuChung = moTaTrieuChung;
	}

	public Date getThoiGianDk() {
		return thoiGianDk;
	}

	public void setThoiGianDk(Date thoiGianDk) {
		this.thoiGianDk = thoiGianDk;
	}

	public Date getNgayKham() {
		return ngayKham;
	}

	public void setNgayKham(Date ngayKham) {
		this.ngayKham = ngayKham;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getKhungGioKham() {
		return khungGioKham;
	}

	public void setKhungGioKham(Integer khungGioKham) {
		this.khungGioKham = khungGioKham;
	}

	public Integer getTienKham() {
		return tienKham;
	}

	public void setTienKham(Integer tienKham) {
		this.tienKham = tienKham;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public BacSy getBacSy() {
		return bacSy;
	}

	public void setBacSy(BacSy bacSy) {
		this.bacSy = bacSy;
	}

	public BenhNhan getBenhNhan() {
		return benhNhan;
	}

	public void setBenhNhan(BenhNhan benhNhan) {
		this.benhNhan = benhNhan;
	}

	public String getChanDoan() {
		return chanDoan;
	}

	public void setChanDoan(String chanDoan) {
		this.chanDoan = chanDoan;
	}
}