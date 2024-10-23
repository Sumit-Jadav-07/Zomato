package com.controller;

import com.entity.MenuItemEntity;
import com.entity.OfferEntity;
import com.repository.MenuItemRepository;
import com.repository.OfferRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/private/offers")
public class OfferController {

    @Autowired
    MenuItemRepository menuItemRepo;

    @Autowired
    OfferRespository offerRepo;

    @PostMapping("/create/{itemId}")
    public ResponseEntity<?> createOffer(@PathVariable Integer itemId, @RequestBody OfferEntity offer) {
        Optional<MenuItemEntity> itemOp = menuItemRepo.findById(itemId);
        List<OfferEntity> offers = offerRepo.findByMenuItemItemId(itemId);

        boolean hasOfferPercentage = false;
        boolean hasBOGO = false;

        for (OfferEntity existingOffer : offers) {
            if ("offerPercentage".equals(existingOffer.getOfferType())) {
                hasOfferPercentage = true;
            } else if ("BOGO".equals(existingOffer.getOfferType())) {
                hasBOGO = true;
            }
        }

        if (itemOp.isPresent()) {
            MenuItemEntity item = itemOp.get();
            if ("offerPercentage".equals(offer.getOfferType()) && hasOfferPercentage) {
                return ResponseEntity.badRequest().body("Offer percentage already exists for this item");
            } else if ("BOGO".equals(offer.getOfferType()) && hasBOGO) {
                return ResponseEntity.badRequest().body("BOGO offer already exists for this item");
            } else if (offers.size() >= 2) {
                return ResponseEntity.badRequest().body("This Item already has 2 offers");
            } else {

                item.setIsOffer(true);
                item.setOfferType(offer.getOfferType());
                menuItemRepo.save(item);

                double finalPrice = item.getItemPrice();
                if (item.getIsOffer()) {
                    if ("offerPercentage".equals(offer.getOfferType()) && offer.getOfferPercentage() > 0) {
                        finalPrice -= finalPrice * offer.getOfferPercentage() / 100;
                    }

                    if ("BOGO".equals(offer.getOfferType()) && offer.getOfferQuantity() > 0 && offer.getOfferQuantityCount() > 0) {
                        int freeItems = offer.getOfferQuantity();
                        int paidItems = offer.getOfferQuantityCount();
                        finalPrice = (finalPrice * paidItems) / (paidItems + freeItems);
                    }
                }

                offer.setMenuItem(item);
                offer.setOriginalValue(item.getItemPrice());
                offer.setFinalValue(finalPrice);
                offerRepo.save(offer);
                return ResponseEntity.ok("Offer created successfully");
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu item not found");
        }

    }

    @PutMapping("/update/{itemId}")
    public ResponseEntity<?> updateOffer(@PathVariable Integer offerId, @RequestBody OfferEntity offerDetails) {
        Optional<OfferEntity> op = offerRepo.findById(offerId);
        if (op.isPresent()) {
            OfferEntity offer = op.get();
            offer.setOfferType(offerDetails.getOfferType());
            offer.setOfferDescription(offerDetails.getOfferDescription());
            offer.setOfferValue(offerDetails.getOfferValue());
            offerRepo.save(offer);
            return ResponseEntity.ok("Offer updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer not found");
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getOffersForMenuItem(@PathVariable Integer itemId) {
        List<OfferEntity> offers = offerRepo.findByMenuItemItemId(itemId);
        if (offers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Offers found for this item");
        } else {
            return ResponseEntity.ok(offers);
        }
    }
}
