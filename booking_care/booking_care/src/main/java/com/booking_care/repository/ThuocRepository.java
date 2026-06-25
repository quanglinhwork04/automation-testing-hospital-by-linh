package com.booking_care.repository;

import com.booking_care.model.Thuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ThuocRepository extends JpaRepository<Thuoc, Integer> {
    @Query("SELECT t FROM Thuoc t WHERE t.tenThuoc LIKE %:keyword%")
    List<Thuoc> findByTenThuocContaining(String keyword);
}