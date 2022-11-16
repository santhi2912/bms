/**
 * 
 */
package com.data.bms.dto;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.data.bms.enums.SeatType;

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
public class BookTicketRequestDto {

	@NotEmpty(message = "Select atleast 1 Seat to Book")
	private Set<String> seatsNumbers;

	@Min(value = 1, message = "User is Invalid")
	private long userId;

	@Min(value = 1, message = "Theater is Invalid")
	private long theaterId;
	
	@Min(value = 1, message = "Show is Invalid")
	private long showId;

	@NotNull(message = "Seat Type is Mandatory to Book")
	private SeatType seatType;

}