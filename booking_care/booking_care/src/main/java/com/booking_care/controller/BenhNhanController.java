package com.booking_care.controller;

import com.booking_care.constant.Constants;
import com.booking_care.constant.ewewwewe.Status;
import com.booking_care.model.BenhNhan;
import com.booking_care.model.ChuyenKhoa;
import com.booking_care.model.LichKham;
import com.booking_care.model.TaiKhoan;
import com.booking_care.model.request.BenhNhanRequest;
import com.booking_care.model.request.BookingRequest;
import com.booking_care.model.request.DoiMatKhauRequest;
import com.booking_care.model.request.SignUpRequest;
import com.booking_care.repository.*;
import com.booking_care.security.CustomUserDetails;
import com.booking_care.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/")
public class BenhNhanController {

    @Autowired
    BenhNhanRepository benhNhanRepo;

    @Autowired
    BacSyRepository bacSyRepo;

    @Autowired
    LichKhamRepository lichKhamRepo;

    @Autowired
    TaiKhoanRepository taiKhoanRepo;

    @Autowired
    ChuyenKhoaRepository chuyenKhoaRepo;

    @Autowired
    VaiTroRepository vaiTroRepo;

    DateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping(value = {"", "trang-chu"})
    public String home(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null) {
            return "gioi-thieu";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        model.addAttribute("benhNhan", benhNhan);
        return "gioi-thieu";
    }

    @GetMapping("dang-ki")
    public String viewDangKi() {
        return "dang-ki";
    }

    @GetMapping("dang-nhap")
    public String viewDangNhap() {
        return "dang-nhap";
    }

    @ModelAttribute
    SignUpRequest signUpRequest() {
        return new SignUpRequest();
    }

