package com.example.travenorowner.data

import com.example.travenorowner.model.Booking
import com.example.travenorowner.network.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


class BookingRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : BookingRepository {

    override fun bookingStatus(): Flow<Booking> = callbackFlow {

        val channel = supabaseClient.channel(Constants.BOOKING)

        val realtimeFlow = channel.postgresChangeFlow<PostgresAction.Insert>(
            schema = Constants.PUBLIC_SCHEME
        ) {
            table = Constants.BOOKING
        }

        val job = realtimeFlow.onEach { event ->
            val booking = Json.decodeFromJsonElement<Booking>(event.record)
            trySend(booking).isSuccess
        }.launchIn(this)

        channel.subscribe()

        awaitClose {
            job.cancel()
            launch {
                channel.unsubscribe()
            }
        }
    }


    override suspend fun updateStatus(
        bookingId: String, newStatus: String
    ) {
        supabaseClient.from(Constants.BOOKING)
            .update(
                {
                    set("status", newStatus)
                }) {
                filter {
                    eq("id", bookingId)
                }
            }
    }
}