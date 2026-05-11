package com.bank.app.people;

import java.util.ArrayList;
import java.util.Random;

/**
 * Banka personelini temsil eden bu sınıfı, ortak özellikleri kullanabilmek adına 
 * Kisi sınıfından miras alarak (extends) oluşturdum.
 * Her personelin sisteme özel bir kimlik numarası ve ilgilendiği bir müşteri portföyü bulunmaktadır.
 */

public class BankaPersoneli extends Kisi {
	// Personelin tekil ID bilgisini ve yönettiği müşterilerin listesini private olarak tanımladım.
    private String personelID;
    private ArrayList<Musteri> musteriler; // personellerin ilgilendiği müşterileri bu listede tutuyorum.
    
   
     //Constructor ile personel oluşturulduğunda ad, soyad gibi temel bilgileri super anahtar kelimesiyle üst sınıfa gönderiyorum. 
     // Personel nesnesi oluştuğu an otomatik ID ataması yapıp müşteri listesini bellekte hazırlıyorum.
     
    
    public BankaPersoneli(String ad, String soyad, String email, String telefonNumarasi) {
        super(ad, soyad, email, telefonNumarasi); // Üst sınıftaki bilgileri gönderiyorum.
        this.personelID = rastgeleIDUret(); // Personel oluşturulduğu an otomatik personele özel ID üretiliyor..
        this.musteriler = new ArrayList<>();
    } 

 // Random sınıfını kullanarak PER- ile başlayan 4 haneli rastgele bir personel ID'si üretiyorum.
    private String rastgeleIDUret() {
        Random rnd = new Random();
        return "PER-" + (1000 + rnd.nextInt(9000)); 
    }

    //Değişkenlere dışarıdan kontrollü erişim sağlamak için Get ve Set metotlarını tanımlıyorum
    public String getPersonelID() {
    	return personelID; 
    	}
    
    public void setPersonelID(String personelID) {
    	this.personelID = personelID; 
    	}

    public ArrayList<Musteri> getMusteriler() {
    	return musteriler;
    	}
    public void setMusteriler(ArrayList<Musteri> musteriler) { 
    	this.musteriler = musteriler; 
    	}

 // Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    public String toString() {
        return super.toString() + " Personel ID: " + personelID + " Sorumlu Olduğu Müşteri Sayısı: " + musteriler.size();
    }
}