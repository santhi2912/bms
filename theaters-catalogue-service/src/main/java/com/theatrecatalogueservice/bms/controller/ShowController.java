/**
 * 
 */
package com.theatrecatalogueservice.bms.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.bms.dto.PageResponse;
import com.data.bms.dto.ShowDto;
import com.data.bms.service.ShowService;

import lombok.extern.log4j.Log4j2;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Log4j2
@RestController
@RequestMapping("theatre-catalogue-service")
public class ShowController {

	@Autowired
	private ShowService showService;

	@GetMapping("/search/{pageNo}/{limit}/{city}/{movieName}")
	public ResponseEntity<PageResponse<ShowDto>> search(
			@PathVariable(name = "pageNo") @Min(value = 1, message = "Page No Cannot be less than 1") int pageNo,
			@PathVariable(name = "limit") @Min(value = 1, message = "Limit Cannot be less than 1") int limit,
			@RequestParam(name = "city", required = false) String city,
			@RequestParam(name = "movieName", required = false) String movieName,
			@RequestParam(name = "showDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate showDate,
			@RequestParam(name = "showTime", required = false) @DateTimeFormat(pattern = "HH:mm:ss") LocalTime showTime) {

		log.info("Received Request to search shows for Page: [Number: " + pageNo + ", Limit: " + limit + "]");

		return ResponseEntity.ok(showService.searchShows(movieName, city, showDate, showTime, pageNo, limit));
	}

	@PostMapping("/bookseats")
	public ResponseEntity<TicketDto> bookTicket(@RequestBody BookTicketRequestDto bookTicketRequestDto) {

		log.info("Received Request to book ticket: " + bookTicketRequestDto);
		
		return ResponseEntity.ok(showService.bookTicket(bookTicketRequestDto));
	}


}