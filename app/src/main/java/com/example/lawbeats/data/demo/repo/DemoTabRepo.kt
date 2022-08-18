package com.example.lawbeats.data.demo.repo

import com.example.app_domain.entity.NewsTabEntity
import com.example.app_domain.repo.TabsRepo
import com.example.app_domain.state.NewsTabApiState

class DemoTabRepo:TabsRepo {
    companion object{
        val dummy_data=listOf(
            "Top Stories",
            "News Updates",
            "Columns",
            "Bar Speaks",
            "Articles",
            "Event Corner",
            "Videos"
        ).mapIndexed { index, s ->
            NewsTabEntity(index.toString(),s)
        }
    }
    override suspend fun invoke(): NewsTabApiState {
        return NewsTabApiState.Success(dummy_data)
    }
}