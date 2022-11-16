package com.data.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.data.bms.model.TheaterEntity;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Repository
public interface TheaterRepository extends JpaRepository<TheaterEntity, Long>, JpaSpecificationExecutor<TheaterEntity> {

}