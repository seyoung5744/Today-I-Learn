package com.example.springbootbatchsample.domain.repository;

import com.example.springbootbatchsample.domain.entity.DeletedBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedBoardRepository extends JpaRepository<DeletedBoard, Long> {

}
