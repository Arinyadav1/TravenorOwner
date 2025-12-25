package com.example.travenorowner.data

import com.example.travenorowner.model.Booking
import kotlinx.coroutines.flow.Flow

interface BookingRepository {

     suspend fun bookingStatus(): Flow<Booking>?

    suspend fun updateStatus(
        bookingId: String,
        newStatus: String
    )
}