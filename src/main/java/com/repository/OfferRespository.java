package com.repository;

import java.util.*;

import com.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRespository extends JpaRepository<OfferEntity, Integer> {


    List<OfferEntity> findByMenuItemItemId(Integer itemId);
}
