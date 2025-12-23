package com.example.travenorowner.data

import com.example.travenorowner.model.Destination
import kotlinx.coroutines.flow.Flow

interface DestinationsRepository {
    fun getAllDestination(): Flow<List<Destination>>
    fun getDestination(id : String): Flow<Destination>
}