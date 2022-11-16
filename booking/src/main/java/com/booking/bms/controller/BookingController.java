/**
 * 
 */
package com.booking.bms.controller;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.bms.dto.BookTicketRequestDto;
import com.data.bms.dto.TicketDto;
import com.booking.bms.service.TicketService;

import lombok.extern.log4j.Log4j2;


/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Log4j2
@RestController
public class BookingController {

	@Autowired
	private TheatreCatalogueServiceProxy proxy;

	@PostMapping("/bookseats")
	public ResponseEntity<TicketDto> bookTicket(@RequestBody BookTicketRequestDto bookTicketRequestDto) {

		log.info("Received Request to book ticket: " + bookTicketRequestDto);

		TicketDto ticketDto = proxy.bookSeats(bookTicketRequestDto);
		if (ticketDto != null) {
			return ticketDto;
		}

		return ResponseEntity.ok(ticketService.bookTicket(bookTicketRequestDto));
	}

}