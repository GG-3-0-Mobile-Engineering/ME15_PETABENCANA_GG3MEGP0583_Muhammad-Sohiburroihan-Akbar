package com.gigih.petabencana.data.response

import com.google.gson.annotations.SerializedName

data class DisasterByLocationResponse(

	@field:SerializedName("result")
	val result: Result,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class Tags(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String,

	@field:SerializedName("district_id")
	val districtId: Any,

	@field:SerializedName("local_area_id")
	val localAreaId: Any,

	@field:SerializedName("region_code")
	val regionCode: String
)

data class Output(

	@field:SerializedName("geometries")
	val geometries: List<GeometriesItem>,

	@field:SerializedName("type")
	val type: String
)

data class Result(

	@field:SerializedName("objects")
	val objects: Objects,

	@field:SerializedName("bbox")
	val bbox: List<Any>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("arcs")
	val arcs: List<Any>
)

data class GeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Any>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("properties")
	val properties: Properties
)

data class Objects(

	@field:SerializedName("output")
	val output: Output
)

data class ReportData(

	@field:SerializedName("flood_depth")
	val floodDepth: Int,

	@field:SerializedName("report_type")
	val reportType: String
)

data class Properties(

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("disaster_type")
	val disasterType: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("title")
	val title: Any,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("tags")
	val tags: Tags,

	@field:SerializedName("partner_icon")
	val partnerIcon: Any,

	@field:SerializedName("report_data")
	val reportData: ReportData,

	@field:SerializedName("pkey")
	val pkey: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("partner_code")
	val partnerCode: Any,

	@field:SerializedName("status")
	val status: String
)
