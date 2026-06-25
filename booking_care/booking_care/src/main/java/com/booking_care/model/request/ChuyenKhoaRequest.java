package com.booking_care.model.request;

import com.booking_care.model.ChuyenKhoa;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChuyenKhoaRequest {
    private String ten;
    private String moTa;

    public ChuyenKhoa toChuyenKhoa() {
        ChuyenKhoa chuyenKhoa = new ChuyenKhoa();
        chuyenKhoa.setTen(this.ten);
        chuyenKhoa.setMoTa(this.moTa);
        return chuyenKhoa;
    }

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
    
    
}