    @ModelAttribute
    TaiKhoan taiKhoan() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof CustomUserDetails && ((CustomUserDetails) user).hasRole("BENH_NHAN")) {
            CustomUserDetails userDetails = (CustomUserDetails) user;
            return userDetails.getTaiKhoan();
        }
        return null;
    }

    @PostMapping("dang-ki")
    @Transactional
    public String dangKi(@ModelAttribute @Valid SignUpRequest signUpRequest,
                         Errors errors, Model model, RedirectAttributes redirectAttributes) throws ParseException {
        if (errors.hasErrors()) {
            return "dang-ki";
        }
        if (taiKhoanRepo.findByUsername(signUpRequest.getUsername()) != null) {
            model.addAttribute("loi", "Tài khoản đã tồn tại");
            return "dang-ki";
        }
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setUsername(signUpRequest.getUsername());
        taiKhoan.setPassword(signUpRequest.getPassword());
        taiKhoan.setVaiTro(vaiTroRepo.findById(Constants.VaiTro.ROLE_BENH_NHAN).orElseThrow(() -> new IllegalArgumentException("Vai trò không tồn tại")));
        taiKhoanRepo.save(taiKhoan);
        BenhNhan benhNhan = new BenhNhan();
        benhNhan.setHoTen(signUpRequest.getHoTen());
        benhNhan.setEmail(signUpRequest.getEmail());
        benhNhan.setNgaySinh(format.parse(signUpRequest.getNgaySinh()));
        benhNhan.setSdt(signUpRequest.getSdt());
        benhNhan.setTaiKhoan(taiKhoan);
        benhNhanRepo.save(benhNhan);
        redirectAttributes.addFlashAttribute("ok", "Tạo tài khoản mới thành công");
        return "redirect:/dang-ki";
    }

    @GetMapping("dat-lich")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String datLichKhambenh(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan, RedirectAttributes redirectAttributes) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng đăng nhập để đặt lịch");
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        if (benhNhan == null) {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy thông tin bệnh nhân");
            return "redirect:/dang-nhap";
        }
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepo.findAll();
        Map<Integer, String> khungGioKham = new LinkedHashMap<>();
        khungGioKham.put(1, "8h");
        khungGioKham.put(2, "9h");
        khungGioKham.put(3, "10h");
        khungGioKham.put(4, "11h");
        khungGioKham.put(5, "12h");
        khungGioKham.put(6, "13h");
        khungGioKham.put(7, "14h");
        khungGioKham.put(8, "15h");
        khungGioKham.put(9, "16h");
        khungGioKham.put(10, "17h");
        khungGioKham.put(11, "18h");
        khungGioKham.put(12, "19h");
        model.addAttribute("benhNhan", benhNhan);
        model.addAttribute("chuyenKhoaList", chuyenKhoaList);
        model.addAttribute("khungGioKham", khungGioKham);
        model.addAttribute("bookingRequest", new BookingRequest());
        return "booking";
    }

    @PostMapping("dat-lich")
    @Transactional
    public String dangKiKhamBenh(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                 @ModelAttribute @Valid BookingRequest bookingRequest,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) throws ParseException {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng đăng nhập để đặt lịch");
            return "redirect:/dang-nhap";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loi", "Thông tin đặt lịch không hợp lệ");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getChuyenKhoaId() == null || bookingRequest.getChuyenKhoaId() == 0) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng chọn chuyên khoa");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getBacSyId() == null || bookingRequest.getBacSyId() == 0) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng chọn bác sĩ");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getNgayKham() == null || bookingRequest.getNgayKham().isEmpty()) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng chọn ngày khám");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getMoTaTrieuChung().matches(".*[<>{}].*")) {
            redirectAttributes.addFlashAttribute("loi", "Mô tả triệu chứng không được chứa ký tự đặc biệt như <, >, {, }");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getKhungGioKham() == null || bookingRequest.getKhungGioKham() == 0 || bookingRequest.getKhungGioKham() > 12) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng chọn giờ khám hợp lệ");
            return "redirect:/dat-lich";
        }
        Date ngayKham;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng: 2023-12-01
            format.setLenient(false); // Không cho phép giá trị không hợp lệ
            ngayKham = format.parse(bookingRequest.getNgayKham());
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("loi", "Định dạng ngày tháng không hợp lệ");
            return "redirect:/dat-lich";
        }

        if (ngayKham.before(Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()))) {
            redirectAttributes.addFlashAttribute("loi", "Không thể đặt lịch cho ngày trong quá khứ");
            return "redirect:/dat-lich";
        }
        if (ngayKham.before(Date.from(LocalDate.now().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()))) {
            redirectAttributes.addFlashAttribute("loi", "Không thể đặt lịch cho ngày trong quá khứ");
            return "redirect:/dat-lich";
        }
        List<LichKham> lichKhamList = lichKhamRepo.findByNgayKhamAndBacSyIdAndKhungGioKham(
                ngayKham, bookingRequest.getBacSyId(), bookingRequest.getKhungGioKham());
        if (!lichKhamList.isEmpty()) {
            redirectAttributes.addFlashAttribute("loi", "Giờ khám này đã được đặt. Vui lòng chọn giờ khác.");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getMoTaTrieuChung() == null || bookingRequest.getMoTaTrieuChung().length() < 2) {
            redirectAttributes.addFlashAttribute("loi", "Mô tả triệu chứng phải có ít nhất 2 ký tự");
            return "redirect:/dat-lich";
        }
        if (bookingRequest.getMoTaTrieuChung() != null && bookingRequest.getMoTaTrieuChung().length() > 100) {
            redirectAttributes.addFlashAttribute("loi", "Mô tả triệu chứng không được vượt quá 100 ký tự");
            return "redirect:/dat-lich";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        if (benhNhan == null) {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy thông tin bệnh nhân");
            return "redirect:/dang-nhap";
        }
        LichKham lichKham = new LichKham();
        lichKham.setBenhNhan(benhNhan);
        lichKham.setBacSy(bacSyRepo.findById(bookingRequest.getBacSyId()).orElseThrow(() -> new IllegalArgumentException("Bác sĩ không tồn tại")));
        lichKham.setStatus(Status.CHO_XU_LY);
        lichKham.setKhungGioKham(bookingRequest.getKhungGioKham());
        lichKham.setChuyenKhoaId(bookingRequest.getChuyenKhoaId());
        lichKham.setMoTaTrieuChung(bookingRequest.getMoTaTrieuChung());
        lichKham.setNgayKham(ngayKham);
        lichKham.setTienKham(lichKham.getBacSy().getTienKham());
        lichKhamRepo.save(lichKham);
        redirectAttributes.addFlashAttribute("ok", "Đặt lịch thành công");
        return "redirect:/dat-lich";
    }

    @GetMapping("huyLichKham/{id}")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    @Transactional
    public String huyLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan, @PathVariable Integer id,
                              RedirectAttributes redirectAttributes) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng đăng nhập để hủy lịch");
            return "redirect:/dang-nhap";
        }
        LichKham lichKham = lichKhamRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        if (!lichKham.getBenhNhan().getTaiKhoan().getId().equals(taiKhoan.getTaiKhoan().getId())) {
            redirectAttributes.addFlashAttribute("loi", "Bạn không có quyền hủy lịch này");
            return "redirect:/lich-kham-benh";
        }
        lichKham.setStatus(Status.DA_HUY);
        lichKhamRepo.save(lichKham);
        redirectAttributes.addFlashAttribute("ok", "Hủy lịch khám thành công");
        return "redirect:/lich-kham-benh?status=2";
    }

    @GetMapping("lich-kham-benh")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String lichKhamBenh(Model model,
                               @AuthenticationPrincipal CustomUserDetails taiKhoan,
                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "status", required = false) Integer status) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        if (benhNhan == null) {
            return "redirect:/dang-nhap";
        }
        List<LichKham> lichKhamList;
        Sort sort = Sort.by(Sort.Direction.ASC, "thoiGianDk");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        if (status != null) {
            Status statusEnum;
            switch (status) {
                case 0:
                    statusEnum = Status.CHO_XU_LY;
                    break;
                case 1:
                    statusEnum = Status.DA_XAC_NHAN;
                    break;
                case 2:
                    statusEnum = Status.DA_HUY;
                    break;
                case 3:
                    statusEnum = Status.DA_KHAM;
                    break;
                default:
                    throw new IllegalArgumentException("Trạng thái không hợp lệ");
            }
            lichKhamList = lichKhamRepo.getAllLichKhamOfBenhNhanByStatus(benhNhan.getId(), statusEnum, pageable);
        } else {
            lichKhamList = lichKhamRepo.getAllLichKhamOfBenhNhan(benhNhan.getId(), pageable);
        }
        model.addAttribute("lichKhamList", lichKhamList);
        return "lich-kham-benh";
    }

    @GetMapping("profile")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String viewProfile(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        model.addAttribute("benhNhan", benhNhan);
        return "profile-benh-nhan";
    }

    @PostMapping("profile")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    @Transactional
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                @ModelAttribute(name = "benhNhan") @Valid BenhNhanRequest benhNhanRequest,
                                BindingResult bindingResult,
                                @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException, ParseException {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng đăng nhập để cập nhật hồ sơ");
            return "redirect:/dang-nhap";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loi", "Thông tin hồ sơ không hợp lệ");
            return "redirect:/profile";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        if (benhNhan == null) {
            redirectAttributes.addFlashAttribute("loi", "Không tìm thấy thông tin bệnh nhân");
            return "redirect:/dang-nhap";
        }
        if (file.isEmpty()) {
            benhNhanRepo.updateProfileBenhNhanKoUploadFile(benhNhanRequest.getDiaChi(), benhNhanRequest.getHoTen(),
                    format.parse(benhNhanRequest.getNgaySinh()), benhNhanRequest.getSdt(), benhNhanRequest.getEmail(), benhNhan.getId());
        } else {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "benhnhan-photos/" + benhNhan.getId();
            benhNhanRepo.updateProfileBenhNhan(benhNhanRequest.getDiaChi(), benhNhanRequest.getHoTen(),
                    format.parse(benhNhanRequest.getNgaySinh()), benhNhanRequest.getSdt(), benhNhanRequest.getEmail(),
                    fileName, benhNhan.getId());
            FileUploadUtil.saveFile(uploadDir, fileName, file);
        }
        redirectAttributes.addFlashAttribute("ok", "Cập nhật hồ sơ thành công");
        return "redirect:/profile";
    }

    @GetMapping("/doi-mat-khau")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String viewDoiMatKhau(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        model.addAttribute("benhNhan", benhNhan);
        model.addAttribute("doiMatKhauRequest", new DoiMatKhauRequest());
        return "doimatkhau-benh-nhan";
    }

    @PostMapping("/doi-mat-khau")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    @Transactional
    public String updateMatKhau(@ModelAttribute @Valid DoiMatKhauRequest doiMatKhauRequest,
                                BindingResult bindingResult,
                                Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan,
                                RedirectAttributes redirectAttributes) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            redirectAttributes.addFlashAttribute("loi", "Vui lòng đăng nhập để đổi mật khẩu");
            return "redirect:/dang-nhap";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("benhNhan", benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan()));
            return "doimatkhau-benh-nhan";
        }
        if (!doiMatKhauRequest.getMatKhauHienTai().equals(taiKhoan.getPassword())) {
            redirectAttributes.addFlashAttribute("loiMatKhau1", "Mật khẩu hiện tại không đúng");
            return "redirect:/doi-mat-khau";
        }
        taiKhoanRepo.updateMatKhau(doiMatKhauRequest.getMatKhauMoi(), taiKhoan.getTaiKhoan().getUsername());
        redirectAttributes.addFlashAttribute("ok", "Cập nhật mật khẩu thành công");
        return "redirect:/doi-mat-khau";
    }

    @PostMapping("/thanhToan")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String thanhToan(HttpServletRequest request,
                            @RequestParam("payment_method") String paymentMethod,
                            @RequestParam("tienKham") Integer tienKham,
                            @RequestParam("idLichKham") Integer idLichKham,
                            @AuthenticationPrincipal CustomUserDetails taiKhoan,
                            Model model) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        LichKham lichKham = lichKhamRepo.findById(idLichKham)
                .orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        if ("MOMO".equals(paymentMethod)) {
            model.addAttribute("msg", "Vui lòng quét mã QR Momo và xác nhận sau khi thanh toán.");
            model.addAttribute("momoDisplayId", idLichKham);
            model.addAttribute("paymentMethod", "MOMO");
        } else if ("ZALOPAY".equals(paymentMethod)) {
            model.addAttribute("msg", "Vui lòng quét mã QR ZaloPay và xác nhận sau khi thanh toán.");
            model.addAttribute("momoDisplayId", idLichKham);
            model.addAttribute("paymentMethod", "ZALOPAY");
        } else {
            model.addAttribute("msg", "Phương thức thanh toán không hợp lệ!");
        }
        model.addAttribute("lichKham", lichKham);
        return "thanhtoan";
    }

    @PostMapping("/confirmMomoPayment")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String confirmMomoPayment(@RequestParam("idLichKham") Integer idLichKham,
                                     @AuthenticationPrincipal CustomUserDetails taiKhoan,
                                     Model model) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        LichKham lichKham = lichKhamRepo.findById(idLichKham)
                .orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        lichKham.setPaid(true);
        lichKhamRepo.save(lichKham);
        model.addAttribute("msgierig", "Thanh toán Momo đã được xác nhận cho lịch khám " + idLichKham);
        model.addAttribute("lichKham", lichKham);
        return "thanhtoan";
    }

    @PostMapping("/confirmZaloPayPayment")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String confirmZaloPayPayment(@RequestParam("idLichKham") Integer idLichKham,
                                        @AuthenticationPrincipal CustomUserDetails taiKhoan,
                                        Model model) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        LichKham lichKham = lichKhamRepo.findById(idLichKham)
                .orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        lichKham.setPaid(true);
        lichKhamRepo.save(lichKham);
        model.addAttribute("msg", "Thanh toán ZaloPay đã được xác nhận cho lịch khám " + idLichKham);
        model.addAttribute("lichKham", lichKham);
        return "thanhtoan";
    }

    @GetMapping("/thanh-toan")
    @PreAuthorize("hasAuthority('BENH_NHAN')")
    public String thanhToan(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan,
                            @RequestParam(value = "idLichKham") Integer idLichKham) {
        if (taiKhoan == null || !taiKhoan.hasRole("BENH_NHAN")) {
            return "redirect:/dang-nhap";
        }
        BenhNhan benhNhan = benhNhanRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        LichKham lichKham = lichKhamRepo.findById(idLichKham)
                .orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        model.addAttribute("lichKham", lichKham);
        model.addAttribute("msg", "Vui lòng chọn phương thức thanh toán cho lịch khám " + idLichKham);
        return "thanhtoan";
    }
}