package entity;

import java.io.Serializable;

public class ThanhThieuNien implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String hoTen; 
    private String gioiTinh; 
    private String ngaySinh; 
    private String noiO; 
    private String hoKhauThuongTru; 
    private String nguoiGiamHo; 
    private String sdtNguoiGiamHo; 
    private String quanHeVoiTre; 
    private String tinhTrangGiaDinh; 

    public ThanhThieuNien() {
    }

    public ThanhThieuNien(int id, String hoTen, String gioiTinh, String ngaySinh, String noiO, 
                           String hoKhauThuongTru, String nguoiGiamHo, String sdtNguoiGiamHo, 
                           String quanHeVoiTre, String tinhTrangGiaDinh) {
        this.id = id;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.noiO = noiO;
        this.hoKhauThuongTru = hoKhauThuongTru;
        this.nguoiGiamHo = nguoiGiamHo;
        this.sdtNguoiGiamHo = sdtNguoiGiamHo;
        this.quanHeVoiTre = quanHeVoiTre;
        this.tinhTrangGiaDinh = tinhTrangGiaDinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNoiO() {
        return noiO;
    }

    public void setNoiO(String noiO) {
        this.noiO = noiO;
    }

    public String getHoKhauThuongTru() {
        return hoKhauThuongTru;
    }

    public void setHoKhauThuongTru(String hoKhauThuongTru) {
        this.hoKhauThuongTru = hoKhauThuongTru;
    }

    public String getNguoiGiamHo() {
        return nguoiGiamHo;
    }

    public void setNguoiGiamHo(String nguoiGiamHo) {
        this.nguoiGiamHo = nguoiGiamHo;
    }

    public String getSdtNguoiGiamHo() {
        return sdtNguoiGiamHo;
    }

    public void setSdtNguoiGiamHo(String sdtNguoiGiamHo) {
        this.sdtNguoiGiamHo = sdtNguoiGiamHo;
    }

    public String getQuanHeVoiTre() {
        return quanHeVoiTre;
    }

    public void setQuanHeVoiTre(String quanHeVoiTre) {
        this.quanHeVoiTre = quanHeVoiTre;
    }

    public String getTinhTrangGiaDinh() {
        return tinhTrangGiaDinh;
    }

    public void setTinhTrangGiaDinh(String tinhTrangGiaDinh) {
        this.tinhTrangGiaDinh = tinhTrangGiaDinh;
    }
}
