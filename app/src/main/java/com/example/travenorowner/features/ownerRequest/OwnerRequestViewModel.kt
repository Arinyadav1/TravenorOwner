package com.example.travenorowner.features.ownerRequest

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.travenorowner.data.BookingRepository
import com.example.travenorowner.data.DestinationsRepository
import com.example.travenorowner.features.BaseViewModel
import com.example.travenorowner.model.Booking
import com.example.travenorowner.model.Destination
import com.example.travenorowner.model.Status
import com.example.travenorowner.navigation.OwnerRequestScreenRoute
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerRequestViewModel(
    private val bookingRepository: BookingRepository,
    private val destinationsRepository: DestinationsRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OwnerRequestState, OwnerRequestAction, OwnerRequestEvent>(OwnerRequestState()) {

    private val route = savedStateHandle.toRoute<OwnerRequestScreenRoute>()

    init {
        getDestination(route.destinationId)
        bookingStatus()
    }

    private fun getDestination(id: String) {
        mutableStateFlow.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            destinationsRepository.getDestination(id)
                .catch {}
                .collect { data ->
                    mutableStateFlow.update {
                        it.copy(
                            destination = data,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun bookingStatus() {
        viewModelScope.launch {
            bookingRepository.bookingStatus()
                ?.catch { }
                ?.collect { booking ->
                    if (
                        booking.destinationId == route.destinationId
                    ) {
                        mutableStateFlow.update {
                            it.copy(
                                isShowRequestCard = true,
                                booking = booking
                            )
                        }
                    }
                }

        }
    }

    private fun acceptRejectRequest(status: String) {
        mutableStateFlow.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            try {
                bookingRepository.updateStatus(state.booking.id!!, status)
                mutableStateFlow.update {
                    it.copy(
                        isLoading = false,
                        requestCardStatus = if (status == Status.Accept.name) RequestCardStatus.Accepted else RequestCardStatus.Rejected
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun handleAction(action: OwnerRequestAction) {
        when (action) {
            OwnerRequestAction.AcceptRequest -> {
                acceptRejectRequest(Status.Accept.name)
            }

            OwnerRequestAction.RejectRequest -> {
                acceptRejectRequest(Status.Reject.name)
            }
        }
    }
}


data class OwnerRequestState(
    val destination: Destination? = null,
    val booking: Booking = Booking(),
    val isShowRequestCard: Boolean = false,
    val requestCardStatus: RequestCardStatus = RequestCardStatus.NewRequest,
    val isLoading: Boolean = false
)

sealed interface OwnerRequestAction {
    object AcceptRequest : OwnerRequestAction
    object RejectRequest : OwnerRequestAction
}

sealed interface OwnerRequestEvent {

}

enum class RequestCardStatus {
    Accepted,
    Rejected,
    NewRequest
}