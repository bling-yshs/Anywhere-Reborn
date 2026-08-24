package com.yshs.anywhere_.api

import com.yshs.anywhere_.model.cloud.RuleEntity
import retrofit2.Call
import retrofit2.http.GET

interface RuleApi {

  @GET("rules/rules.json")
  fun requestAllRules(): Call<List<RuleEntity>>
}
