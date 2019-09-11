package com.ayannah.asira.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Kelurahan extends BaseResponse{

	@SerializedName("data")
	private List<KelurahanItem> daftarDesa;

	public List<KelurahanItem> getDaftarDesa(){
		return daftarDesa;
	}

	public static class KelurahanItem{

		@SerializedName("district_id")
		private String idKecamatan;

		@SerializedName("name")
		private String nama;

		@SerializedName("id")
		private String id;

		@SerializedName("zip_code")
		private int zip_code;

		@SerializedName("area_code")
		private String area_code;

		public String getIdKecamatan(){
			return idKecamatan;
		}

		public String getNama(){
			return nama;
		}

		public String getId(){
			return id;
		}

		public int getZip_code() {
			return zip_code;
		}

		public String getArea_code() {
			return area_code;
		}
	}
}