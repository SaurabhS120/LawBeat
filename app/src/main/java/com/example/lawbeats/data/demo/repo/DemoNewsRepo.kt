package com.example.lawbeats.data.demo.repo

import com.example.app_domain.entity.NewsEntity
import com.example.app_domain.repo.NewsRepo
import com.example.app_domain.state.NewsApiState

class DemoNewsRepo:NewsRepo {
    override suspend fun invoke(uid:Int,page:Int,tid:Int,items_per_page:Int): NewsApiState.Success {
        return NewsApiState.Success(
            listOf(
                NewsEntity("Android 13 is coming to phones from Samsung, Sony, Motorola, OnePlus, Oppo, Xiaomi, and more later this year","The news is out: Android 13 is finally here and we didn't have to wait until September to get it! But for those of you who don't own a Pixel, when can you expect to get that fresh over-the-air software update on your devices? Well, Google isn't making any specific promises on behalf of the manufacturers, but it is giving users a heads-up on which ones will be joining the party by the end of 2022."),
                NewsEntity("PM modi best Leader in world","As per recent survey PM modi is rated as the one of the most popular prime minister in th world"),
                NewsEntity("Assasination of Shinzo Abe","Shinzo Abe, a former prime minister of Japan and a serving member of the House of Representatives, was assassinated on 8 July 2022 while speaking at a political event outside Yamato-Saidaiji Station in Nara City, Nara Prefecture, Japan."),
                NewsEntity("Chinese vessel reaches Sri Lanka’s Hambantota port","China’s satellite tracking vessel Yuan Wang 5, on August 16, arrived at Sri Lanka’s southern Hambantota Port, after both India and the U. S. voiced concern with the Sri Lankan government over the “military” ship’s visit."),
                NewsEntity("New Atal Pension Yojana account opening rule: No Rs 5000/month for some subscribers soon – 5 points","the scheme provides a minimum guaranteed pension of Rs Rs 1000 to Rs 5000 per month to subscribers after they attain 60 years of age. The pension amount depends on the contribution made by subscribers. However, the account will not be open for subscription for everyone soon."),
            )
        )
    }
}