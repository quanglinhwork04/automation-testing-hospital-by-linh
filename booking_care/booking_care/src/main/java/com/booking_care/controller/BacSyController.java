package com.booking_care.controller;

import com.booking_care.constant.ewewwewe.Status;
import com.booking_care.model.*;
import com.booking_care.model.request.BacSyRequest;
import com.booking_care.model.request.DoiMatKhauRequest;
import com.booking_care.model.request.ToaThuocRequest;
import com.booking_care.repository.*;
import com.booking_care.security.CustomUserDetails;
import com.booking_care.service.EmailSenderService;
import com.booking_care.utils.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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

import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class BacSyController {
    private static final Logger logger = LoggerFactory.getLogger(BacSyController.class);

    @Autowired
    public JavaMailSender mailSender;
    @Autowired
    BacSyRepository bacSyRepo;
    @Autowired
    LichKhamRepository lichKhamRepo;
    @Autowired
    ChuyenKhoaRepository chuyenKhoaRepo;
    @Autowired
    TaiKhoanRepository taiKhoanRepo;
    @Autowired
    private ThuocRepository thuocRepo;
    @Autowired
    private ToaThuocRepository toaThuocRepo;
    @Autowired
    private ChiTietToaThuocRepository chiTietToaThuocRepo;
    @Autowired
    EmailSenderService emailSenderService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @ModelAttribute
    TaiKhoan taiKhoan() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof CustomUserDetails && ((CustomUserDetails) user).hasRole("BENH_NHAN")) {
            return ((CustomUserDetails) user).getTaiKhoan();
        }
        return null;
    }

    @GetMapping("/danh-sach-bac-sy")
    public String getAllBacSy(
            Model model,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "chuyenKhoaId", required = false) String chuyenKhoaId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {

        logger.info("Received keyword: {}, chuyenKhoaId: {}, page: {}, pageSize: {}", keyword, chuyenKhoaId, page, pageSize);

        Page<BacSy> bacSyPage;
        String query = request.getQueryString();

        model.addAttribute("bacSyList", Collections.emptyList());
        model.addAttribute("chuyenKhoaList", chuyenKhoaRepo.findAll());
        model.addAttribute("keyword", keyword != null ? keyword : "");
        model.addAttribute("chuyenKhoaId", chuyenKhoaId != null ? chuyenKhoaId : "");

        if (query != null && (query.contains("keyword") || query.contains("chuyenKhoaId"))) {
            model.addAttribute("issearch", true);
            model.addAttribute("query", query);
        } else {
            model.addAttribute("issearch", false);
        }

        try {
            if (keyword != null && keyword.trim().length() > 50) {
                model.addAttribute("errorMessage", "Từ khóa tìm kiếm quá dài (tối đa 50 ký tự).");
                return "list-doctor";
            }

            if (keyword != null && keyword.matches(".*[<>{}();\"'`].*")) {
                model.addAttribute("errorMessage", "Từ khóa chứa ký tự không hợp lệ (ví dụ: <, >, ;, ').");
                return "list-doctor";
            }

            Integer chuyenKhoaIdInt = null;
            if (chuyenKhoaId != null && !chuyenKhoaId.trim().isEmpty()) {
                try {
                    chuyenKhoaIdInt = Integer.parseInt(chuyenKhoaId);
                    if (!chuyenKhoaRepo.existsById(chuyenKhoaIdInt)) {
                        model.addAttribute("errorMessage", "Chuyên khoa không tồn tại.");
                        return "list-doctor";
                    }
                } catch (NumberFormatException e) {
                    model.addAttribute("errorMessage", "ID chuyên khoa không hợp lệ.");
                    return "list-doctor";
                }
            }

            if (keyword != null && !keyword.trim().isEmpty() && chuyenKhoaIdInt != null) {
                bacSyPage = bacSyRepo.findByKeywordAndChuyenKhoa(keyword.trim(), chuyenKhoaIdInt, PageRequest.of(page - 1, pageSize));
            } else if (keyword != null && !keyword.trim().isEmpty()) {
                bacSyPage = bacSyRepo.findByKeyword(keyword.trim(), PageRequest.of(page - 1, pageSize));
            } else if (chuyenKhoaIdInt != null) {
                bacSyPage = bacSyRepo.findByChuyenKhoaId(chuyenKhoaIdInt, PageRequest.of(page - 1, pageSize));
            } else {
                bacSyPage = bacSyRepo.findAll(PageRequest.of(page - 1, pageSize));
            }

            if (bacSyPage.getContent().isEmpty()) {
                model.addAttribute("errorMessage", "Không tìm thấy bác sĩ phù hợp với tiêu chí!");
            } else {
                if (bacSyPage.getContent().stream().anyMatch(bacSy -> bacSy.getId() == null)) {
                    model.addAttribute("errorMessage", "Dữ liệu bác sĩ không hợp lệ (ID null).");
                }
            }

            logger.info("Danh sách bác sĩ:");
            bacSyPage.getContent().forEach(bacSy -> {
                if (bacSy.getId() == null) {
                    logger.error("Lỗi: Bác sĩ có ID null - {}", bacSy.getHoTen());
                } else {
                    logger.info("ID: {}, Tên: {}", bacSy.getId(), bacSy.getHoTen());
                }
            });

            model.addAttribute("bacSyList", bacSyPage.getContent());
            model.addAttribute("bacSyPage", bacSyPage);

            int totalPages = bacSyPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
                model.addAttribute("totalPage", totalPages);
            }

            return "list-doctor";
        } catch (Exception e) {
            logger.error("Lỗi hệ thống: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            return "list-doctor";
        }
    }

    @GetMapping("/bac-sy/{id}")
    public String getBacSy(@PathVariable Integer id, Model model) {
        BacSy bacSy = bacSyRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bác sĩ không tồn tại"));
        // Xử lý tienKham nếu null
        if (bacSy.getTienKham() == null) {
            logger.warn("tienKham của bác sĩ ID {} là null, gán giá trị mặc định 0", id);
            bacSy.setTienKham(0);
        }
        logger.info("tienKham của bác sĩ ID {}: {}", id, bacSy.getTienKham());
        model.addAttribute("bacSy", bacSy);
        return "detail-doctor";
    }

    @GetMapping("/bacsy/login")
    public String viewBacSyLoginPage() {
        return "bacsy/bacsy_login";
    }

    @GetMapping("/bacsy")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String homeBacSy2() {
        return "bacsy/bacsy_home";
    }

    @GetMapping("/bacsy/home")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String homeBacSy() {
        return "bacsy/bacsy_home";
    }

    @GetMapping("bacsy/danhSachLichKham")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String getAllLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                 Model model) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }
        BacSy bacSy = bacSyRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        List<LichKham> lichKhamList = lichKhamRepo.getAllLichKhamByBacSyId(bacSy.getId());
        model.addAttribute("lichKhamList", lichKhamList);
        return "bacsy/bacsy_xem_lich_kham";
    }

    @GetMapping("bacsy/xemChiTietLichKham")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String xemChiTietLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                     @RequestParam("id") Integer id,
                                     Model model) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }
        LichKham lichKham = lichKhamRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lịch khám không tồn tại"));
        model.addAttribute("lichKham", lichKham);
        return "bacsy/bacsy_xem_chi_tiet_lich_kham";
    }

    @PostMapping("bacsy/xacNhan")
    @PreAuthorize("hasAuthority('BAC_SY')")
    @Transactional
    public ResponseEntity<?> xacNhanLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                             @RequestParam("id") Integer id) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập!");
        }

        Optional<LichKham> optionalLichKham = lichKhamRepo.findById(id);
        if (!optionalLichKham.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lịch hẹn không tồn tại!");
        }

        LichKham lichKham = optionalLichKham.get();
        if (!lichKham.getStatus().equals(Status.CHO_XU_LY)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lịch hẹn không ở trạng thái 'Chờ xử lý'!");
        }

        String email = lichKham.getBenhNhan() != null ? lichKham.getBenhNhan().getEmail() : null;
        logger.info("Xác nhận lịch khám ID: {}, Email bệnh nhân: {}", id, email);
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email bệnh nhân không hợp lệ hoặc không tồn tại!");
        }

        try {
            lichKham.setStatus(Status.DA_XAC_NHAN);
            lichKhamRepo.save(lichKham);

            Email emailObj = new Email();
            emailObj.setTo(email);
            emailObj.setFrom(new InternetAddress("haibonglau411@gmail.com", "MEDICATE"));
            emailObj.setSubject("Thông tin chi tiết lịch khám");
            emailObj.setTemplate("template-email.html");
            Map<String, Object> properties = new HashMap<>();
            properties.put("lichKham", lichKham);
            emailObj.setProperties(properties);
            emailSenderService.sendHtmlMessage(emailObj);

            return ResponseEntity.ok("Xác nhận lịch khám thành công!");
        } catch (SendFailedException e) {
            logger.error("Gửi email thất bại: {}", e.getMessage(), e);
            lichKham.setStatus(Status.CHO_XU_LY);
            lichKhamRepo.save(lichKham);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gửi email thất bại!");
        } catch (Exception e) {
            logger.error("Xác nhận lịch hẹn thất bại: {}", e.getMessage(), e);
            lichKham.setStatus(Status.CHO_XU_LY);
            lichKhamRepo.save(lichKham);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xác nhận lịch hẹn thất bại: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && !email.trim().isEmpty() && email.length() <= 100 && email.matches(emailRegex);
    }

    @PostMapping("bacsy/huyLichKham")
    @PreAuthorize("hasAuthority('BAC_SY')")
    @Transactional
    @ResponseBody
    public ResponseEntity<?> huyLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                         @RequestParam("id") Integer id,
                                         @RequestParam("lyDoHuy") String lyDoHuy) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập!");
        }

        Optional<LichKham> optionalLichKham = lichKhamRepo.findById(id);
        if (!optionalLichKham.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lịch hẹn không tồn tại!");
        }

        LichKham lichKham = optionalLichKham.get();
        if (!lichKham.getStatus().equals(Status.CHO_XU_LY)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chỉ có thể hủy lịch hẹn ở trạng thái 'Chờ xử lý'!");
        }

        String email = lichKham.getBenhNhan() != null ? lichKham.getBenhNhan().getEmail() : null;
        logger.info("Hủy lịch khám ID: {}, Email bệnh nhân: {}", id, email);
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email bệnh nhân không hợp lệ hoặc không tồn tại!");
        }

        if (lyDoHuy == null || lyDoHuy.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lý do hủy!");
        }

        if (lyDoHuy.length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lý do hủy không được vượt quá 100 ký tự!");
        }

        try {
            lichKham.setStatus(Status.DA_HUY);
            lichKhamRepo.save(lichKham);

            Email emailObj = new Email();
            emailObj.setTo(email);
            emailObj.setFrom(new InternetAddress("haibonglau411@gmail.com", "MEDICATE"));
            emailObj.setSubject("Hủy lịch khám");
            emailObj.setTemplate("template-huy-lich.html");
            Map<String, Object> properties = new HashMap<>();
            properties.put("lichKham", lichKham);
            properties.put("lyDoHuy", lyDoHuy.trim());
            emailObj.setProperties(properties);
            emailSenderService.sendHtmlMessage(emailObj);

            return ResponseEntity.ok("Hủy lịch khám thành công!");
        } catch (SendFailedException e) {
            logger.error("Gửi email thất bại: {}", e.getMessage(), e);
            lichKham.setStatus(Status.CHO_XU_LY);
            lichKhamRepo.save(lichKham);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gửi email thất bại!");
        } catch (Exception e) {
            logger.error("Hủy lịch hẹn thất bại: {}", e.getMessage(), e);
            lichKham.setStatus(Status.CHO_XU_LY);
            lichKhamRepo.save(lichKham);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hủy lịch hẹn thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/bacsy/danhSachThuoc")
    @PreAuthorize("hasAuthority('BAC_SY')")
    @ResponseBody
    public List<Thuoc> getAllThuoc() {
        return thuocRepo.findAll();
    }

    @PostMapping("/bacsy/guiToaThuoc")
    @PreAuthorize("hasAuthority('BAC_SY')")
    @ResponseBody
    public String guiToaThuoc(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                              @RequestBody ToaThuocRequest request) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            throw new SecurityException("Không có quyền truy cập");
        }

        try {
            if (request.getLichKhamId() == null || request.getLichKhamId().trim().isEmpty()) {
                throw new IllegalArgumentException("ID lịch khám không được để trống");
            }

            int lichKhamId;
            try {
                lichKhamId = Integer.parseInt(request.getLichKhamId());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID lịch khám không hợp lệ: " + request.getLichKhamId());
            }

            Optional<LichKham> optionalLichKham = lichKhamRepo.findById(lichKhamId);
            if (!optionalLichKham.isPresent()) {
                throw new IllegalArgumentException("Lịch khám không tồn tại");
            }

            LichKham lichKham = optionalLichKham.get();
            if (!lichKham.getStatus().equals(Status.DA_XAC_NHAN)) {
                throw new IllegalArgumentException("Lịch hẹn phải ở trạng thái 'Đã xác nhận' để gửi toa thuốc");
            }

            String email = lichKham.getBenhNhan() != null ? lichKham.getBenhNhan().getEmail() : null;
            logger.info("Gửi toa thuốc cho lịch khám ID: {}, Email bệnh nhân: {}", lichKhamId, email);
            if (email == null || !isValidEmail(email)) {
                throw new IllegalArgumentException("Email bệnh nhân không hợp lệ");
            }

            if (request.getChanDoan() == null || request.getChanDoan().trim().isEmpty()) {
                throw new IllegalArgumentException("Chuẩn đoán không được để trống");
            }

            if (request.getThuocList() == null || request.getThuocList().isEmpty()) {
                throw new IllegalArgumentException("Danh sách thuốc không được để trống");
            }

            lichKham.setChanDoan(request.getChanDoan());
            lichKhamRepo.save(lichKham);

            ToaThuoc toaThuoc = new ToaThuoc();
            toaThuoc.setLichKham(lichKham);
            toaThuoc.setBenhNhan(lichKham.getBenhNhan());
            toaThuocRepo.save(toaThuoc);

            List<Map<String, Object>> thuocItems = new ArrayList<>();
            for (ToaThuocRequest.ThuocItem item : request.getThuocList()) {
                if (item.getThuocId() == null) {
                    throw new IllegalArgumentException("ID thuốc không được để trống");
                }
                Thuoc thuoc = thuocRepo.findById(item.getThuocId())
                        .orElseThrow(() -> new IllegalArgumentException("Thuốc không tồn tại: " + item.getThuocId()));

                if (item.getSoLuong() == null || item.getSoLuong() <= 0) {
                    throw new IllegalArgumentException("Số lượng thuốc phải lớn hơn 0");
                }

                ChiTietToaThuoc chiTiet = new ChiTietToaThuoc();
                chiTiet.setToaThuoc(toaThuoc);
                chiTiet.setThuoc(thuoc);
                chiTiet.setDonViTinh(item.getDonViTinh() != null ? item.getDonViTinh() : "");
                chiTiet.setSoLuong(item.getSoLuong());
                chiTiet.setHdsd(item.getHdsd() != null ? item.getHdsd() : "");
                chiTietToaThuocRepo.save(chiTiet);

                Map<String, Object> thuocData = new HashMap<>();
                thuocData.put("tenThuoc", thuoc.getTenThuoc());
                thuocData.put("donViTinh", item.getDonViTinh());
                thuocData.put("soLuong", item.getSoLuong());
                thuocData.put("hdsd", item.getHdsd());
                thuocItems.add(thuocData);
            }

            Email emailObj = new Email();
            emailObj.setTo(request.getBenhNhanEmail());
            emailObj.setFrom(new InternetAddress("haibonglau411@gmail.com", "MEDICATE"));
            emailObj.setSubject("Toa thuốc từ bác sĩ " + lichKham.getBacSy().getHoTen());
            emailObj.setTemplate("template-toa-thuoc.html");
            Map<String, Object> properties = new HashMap<>();
            properties.put("benhNhan", lichKham.getBenhNhan());
            properties.put("bacSy", lichKham.getBacSy());
            properties.put("thuocItems", thuocItems);
            properties.put("chanDoan", lichKham.getChanDoan());
            emailObj.setProperties(properties);
            emailSenderService.sendHtmlMessage(emailObj);

            return "Toa thuốc đã được gửi và lưu thành công!";
        } catch (Exception e) {
            logger.error("Gửi toa thuốc thất bại: {}", e.getMessage(), e);
            throw new RuntimeException("Gửi toa thuốc thất bại: " + e.getMessage());
        }
    }

    @GetMapping("/bacsy/profile")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String viewBacSy(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }
        BacSy bacSy = bacSyRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepo.findAll();
        model.addAttribute("bacSy", bacSy);
        model.addAttribute("chuyenKhoaList", chuyenKhoaList);
        return "bacsy/bacsy_profile";
    }

    @Transactional
    @PostMapping("/bacsy/profile")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String updateBacSy(RedirectAttributes redirectAttributes, @AuthenticationPrincipal CustomUserDetails taiKhoan,
                              @ModelAttribute(name = "bacSy") @Valid BacSyRequest bacSyRequest, @RequestParam("image") MultipartFile file,
                              Errors errors) {
        if (errors.hasErrors()) {
            return "bacsy/bacsy_profile";
        }
        try {
            BacSy bacSy = bacSyRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());

            if (file.isEmpty()) {
                bacSyRepo.updateThongTinBacSy(format.parse(bacSyRequest.getNgaySinh()), bacSyRequest.getHoTen(),
                        bacSyRequest.getChucVu(), bacSyRequest.getChuyenKhoaId(),
                        bacSyRequest.getSdt(), bacSyRequest.getEmail(), bacSyRequest.getChungChi(), bacSyRequest.getKinhNghiem(),
                        bacSyRequest.getLinhVucChuyenSau(), bacSyRequest.getTienKham(), bacSyRequest.getNoiKham(), bacSy.getId());
                redirectAttributes.addFlashAttribute("ok", "Update thành công");
                return "redirect:/bacsy/profile";
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            bacSyRepo.updateThongTinBacSyAndUploadFile(format.parse(bacSyRequest.getNgaySinh()), bacSyRequest.getHoTen(),
                    bacSyRequest.getChucVu(), bacSyRequest.getChuyenKhoaId(),
                    bacSyRequest.getSdt(), bacSyRequest.getEmail(), fileName, bacSyRequest.getChungChi(),
                    bacSyRequest.getKinhNghiem(), bacSyRequest.getLinhVucChuyenSau(), bacSyRequest.getTienKham(), bacSyRequest.getNoiKham(), bacSy.getId());
            String uploadDir = "bacsy-photos/" + bacSy.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);

            redirectAttributes.addFlashAttribute("ok", "Update thành công");
            return "redirect:/bacsy/profile";
        } catch (Exception e) {
            logger.error("Cập nhật thông tin bác sĩ thất bại: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("loi", "Cập nhật thông tin thất bại: " + e.getMessage());
            return "redirect:/bacsy/profile";
        }
    }

    @GetMapping("bacsy/doiMatKhau")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String viewDoiMatKhauBacSy(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }
        BacSy bacSy = bacSyRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        model.addAttribute("bacSy", bacSy);
        model.addAttribute("doiMatKhauRequest", new DoiMatKhauRequest());
        return "bacsy/bacsy_doi_mat_khau";
    }

    @PostMapping("bacsy/doiMatKhau")
    @PreAuthorize("hasAuthority('BAC_SY')")
    @Transactional
    public String updateMatKhauBacSy(@ModelAttribute @Valid DoiMatKhauRequest doiMatKhauRequest,
                                     BindingResult bindingResult,
                                     Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan,
                                     RedirectAttributes redirectAttributes, Errors errors) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }

        if (errors.hasErrors()) {
            return "bacsy/bacsy_doi_mat_khau";
        }

        if (!doiMatKhauRequest.getMatKhauHienTai().equals(taiKhoan.getPassword())) {
            redirectAttributes.addFlashAttribute("loiMatKhau1", "Mật khẩu hiện tại sai mật khẩu cũ");
            return "redirect:/bacsy/doiMatKhau";
        }

        try {
            taiKhoanRepo.updateMatKhau(doiMatKhauRequest.getMatKhauMoi(), taiKhoan.getTaiKhoan().getUsername());
            model.addAttribute("ok", "Update mật khẩu thành công");
            return "bacsy/bacsy_doi_mat_khau";
        } catch (Exception e) {
            logger.error("Cập nhật mật khẩu thất bại: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("loi", "Cập nhật mật khẩu thất bại: " + e.getMessage());
            return "redirect:/bacsy/doiMatKhau";
        }
    }

    @GetMapping("bacsy/xemThongKeLichKham")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String thongKeLichKham(Model model, @AuthenticationPrincipal CustomUserDetails taiKhoan) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            return "redirect:/bacsy/login";
        }
        BacSy bacSy = bacSyRepo.findByTaiKhoan(taiKhoan.getTaiKhoan());
        List<LichKham> lichKhamList = lichKhamRepo.getLichKhamTrongTuan(bacSy.getId());
        model.addAttribute("lichKhamList", lichKhamList);
        return "bacsy/bacsy_thong_ke_lich_kham";
    }

    @PostMapping("bacsy/daKham/{id}")
    @PreAuthorize("hasAuthority('BAC_SY')")
    public String daKhamLichKham(@AuthenticationPrincipal CustomUserDetails taiKhoan,
                                 @PathVariable Integer id,
                                 RedirectAttributes redirectAttributes) {
        if (taiKhoan == null || !taiKhoan.hasRole("BAC_SY")) {
            redirectAttributes.addFlashAttribute("loi", "Bạn không có quyền truy cập!");
            return "redirect:/bacsy/login";
        }

        Optional<LichKham> optionalLichKham = lichKhamRepo.findById(id);
        if (!optionalLichKham.isPresent()) {
            redirectAttributes.addFlashAttribute("loi", "Lịch hẹn không tồn tại!");
            return "redirect:/bacsy/danhSachLichKham";
        }

        LichKham lichKham = optionalLichKham.get();
        if (!lichKham.getStatus().equals(Status.DA_XAC_NHAN)) {
            redirectAttributes.addFlashAttribute("loi", "Chỉ có thể đánh dấu 'Đã khám' cho lịch hẹn ở trạng thái 'Đã xác nhận'!");
            return "redirect:/bacsy/xemChiTietLichKham?id=" + id;
        }

        try {
            lichKham.setStatus(Status.DA_KHAM);
            lichKhamRepo.save(lichKham);
            redirectAttributes.addFlashAttribute("ok", "Chuyển trạng thái đã khám thành công");
        } catch (Exception e) {
            logger.error("Đánh dấu đã khám thất bại: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("loi", "Đánh dấu đã khám thất bại: " + e.getMessage());
        }

        return "redirect:/bacsy/xemChiTietLichKham?id=" + id;
    }
}