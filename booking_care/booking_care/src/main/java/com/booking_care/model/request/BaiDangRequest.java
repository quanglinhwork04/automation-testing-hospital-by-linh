package com.booking_care.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BaiDangRequest {
    private Integer id;
    private String ten;
    private String noiDung;
    private String tomTat;
    private Integer chuyenMucId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public String getTomTat() {
		return tomTat;
	}
	public void setTomTat(String tomTat) {
		this.tomTat = tomTat;
	}
	public Integer getChuyenMucId() {
		return chuyenMucId;
	}
	public void setChuyenMucId(Integer chuyenMucId) {
		this.chuyenMucId = chuyenMucId;
	}
    
}
