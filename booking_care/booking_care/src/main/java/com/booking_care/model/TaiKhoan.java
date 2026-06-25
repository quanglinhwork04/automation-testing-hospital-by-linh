package com.booking_care.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;


@Data
@Entity
@Table(name="tai_khoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @OneToOne
    @JoinColumn(name = "id_vai_tro")
    private VaiTro vaiTro;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public VaiTro getVaiTro() {
		return vaiTro;
	}
	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}
	public Integer getProvider() {
		return provider;
	}
	public void setProvider(Integer provider) {
		this.provider = provider;
	}
	public boolean hasRole(String tenVaiTro) {
            if (vaiTro.getTen().equals(tenVaiTro)) {
                return true;
            }
        return false;

    }
    private Integer provider;
}
