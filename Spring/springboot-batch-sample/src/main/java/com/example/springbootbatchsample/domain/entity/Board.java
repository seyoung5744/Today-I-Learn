package com.example.springbootbatchsample.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Table(name = "board")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ColumnDefault(value = "false")
    private boolean isDeleted;

    private LocalDateTime createdAt;

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
