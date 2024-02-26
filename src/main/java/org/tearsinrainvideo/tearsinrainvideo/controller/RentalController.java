package org.tearsinrainvideo.tearsinrainvideo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tearsinrainvideo.tearsinrainvideo.model.Rental;
import org.tearsinrainvideo.tearsinrainvideo.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        return ResponseEntity.ok(rentalService.saveRental(rental));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Rental> returnRental(@PathVariable Long id, @RequestBody Rental rentalDetails) {
        Rental rental = rentalService.processReturn(id, rentalDetails);
        return ResponseEntity.ok(rental);
    }
}
