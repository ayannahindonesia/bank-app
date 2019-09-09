package com.ayannah.asira.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Kabupaten extends BaseResponse{

	@SerializedName("kabupatens")
	private List<KabupatenItem> daftarKabupaten;

	public List<KabupatenItem> getDaftarKabupaten() {
		return daftarKabupaten;
	}

	public static class KabupatenItem{

		@SerializedName("nama")
		private String nama;

		@SerializedName("id")
		private String id;

		@SerializedName("id_prov")
		private String idProv;

		public void setNama(String nama){
			this.nama = nama;
		}

		public String getNama(){
			return nama;
		}

		public void setId(String id){
			this.id = id;
		}

		public String getId(){
			return id;
		}

		public String getIdProv() {
			return idProv;
		}
	}
}