package com.data.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.data.bms.enums.MovieLanguage;
import com.data.bms.model.MovieEntity;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {

	boolean existsByNameAndLanguage(String name, MovieLanguage language);
}