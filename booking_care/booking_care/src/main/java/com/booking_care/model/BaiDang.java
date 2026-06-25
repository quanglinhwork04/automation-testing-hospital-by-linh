package com.booking_care.model;

import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;

@Table(name = "bai_dang")
@Entity
@Data
public class BaiDang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ten;
    private String noiDung;
    private String tomTat;
    @JoinColumn(name = "chuyen_muc_id")
    @ManyToOne
    private ChuyenMuc chuyenMuc;

    private Date createAt;
    private String writerBy;
    private String photo;

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/baidang-photos/" + id + "/" + photo;
    }

    @PrePersist
    void createAt() {
        createAt = new Date();
//        writerBy = SecurityContextHolder.getContext().getAuthentication().getName();
    }

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

	public ChuyenMuc getChuyenMuc() {
		return chuyenMuc;
	}

	public void setChuyenMuc(ChuyenMuc chuyenMuc) {
		this.chuyenMuc = chuyenMuc;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getWriterBy() {
		return writerBy;
	}

	public void setWriterBy(String writerBy) {
		this.writerBy = writerBy;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
    
}
