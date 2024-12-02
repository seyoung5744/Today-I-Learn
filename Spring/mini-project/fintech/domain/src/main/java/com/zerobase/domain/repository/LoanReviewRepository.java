package com.zerobase.domain.repository;

import com.zerobase.domain.domain.LoanReview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanReviewRepository extends JpaRepository<LoanReview, Long> {

    Optional<LoanReview> findByUserKey(String userKey);
}
