package com.example.springbootbatchsample.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "deleted_board")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeletedBoard {

    @Id
    private Long id;

    private String title;

    private String content;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
}
