package com.example.springbootbatchsample.domain.repository;

import com.example.springbootbatchsample.domain.entity.OddBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OddBoardRepository extends JpaRepository<OddBoard, Long> {
}
