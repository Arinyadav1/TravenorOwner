package com.example.travenorowner.data

import com.example.travenorowner.model.Booking
import com.example.travenorowner.network.Constants
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class BookingRepositoryImpl(
    private val supabaseClient: SupabaseClient,
    private val realtimeChannel: RealtimeChannel
) : BookingRepository {

    private var realtimeFlow: Flow<Booking>? = null

    override suspend fun bookingStatus(): Flow<Booking>? {
        if (realtimeChannel.status.value != RealtimeChannel.Status.SUBSCRIBED) {

            realtimeFlow = realtimeChannel.postgresChangeFlow<PostgresAction.Insert>(
                schema = Constants.PUBLIC_SCHEME
            ) {
                table = Constants.BOOKING
            }.map { event ->
                event.decodeRecord<Booking>()
            }

            realtimeChannel.subscribe()

        }
        return realtimeFlow
    }


    override suspend fun updateStatus(
        bookingId: String, newStatus: String
    ) {
        supabaseClient.from(Constants.BOOKING).update(
            {
                set("status", newStatus)
            }) {
            filter {
                eq("id", bookingId)
            }
        }
    }
}