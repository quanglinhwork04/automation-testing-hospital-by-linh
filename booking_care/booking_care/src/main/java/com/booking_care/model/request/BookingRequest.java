package com.booking_care.model.request;

import com.booking_care.utils.Utils;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import java.util.Date;

@Data
public class BookingRequest {
    private Integer chuyenKhoaId;
    private String ngayKham;
    private Integer khungGioKham;
    private String moTaTrieuChung;
    private Integer bacSyId;
    private String tienKham;

    public boolean isValid() {
        Date ngayKham = Utils.stringToDate2(this.ngayKham);
        if(this.chuyenKhoaId == 0 || this.khungGioKham == null
                || this.bacSyId == 0 || Strings.isBlank(this.moTaTrieuChung)
                || Strings.isBlank(this.ngayKham)
                || this.moTaTrieuChung.length() < 2) {
            return false;
        }
        if(ngayKham.compareTo(new Date()) <= 0) {
            return false;
        }
		if (this.moTaTrieuChung.matches(".*[<>{}].*")) {
			return false; // Cấm các ký tự <, >, {, }
		}
        return true;
    }

	public Integer getChuyenKhoaId() {
		return chuyenKhoaId;
	}

	public void setChuyenKhoaId(Integer chuyenKhoaId) {
		this.chuyenKhoaId = chuyenKhoaId;
	}

	public String getNgayKham() {
		return ngayKham;
	}

	public void setNgayKham(String ngayKham) {
		this.ngayKham = ngayKham;
	}

	public Integer getKhungGioKham() {
		return khungGioKham;
	}

	public void setKhungGioKham(Integer khungGioKham) {
		this.khungGioKham = khungGioKham;
	}

	public String getMoTaTrieuChung() {
		return moTaTrieuChung;
	}

	public void setMoTaTrieuChung(String moTaTrieuChung) {
		this.moTaTrieuChung = moTaTrieuChung;
	}

	public Integer getBacSyId() {
		return bacSyId;
	}

	public void setBacSyId(Integer bacSyId) {
		this.bacSyId = bacSyId;
	}

	public String getTienKham() {
		return tienKham;
	}

	public void setTienKham(String tienKham) {
		this.tienKham = tienKham;
	}
    
}
