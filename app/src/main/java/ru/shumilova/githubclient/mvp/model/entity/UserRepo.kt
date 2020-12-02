package ru.shumilova.githubclient.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRepo(
    @Expose val id: String? = null,
    @Expose val login: String? = null,

    @Expose
    @SerializedName("name") val name: String? = null,

    @SerializedName("forks_url")
    @Expose
    val forksUrl: String? = null,
    val forksCount: Int = 0
) : Parcelable