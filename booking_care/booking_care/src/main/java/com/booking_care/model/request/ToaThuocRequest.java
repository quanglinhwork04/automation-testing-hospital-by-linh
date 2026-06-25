package com.booking_care.model.request;

import java.util.List;

public class ToaThuocRequest {
    private String lichKhamId;
    private String benhNhanEmail;
    private List<ThuocItem> thuocList;
    private String chanDoan;

    public String getLichKhamId() { return lichKhamId; }
    public void setLichKhamId(String lichKhamId) { this.lichKhamId = lichKhamId; }
    public String getBenhNhanEmail() { return benhNhanEmail; }
    public void setBenhNhanEmail(String benhNhanEmail) { this.benhNhanEmail = benhNhanEmail; }
    public List<ThuocItem> getThuocList() { return thuocList; }
    public void setThuocList(List<ThuocItem> thuocList) { this.thuocList = thuocList; }
    public String getChanDoan() { return chanDoan; }
    public void setChanDoan(String chanDoan) { this.chanDoan = chanDoan; }

    public static class ThuocItem {
        private Integer thuocId;
        private String donViTinh;
        private Integer soLuong;
        private String hdsd;

        public Integer getThuocId() { return thuocId; }
        public void setThuocId(Integer thuocId) { this.thuocId = thuocId; }
        public String getDonViTinh() { return donViTinh; }
        public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }
        public Integer getSoLuong() { return soLuong; }
        public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
        public String getHdsd() { return hdsd; }
        public void setHdsd(String hdsd) { this.hdsd = hdsd; }
    }
}