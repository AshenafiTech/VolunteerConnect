package com.mobile.volunteerconnect.view.organization.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobile.volunteerconnect.view.components.TopBarComponent

@Composable
fun ViewApplicants(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
        ,
        color = Color(0xFFF8F9FB)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarComponent("ViewApplciant")

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewApplicantPreview(){
    ViewApplicants()
}








//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.mobile.volunteerconnect.view.pages.components.ApplicantRow
//import com.mobile.volunteerconnect.view.pages.components.ApplicantsSummary
//import com.mobile.volunteerconnect.view.pages.components.TopBarComponent
//import com.mobile.volunteerconnect.view.pages.viewmodel.ApplicantsViewModel
//import com.mobile.volunteerconnect.data.model.ApplicantItem
//import androidx.compose.foundation.lazy.items
//import androidx.compose.ui.text.font.FontWeight
//import androidx.navigation.NavController
//
//
//@Composable
//fun ViewApplicants(
//    navController: NavController,
//    viewModel: ApplicantsViewModel = hiltViewModel()
//) {
//    val isLoading by viewModel.isLoading.collectAsState()
//    val applicants by viewModel.applicants.collectAsState()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF8F9FB))
//    ) {
//        if (isLoading) {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        } else {
//            Column(modifier = Modifier.fillMaxSize()) {
//                TopBarComponent("Applicants List")
//
//                ApplicantsSummary(applicants.size)
//
//                if (applicants.isEmpty()) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(32.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "No applicants yet",
//                            style = MaterialTheme.typography.bodyLarge.copy(
//                                color = Color.Gray,
//                                fontWeight = FontWeight.Medium
//                            )
//                        )
//                    }
//                } else {
//                    LazyColumn(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        items(applicants) { applicant ->
//                            ApplicantRow(applicant = applicant, navController = navController)
//                            HorizontalDivider(
//                                thickness = 0.5.dp,
//                                color = Color.LightGray,
//                                modifier = Modifier.padding(horizontal = 16.dp)
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
