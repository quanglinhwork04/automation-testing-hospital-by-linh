package com.booking_care.model.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class BacSyRequest {
    private Integer id;
    private String hoTen;
    private String ngaySinh;
    private String sdt;
    private String email;
    private String gioiThieu;
    private Integer chuyenKhoaId;
    private String chucVu;
    private String chungChi;
    private String kinhNghiem;
    private String linhVucChuyenSau;
    private Integer tienKham;
    private String noiKham;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGioiThieu() {
		return gioiThieu;
	}
	public void setGioiThieu(String gioiThieu) {
		this.gioiThieu = gioiThieu;
	}
	public Integer getChuyenKhoaId() {
		return chuyenKhoaId;
	}
	public void setChuyenKhoaId(Integer chuyenKhoaId) {
		this.chuyenKhoaId = chuyenKhoaId;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public String getChungChi() {
		return chungChi;
	}
	public void setChungChi(String chungChi) {
		this.chungChi = chungChi;
	}
	public String getKinhNghiem() {
		return kinhNghiem;
	}
	public void setKinhNghiem(String kinhNghiem) {
		this.kinhNghiem = kinhNghiem;
	}
	public String getLinhVucChuyenSau() {
		return linhVucChuyenSau;
	}
	public void setLinhVucChuyenSau(String linhVucChuyenSau) {
		this.linhVucChuyenSau = linhVucChuyenSau;
	}
	public Integer getTienKham() {
		return tienKham;
	}
	public void setTienKham(Integer tienKham) {
		this.tienKham = tienKham;
	}
	public String getNoiKham() {
		return noiKham;
	}
	public void setNoiKham(String noiKham) {
		this.noiKham = noiKham;
	}

}
