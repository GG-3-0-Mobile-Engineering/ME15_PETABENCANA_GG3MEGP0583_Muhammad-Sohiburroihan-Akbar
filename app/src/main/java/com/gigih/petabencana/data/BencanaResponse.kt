package com.gigih.petabencana.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BencanaResponse(

	@field:SerializedName("result")
	val result: Results,

	@field:SerializedName("statusCode")
	val statusCode: Int
) : Parcelable

@Parcelize
data class FireRadius(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable

@Parcelize
data class ReportData(

	@field:SerializedName("flood_depth")
	val floodDepth: Int? = null,

	@field:SerializedName("report_type")
	val reportType: String? = null,

	@field:SerializedName("impact")
	val impact: Int? = null,

	@field:SerializedName("condition")
	val condition: Int? = null,

	@field:SerializedName("accessabilityFailure")
	val accessabilityFailure: Int? = null,

	@field:SerializedName("structureFailure")
	val structureFailure: Int? = null,

	@field:SerializedName("personLocation")
	val personLocation: PersonLocation? = null,

	@field:SerializedName("fireLocation")
	val fireLocation: FireLocation? = null,

	@field:SerializedName("fireRadius")
	val fireRadius: FireRadius? = null,

	@field:SerializedName("fireDistance")
	val fireDistance: Double? = null,

	@field:SerializedName("airQuality")
	val airQuality: Int? = null,

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("volcanicSigns")
	val volcanicSigns: List<Int?>? = null,

	@field:SerializedName("evacuationArea")
	val evacuationArea: Boolean? = null,

	@field:SerializedName("evacuationNumber")
	val evacuationNumber: Int? = null
) : Parcelable

@Parcelize
data class FireLocation(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: String? = null
) : Parcelable

@Parcelize
data class Output(

	@field:SerializedName("geometries")
	val geometries: List<GeometriesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable

@Parcelize
data class Tags(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String? = null,

	@field:SerializedName("district_id")
	val districtId: Int? = null,

	@field:SerializedName("local_area_id")
	val localAreaId: Int? = null,

	@field:SerializedName("region_code")
	val regionCode: String? = null
) : Parcelable

@Parcelize
data class PersonLocation(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
) : Parcelable

@Parcelize
data class Objects(

	@field:SerializedName("output")
	val output: Output? = null
) : Parcelable

@Parcelize
data class Results(

	@field:SerializedName("objects")
	val objects: Objects? = null,

	@field:SerializedName("bbox")
	val bbox: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("arcs")
	val arcs: List<Double?>? = null
) : Parcelable

@Parcelize
data class Properties(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("disaster_type")
	val disasterType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tags")
	val tags: Tags? = null,

	@field:SerializedName("partner_icon")
	val partnerIcon: Int? = null,

	@field:SerializedName("report_data")
	val reportData: ReportData? = null,

	@field:SerializedName("pkey")
	val pkey: String,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("partner_code")
	val partnerCode: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class GeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: Properties? = null
) : Parcelable

@Parcelize
@Entity(tableName = "bencana")
data class BencanaTable(
	@field:ColumnInfo(name = "id")
	@field:PrimaryKey
	val id: String,

	@field:ColumnInfo(name = "title")
	val title: String?,

	@field:ColumnInfo(name = "imageUrl")
	val imageUrl: String?,

	@field:ColumnInfo(name = "instanceRegionCode")
	val instanceRegionCode: String?,

	@field:ColumnInfo(name = "type")
	val type: String?,

	@field:ColumnInfo(name = "lat")
	val lat: Double?,

	@field:ColumnInfo(name = "lng")
	val lng: Double?
) : Parcelable