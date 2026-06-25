package com.booking_care.model;

import com.booking_care.model.request.BacSyResgisterRequest;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bac_sy")
@Data
public class BacSy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hoTen;
    private Date ngaySinh;
    private String sdt;
    private String email;
    private String chucVu;
    private Integer tienKham;
    @ManyToOne
    @JoinColumn(name = "chuyen_khoa_id")
    private ChuyenKhoa chuyenKhoa;
    private String noiKham = "";
    private String photo;
    private String chungChi;
    private String kinhNghiem;
    private String linhVucChuyenSau;

   // @Transient
 //   public String getPhotosImagePath() {
  //      if (photo == null || photo.trim().isEmpty() || id == null) return "image/user.png";
//
   //      return "/bacsy-photos/" + id + "/" + photo;
  //  }

    public BacSy() {
    }
    public BacSy(BacSyResgisterRequest request) {
        this.hoTen = request.getHoTen();
        this.sdt = request.getSdt();
        this.email = request.getEmail();
        this.chucVu = request.getChucVu();
        this.tienKham = request.getTienKham();
        this.noiKham = request.getNoiKham();
        this.photo = request.getPhoto();
        this.chungChi = request.getChungChi();
        this.kinhNghiem = request.getKinhNghiem();
        this.linhVucChuyenSau = request.getLinhVucChuyenSau();
    }

    @OneToOne
    @JoinColumn(name = "id_tai_khoan")
    private TaiKhoan taiKhoan;

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

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
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

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public Integer getTienKham() {
		return tienKham;
	}

	public void setTienKham(Integer tienKham) {
		this.tienKham = tienKham;
	}

	public ChuyenKhoa getChuyenKhoa() {
		return chuyenKhoa;
	}

	public void setChuyenKhoa(ChuyenKhoa chuyenKhoa) {
		this.chuyenKhoa = chuyenKhoa;
	}

	public String getNoiKham() {
		return noiKham;
	}

	public void setNoiKham(String noiKham) {
		this.noiKham = noiKham;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
    
    
}
