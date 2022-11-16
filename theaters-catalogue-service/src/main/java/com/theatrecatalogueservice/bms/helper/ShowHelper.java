/**
 * 
 */
package com.theatrecatalogueservice.bms.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Join;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.data.bms.model.MovieEntity;
import com.data.bms.model.ShowEntity;
import com.data.bms.model.TheaterEntity;

import lombok.experimental.UtilityClass;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@UtilityClass
public class ShowHelper {

	public static Specification<ShowEntity> createSpecification(String movieName, String city, LocalDate showDate, LocalTime showTime) {

		List<Specification<ShowEntity>> specifications = new ArrayList<>();

		specifications.add(getCurrentAndFutureShowSpec());

		if (StringUtils.isNotBlank(movieName)) {
			specifications.add(getShowByMovieNameSpec(movieName));
		}

		if (StringUtils.isNotBlank(city)) {
			specifications.add(getShowByCitySpec(city));
		}

		if (Objects.nonNull(showDate)) {
			specifications.add(getShowByDateSpec(showDate));
		}

		if (Objects.nonNull(showTime)) {
			specifications.add(getShowByTimeSpec(showTime));
		}

		return createSpecification(specifications);

	}

	private static Specification<ShowEntity> createSpecification(List<Specification<ShowEntity>> specs) {

		Specification<ShowEntity> result = specs.get(0);

		for (int i = 1; i < specs.size(); i++) {
			result = Specification.where(result).and(specs.get(i));
		}

		return result;
	}


}