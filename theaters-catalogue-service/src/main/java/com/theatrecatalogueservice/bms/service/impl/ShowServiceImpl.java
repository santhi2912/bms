/**
 * 
 */
package com.theatrecatalogueservice.bms.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.theatrecatalogueservice.bms.adapter.ShowAdapter;
import com.data.bms.dto.PageResponse;
import com.data.bms.dto.ShowDto;
import com.theatrecatalogueservice.bms.exception.DependencyException;
import com.theatrecatalogueservice.bms.helper.ShowHelper;
import com.data.bms.model.MovieEntity;
import com.data.bms.model.ShowEntity;
import com.data.bms.model.ShowSeatsEntity;
import com.data.bms.model.TheaterEntity;
import com.data.bms.model.TheaterSeatsEntity;
import com.theatrecatalogueservice.bms.repository.ShowRepository;

import com.theatrecatalogueservice.bms.service.ShowService;

import lombok.extern.log4j.Log4j2;

/**
 * @author santhi2912
 *
 * @date 12-Nov-2022
 */
@Log4j2
@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TheaterRepository theaterRepository;



	@Override
	@Transactional
	public PageResponse<ShowDto> searchShows(String movieName, String city, LocalDate showDate, LocalTime showTime, int pageNo, int limit) {

		log.info("Searching Shows by Params: [showName: " + movieName + ", city: " + city + ", showDate: " + showDate + ", showTime: " + showTime + "]");

		Specification<ShowEntity> specifications = ShowHelper.createSpecification(movieName, city, showDate, showTime);

		Page<ShowEntity> showsPage = showsRepository.findAll(specifications, PageRequest.of(pageNo - 1, limit));

		log.info("Found " + showsPage.getNumberOfElements() + " Shows on Page: " + showsPage.getNumber());

		PageResponse<ShowDto> pageResponse = new PageResponse<>();

		if (showsPage.hasContent()) {
			pageResponse.setNumber(pageNo);
			pageResponse.setRecords(showsPage.getNumberOfElements());
			pageResponse.setData(ShowAdapter.toDto(showsPage.getContent()));
		}

		return pageResponse;
	}

	
	@Override
	@Transactional
	public TicketDto bookTicket(BookTicketRequestDto bookTicketRequestDto) {

		log.info("Searching User by id: " + bookTicketRequestDto.getUserId());

		Optional<UserEntity> optionalUser = userRepository.findById(bookTicketRequestDto.getUserId());

		if (!optionalUser.isPresent()) {
			throw new DependencyException("User Not Found with ID: " + bookTicketRequestDto.getUserId() + " to book ticket");
		}

		log.info("Searching Show by id: " + bookTicketRequestDto.getShowId());
		
		Optional<TheaterEntity> optionalTheater = theaterRepository.findById(bookTicketRequestDto.getTheaterId());
		
		List<ShowEntity> showEntities = optionalTheater.get().getShows();
		
		showEntities =
				showEntities
						.stream()
						.filter(show -> show.getShowId().equals(bookTicketRequestDto.getShowId())
								))
						.collect(Collectors.toList());
		
		if (showEntities.size()>0 {
				Optional<ShowEntity> optionalShow = showRepository.findById(showEntities[0].);
			
			if (!optionalShow.isPresent()) {
				throw new DependencyException("Show Not Found with ID: " + bookTicketRequestDto.getUserId() + " to book ticket");
			}
		}
		Set<String> requestedSeats = bookTicketRequestDto.getSeatsNumbers();

		log.info("Requested Bookings For Seats: " + requestedSeats + " of Show: " + bookTicketRequestDto.getShowId() + " by User: " + bookTicketRequestDto.getUserId());

		List<ShowSeatsEntity> showSeatsEntities = optionalShow.get().getSeats();

		log.info("Total Number of Seats in Show: " + bookTicketRequestDto.getShowId() + " are " + showSeatsEntities.size());

		showSeatsEntities =
				showSeatsEntities
						.stream()
						.filter(seat -> seat.getSeatType().equals(bookTicketRequestDto.getSeatType())
								&& !seat.isBooked()
								&& requestedSeats.contains(seat.getSeatNumber()))
						.collect(Collectors.toList());

		if (showSeatsEntities.size() != requestedSeats.size()) {
			throw new DependencyException("Seats Not Available for Booking");
		}

		log.info("Seats: " + requestedSeats + " are avaialble in Show: " + bookTicketRequestDto.getShowId() + " for booking to User " + bookTicketRequestDto.getUserId());

		TicketEntity ticketEntity =
				TicketEntity.builder()
						.user(optionalUser.get())
						.show(optionalShow.get())
						.seats(showSeatsEntities)
						.build();

		double amount = 0.0;
		String allotedSeats = "";

		for (ShowSeatsEntity seatsEntity : showSeatsEntities) {
			seatsEntity.setBookedStatus("RESERVED");
			seatsEntity.setBookedAt(new Date());
			seatsEntity.setTicket(ticketEntity);

			amount += seatsEntity.getRate();

			allotedSeats += seatsEntity.getSeatNumber() + " ";
		}

		ticketEntity.setAmount(amount);
		ticketEntity.setAllottedSeats(allotedSeats);

		if (CollectionUtils.isEmpty(optionalUser.get().getTicketEntities())) {
			optionalUser.get().setTicketEntities(new ArrayList<>());
		}

		optionalUser.get().getTicketEntities().add(ticketEntity);

		if (CollectionUtils.isEmpty(optionalShow.get().getTickets())) {
			optionalShow.get().setTickets(new ArrayList<>());
		}

		optionalShow.get().getTickets().add(ticketEntity);

		log.info("Creating Booking for User: " + bookTicketRequestDto.getUserId() + " in Show: " + bookTicketRequestDto.getShowId() + " for Seats: " + allotedSeats);

		try{
			ticketEntity = ticketRepository.save(ticketEntity);
        }catch(Exception e){
            throw new DataIntegrityViolationException(" OOps - some thing went now - Wait &  try after few minutes")
        }
		

		return TicketAdapter.toDto(ticketEntity);
	}

	
}