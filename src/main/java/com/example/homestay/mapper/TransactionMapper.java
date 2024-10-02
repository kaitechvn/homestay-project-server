package com.example.homestay.mapper;

import com.example.homestay.dto.reponse.TransactionResponse;
import com.example.homestay.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "booking.id", target = "bookingId")
    TransactionResponse toTransactionResponse(Transaction transaction);

//    @Mapping(source = "bookingId", target = "booking.id")
//    Transaction toEntity(Transaction transactionDTO);
}
