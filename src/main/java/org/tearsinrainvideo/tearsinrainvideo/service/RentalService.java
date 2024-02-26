package org.tearsinrainvideo.tearsinrainvideo.service;

import org.tearsinrainvideo.tearsinrainvideo.model.Rental;

public interface RentalService {
    Rental saveRental(Rental rental);
    Rental processReturn(Long rentalId, Rental returnDetails);
}
