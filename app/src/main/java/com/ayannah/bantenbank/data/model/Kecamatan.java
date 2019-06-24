package com.ayannah.bantenbank.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Kecamatan extends BaseResponse{

	@SerializedName("kecamatans")
	private List<KecatamanItem> daftarKecamatan;

	public List<KecatamanItem> getDaftarKecamatan(){
		return daftarKecamatan;
	}

	public static class KecatamanItem{

		@SerializedName("nama")
		private String nama;

		@SerializedName("id")
		private String id;

		@SerializedName("id_kabupaten")
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