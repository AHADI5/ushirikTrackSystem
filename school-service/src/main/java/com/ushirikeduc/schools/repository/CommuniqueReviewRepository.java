package com.ushirikeduc.schools.repository;

import com.ushirikeduc.schools.model.CommuniqueReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuniqueReviewRepository extends JpaRepository<CommuniqueReview , Integer> {
    public CommuniqueReview getCommuniqueReviewByReviewOwnerAndCommunique_CommuniqueID(String reviewOwner, long communique_communiqueID) ;

}
