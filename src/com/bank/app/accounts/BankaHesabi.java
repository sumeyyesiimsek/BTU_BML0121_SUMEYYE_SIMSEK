package com.bank.app.accounts;

import java.util.ArrayList;
import java.util.Random;

/**
 * BankaHesabi tüm hesap türleri için (Vadesiz/Yatırım) temel sınıftır.
 * Her hesabın bir IBAN numarası ve bakiyesi bulunur.
 */

public class BankaHesabi {
	// Veri güvenliğini (Encapsulation) sağlamak için tüm özellikleri private olarak tanımladım.
    private String iban;
    private double bakiye;
    
 // Tüm hesap hareketlerini takip etmek için bir liste tutuyorum.
    
    public ArrayList<String> islemGecmisi;

    public BankaHesabi(double bakiye) {
        this.iban = rastgeleIbanUret();
        this.bakiye = bakiye; 
        this.islemGecmisi = new ArrayList<>();
        // İlk açılış işlemini kaydediyoruz
        islemKaydet("Hesap açılışı gerçekleştirildi. Başlangıç bakiyesi: " + bakiye);
    }
    
 // Random ve StringBuilder sınıflarını kullanarak, "TR" ön ekiyle başlayan ve ardına 24 haneli rastgele sayı gelen standart bir IBAN metni üretiyorum.
    
    private String rastgeleIbanUret() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder("TR");
        for (int i = 0; i < 24; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

 // Yapılan her finansal işlemi metin formatında işlem geçmişi listesine (ArrayList) kaydediyorum.
    
    public void islemKaydet(String islem) {
        islemGecmisi.add(islem);
    }
    
   // İlgili hesabın o güne kadarki tüm hareketlerini ve güncel bakiyesini,liste boşsa uyarı verecek şekilde konsola yazdırıyorum.
       
  
    public void islemGecmisiniYazdir() {
        System.out.println("\n--- HESAP HAREKET DETAYLARI (" + iban + ") ---");
        if (islemGecmisi.isEmpty()) {
            System.out.println("Henüz bir işlem bulunmamaktadır.");
        } 
        else {
            for (String kayit : islemGecmisi) {
                System.out.println("-> " + kayit);
            }
        }
        System.out.println(" ");
        System.out.println("GÜNCEL TOPLAM BAKİYE: " + this.bakiye + " TL");
    }

 // Değişkenlere dışarıdan güvenli erişim sağlamak için get ve set metotlarını tanımlıyorum.
    public String getIban() {
    	return iban;
    	}
    public void setIban(String iban) {
    	this.iban = iban;
    	}

    public double getBakiye() {
    	return bakiye; 
    	}
    public void setBakiye(double bakiye) {
    	this.bakiye = bakiye; 
    	}

  //Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    
    public String toString() {
        return "IBAN: " + iban + " Bakiye: " + bakiye + " TL";
    }
}