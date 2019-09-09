package com.ayannah.asira.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Kelurahan extends BaseResponse{

	@SerializedName("desas")
	private List<KelurahanItem> daftarDesa;

	public List<KelurahanItem> getDaftarDesa(){
		return daftarDesa;
	}

	public static class KelurahanItem{

		@SerializedName("id_kecamatan")
		private String idKecamatan;

		@SerializedName("nama")
		private String nama;

		@SerializedName("id")
		private String id;

		public String getIdKecamatan(){
			return idKecamatan;
		}

		public String getNama(){
			return nama;
		}

		public String getId(){
			return id;
		}

	}
}