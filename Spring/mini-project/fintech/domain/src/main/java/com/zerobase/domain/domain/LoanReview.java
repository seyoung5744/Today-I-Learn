package com.zerobase.domain.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "LOAN_REVIEW")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoanReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_lmt_amt")
    private Long loanLimitAmount;

    @Column(name = "loan_intrt")
    private Double loanInterest;

    private LoanReview(Long loanLimitAmount, Double loanInterest) {
        this.loanLimitAmount = loanLimitAmount;
        this.loanInterest = loanInterest;
    }
}
