package com.zerobase.domain.repository;

import com.zerobase.domain.domain.LoanReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanReviewRepository extends JpaRepository<LoanReview, Long> {

    LoanReview findByUserKey(String userKey);
}
