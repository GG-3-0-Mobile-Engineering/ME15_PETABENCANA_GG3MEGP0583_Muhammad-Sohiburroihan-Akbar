package com.gigih.petabencana.utils

import com.gigih.petabencana.data.BencanaTable

object DataDummy {
    fun generateDummyBencanaEntity(): List<BencanaTable> {
        val bencanaList = ArrayList<BencanaTable>()
        for (i in 0..10) {
            val bencana = BencanaTable(
                "$i",
                "Title $i",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "ID-JT",
                "flood",
                -3.3167723638072197,
                114.593157003658,
            )
            bencanaList.add(bencana)
        }
        return bencanaList
    }
}