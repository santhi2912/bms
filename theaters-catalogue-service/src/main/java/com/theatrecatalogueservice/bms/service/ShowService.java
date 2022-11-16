/**
 * 
 */
package com.theatrecatalogueservice.bms.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.data.bms.dto.PageResponse;
import com.data.bms.dto.ShowDto;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
public interface ShowService {



	PageResponse<ShowDto> searchShows(String movieName, String city, LocalDate showDate, LocalTime showTime, int pageNo, int limit);
	
    TicketDto bookTicket(BookTicketRequestDto bookTicketRequestDto);

}