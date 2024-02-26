package org.tearsinrainvideo.tearsinrainvideo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tearsinrainvideo.tearsinrainvideo.model.Rental;
import org.tearsinrainvideo.tearsinrainvideo.repository.RentalRepository;
import org.tearsinrainvideo.tearsinrainvideo.exception.RentalProcessException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    private static final double BASIC_RATE = 3;
    private static final double PREMIUM_RATE = 4;

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental saveRental(Rental rental) {

        if (rental == null) {
            throw new RentalProcessException("Cannot save a null rental.");
        }

        if (rental.getFilm() == null || rental.getFilm().getId() == null) {
            throw new RentalProcessException("Cannot save a rental with no film information.");
        }

        if (rental.getCustomer() == null || rental.getCustomer().getId() == null) {
            throw new RentalProcessException("Cannot save a rental with no customer information.");
        }

        if (rental.getRentalDate() == null || rental.getRentalDate().isAfter(LocalDate.now())) {
            throw new RentalProcessException("Cannot save a rental with an invalid rental date.");
        }

        if (rental.getRentalDays() <= 0) {
            throw new RentalProcessException("Rental days must be a positive number.");
        }
        return rentalRepository.save(rental);
    }


    @Override
    public Rental processReturn(Long rentalId, Rental returnDetails) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalProcessException("Rental not found with id: " + rentalId));

        rental.setReturnDate(returnDetails.getReturnDate());
        calculateAndSetLateFee(rental);
        return rentalRepository.save(rental);
    }

    /**
     * calculates the late fee based on the rental agreement and actual return date
     * assumes a fixed late fee rate per day for simplicity
     * except, of course, when more is more — like days late
     */
    private void calculateAndSetLateFee(Rental rental) {
        if (rental.getReturnDate() == null) {
            throw new RentalProcessException("No return date set. Is the film still out?");
        }

        LocalDate expectedReturnDate = rental.getRentalDate().plusDays(rental.getRentalDays());
        long daysLate = ChronoUnit.DAYS.between(expectedReturnDate, rental.getReturnDate());

        double lateFee = 0;
        if (daysLate > 0) {
            switch (rental.getFilm().getType()) {
                case NEW_RELEASE:
                    lateFee = daysLate * PREMIUM_RATE;
                    break;
                case REGULAR:
                case OLD:
                    lateFee = daysLate * BASIC_RATE;
                    break;
                default:
                    throw new RentalProcessException("Unknown film type. How peculiar!");
            }
        }
        rental.setLateFee(lateFee);
        rental.setReturned(true);
    }
}
