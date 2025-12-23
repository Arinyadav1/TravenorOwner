package com.example.travenorowner.features.ownerRequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travenorowner.features.components.TravenorIconButton
import com.example.travenorowner.features.components.TravenorShimmerLoadingOverlay
import com.example.travenorowner.ui.theme.AppColors
import com.example.travenorowner.ui.theme.AppTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun OwnerRequestScreen(
    modifier: Modifier = Modifier,
    viewModel: OwnerRequestViewModel = koinViewModel()
) {
    val state = viewModel.stateFlow.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = state.destination?.destinationName ?: "",
                style = AppTypography.titleMediumSfPro,
            )

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = "Active Now",
                style = AppTypography.bodyLarge,
                color = AppColors.lightGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = AppColors.dividerColor,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            if (state.isShowRequestCard) {
                RequestCard(
                    onAccept = { viewModel.onAction(OwnerRequestAction.AcceptRequest) },
                    onReject = { viewModel.onAction(OwnerRequestAction.RejectRequest) },
                    state = state
                )
            }

            Spacer(modifier = Modifier.weight(1.2f))
        }

        if (state.isLoading) {
            TravenorShimmerLoadingOverlay()
        }
    }
}

@Composable
fun RequestCard(
    onAccept: () -> Unit,
    onReject: () -> Unit,
    state: OwnerRequestState
) {
    Column(
        modifier = Modifier
            .width(260.dp)
            .background(
                color = AppColors.lightSub.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val status = remember(state.requestCardStatus) {
            when (state.requestCardStatus) {
                RequestCardStatus.Accepted -> "Accepted Successfully"
                RequestCardStatus.Rejected -> "Rejected Successfully"
                RequestCardStatus.NewRequest -> "New Request from User"
            }
        }

        Text(
            text = status,
            style = AppTypography.labelLargeRegular,
            color = AppColors.lightSub
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            @Composable
            fun acceptButton() {
                TravenorIconButton(
                    icon = Icons.Default.Check,
                    iconTint = AppColors.lightSub,
                    onClick = onAccept
                )
            }

            @Composable
            fun rejectButton() {
                TravenorIconButton(
                    icon = Icons.Default.Close,
                    iconTint = Color.Red,
                    onClick = onReject
                )
            }

            when (state.requestCardStatus) {
                RequestCardStatus.Accepted -> acceptButton()
                RequestCardStatus.Rejected -> rejectButton()
                RequestCardStatus.NewRequest -> {
                    acceptButton()
                    rejectButton()
                }
            }
        }
    }
}


