package com.bank.app.people;

/**
 * Bu sınıf projedeki tüm insanların (personel ve müşteri) ortak noktalarını topladığım temel sınıftır.
 * Kod tekrarını önlemek amacıyla ad, soyad, e-posta ve telefon gibi her bireyde bulunması gereken ortak özellikleri burada tanımladım.
 */

public class Kisi {
	
	// Veri güvenliğini sağlamak için değişkenleri private erişim belirleyicisi ile tanımladım.
	private String ad;
    private String soyad;
    private String email;
    private String telefonNumarasi;  
    
 // Constructor ile bir kişi oluşturulduğunda temel bilgileri başlangıç parametresi olarak olarak alıyorum.
 
 public Kisi(String ad, String soyad, String email, String telefonNumarasi) {
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.telefonNumarasi = telefonNumarasi; 
    }

    //Değişkenlere dışarıdan kontrollü erişim sağlamak için Get ve Set metotlarını tanımlıyorum.
    public String getAd() {
    	return ad;
    	}
    public void setAd(String ad) {  
    	this.ad = ad;
    	}

    public String getSoyad() { 
    	return soyad;
    	}
    public void setSoyad(String soyad) { 
    	this.soyad = soyad; 
    	}

    public String getEmail() { 
    	return email; 
    	}
    public void setEmail(String email) { 
    	this.email = email; 
    	}

    public String getTelefonNumarasi() {
    	return telefonNumarasi;
    	}
    public void setTelefonNumarasi(String telefonNumarasi) { 
    	this.telefonNumarasi = telefonNumarasi;
    	}

 // Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    public String toString() {
        return "Ad: " + ad + " Soyad: " + soyad + " Email: " + email + " Telefon: " + telefonNumarasi;
    }
}


