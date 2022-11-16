/**
 * 
 */
package com.data.bms.dto;

import java.time.LocalDate;

import com.data.bms.enums.CertificateType;
import com.data.bms.enums.MovieLanguage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class MovieDto {

	private long id;

	private String name;

	private MovieLanguage language;

	private CertificateType certificateType;

	private LocalDate releaseDate;

}