package com.booking_care.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SignUpRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;
    @NotNull
    @NotBlank(message = "username không được để trống")
    @Size(min = 6,message = "username ít nhất 6 kí tự")
    private String username;
    @NotNull
    @NotBlank(message = "password không được để trống")
    @Size(min = 6,message = "password ít nhất 6 kí tự")
    private String password;
    @NotNull
    @NotBlank(message = "ngay sinh không được để trống")
    private String ngaySinh;
    @NotNull
    @NotBlank(message = "sdt không được để trống")
    @Size(min = 10,message = "sdt ít nhất 10 số")
    private String sdt;
    @NotNull
    @NotBlank(message = "email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
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
    
}
