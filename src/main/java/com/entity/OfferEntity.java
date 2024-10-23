package com.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Offers")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer offerId;

    private String offerType; // percentage or Buy1get2

    private Double offerValue;

    private Double originalValue;

    private Double finalValue;

    private Double offerPercentage;

    private Integer offerQuantity;

    private Integer offerQuantityCount;

    private Double uptoAmount;

    private String offerDescription;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private MenuItemEntity menuItem;

}
