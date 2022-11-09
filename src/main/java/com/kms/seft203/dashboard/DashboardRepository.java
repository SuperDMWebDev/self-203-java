package com.kms.seft203.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long>{

    boolean existsById(Long id);

    @Query("Select distinct d from Dashboard d where d.user.id = :userId")
    List<Dashboard> findAllByUser_Id(Long userId);
}
