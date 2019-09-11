package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Kecamatan extends BaseResponse{

	@SerializedName("data")
	private List<KecatamanItem> daftarKecamatan;

	public List<KecatamanItem> getDaftarKecamatan(){
		return daftarKecamatan;
	}

	public static class KecatamanItem{

		@SerializedName("name")
		private String nama;

		@SerializedName("id")
		private String id;

		@SerializedName("city_id")
		private String idKabupaten;

		public String getNama(){
			return nama;
		}

		public String getId(){
			return id;
		}

		public String getIdKabupaten() {
			return idKabupaten;
		}
	}
}