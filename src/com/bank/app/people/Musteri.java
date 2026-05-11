package com.bank.app.people;

//Kullanacağımız diğer paketlerdeki sınıfları projeye dahil ediyorum.
import com.bank.app.accounts.*;
import com.bank.app.cards.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Müşteri sınıfı bankadaki en temel işlemlerin yönetildiği yerdir.
 * Burada müşterinin hesaplarını ve kredi kartlarını listeler halinde saklıyorum.
 */

public class Musteri extends Kisi {
    private String musteriNumarasi;
    private ArrayList<BankaHesabi> hesaplar;
    private ArrayList<KrediKarti> krediKartlari;

    // Constructor (yapıcı metot) aracılığıyla müşteri nesnesi oluşturulurken temel bilgileri alıyorum.
    public Musteri(String ad, String soyad, String email, String telefonNumarasi) {
        super(ad, soyad, email, telefonNumarasi);
        this.musteriNumarasi = rastgeleMusteriNoUret();
        this.hesaplar = new ArrayList<>();
        this.krediKartlari = new ArrayList<>(); 
    }
    
    // Müşteri numaralarını MUS- ön ekiyle ve 5 haneli rastgele sayılarla üretiyorum.
    private String rastgeleMusteriNoUret() {
        Random rnd = new Random();
        return "MUS-" + (10000 + rnd.nextInt(90000)); // Müşteri numaralarını 5 haneli rastgele sayılarla üretiyorum.
    }

 // Gelen parametreye göre müşteriye yeni bir Vadesiz veya Yatırım hesabı tanımlıyorum.
    
    public void hesapEkle(String hesapTuru) {
        String tur = hesapTuru.trim();
        
     // equalsIgnoreCase ile büyük/küçük harf duyarlılığını ortadan kaldırarak hesap türünü kontrol ediyorum.
        if (tur.equalsIgnoreCase("Vadesiz")) {
            VadesizHesap vHesap = new VadesizHesap(0.0);
            hesaplar.add(vHesap);
            System.out.println("Başarılı! Vadesiz hesap eklendi. IBAN: " + vHesap.getIban());
        } 
        else if (tur.equalsIgnoreCase("Yatırım") || tur.equalsIgnoreCase("Yatirim") || 
                   tur.equalsIgnoreCase("yatırım") || tur.equalsIgnoreCase("yatirim")) {
            YatirimHesabi yHesap = new YatirimHesabi(0.0);
            hesaplar.add(yHesap);
            System.out.println("Başarılı! Yatırım hesabı eklendi. IBAN: " + yHesap.getIban());
        } 
        else {
            System.out.println("HATA! Geçersiz hesap türü girdiniz.");
        }
    }
    
    // Kredi kartı nesnesi oluşturup müşterinin kart listesine ekliyorum.
    public void krediKartiEkle(double limit) {
        KrediKarti kart = new KrediKarti(limit, 0.0);
        krediKartlari.add(kart);
        System.out.println("Başarılı! Kredi kartı eklendi. Kart No: " + kart.getKartNumarasi());
    }
    
    // Hesabı silmeden önce bakiyenin 0 olması gerektiğini kontrol ediyorum.
    public void hesapSil(BankaHesabi hesap) {
        if (hesap.getBakiye() > 0) {
            System.out.println("\nHATA! Hesap silinemedi! Bakiyeniz: " + hesap.getBakiye() + " TL");
            System.out.println("Lütfen bu parayı şu hesaplarınızdan birine aktarın:");
            
         // Müşterinin diğer hesaplarını listeliyorum.
            for(BankaHesabi h : hesaplar) {
                if(!h.getIban().equals(hesap.getIban())) {
                    System.out.println("- " + h.getIban() + " (" + h.getBakiye() + " TL)");
                }
            }
        } 
        else {
            hesaplar.remove(hesap);
            System.out.println("\nBaşarılı! " + hesap.getIban() + " numaralı hesap kapatıldı.");
        }
    }
    
    
 // Kartı silmeden önce borcun 0 olması gerektiğini kontrol ediyorum.
    public void krediKartiSil(KrediKarti kart) {
        if (kart.getGuncelBorc() > 0) {
            System.out.println("HATA! Lütfen öncelikle borç ödemesi yapınız."); 
        }
        else {
            krediKartlari.remove(kart);
            System.out.println("Başarılı! Kart başarıyla silindi.");
        }
    }

 // Değişkenlere dışarıdan kontrollü erişim sağlamak için Get ve Set metotlarını tanımlıyorum.
    public String getMusteriNumarasi() { 
    	return musteriNumarasi;
    	}
    public void setMusteriNumarasi(String musteriNumarasi) { 
    	this.musteriNumarasi = musteriNumarasi;
    	}

    public ArrayList<BankaHesabi> getHesaplar() { 
    	return hesaplar; 
    	}
    public void setHesaplar(ArrayList<BankaHesabi> hesaplar) { 
    	this.hesaplar = hesaplar; 
    	}

    public ArrayList<KrediKarti> getKrediKartlari() { 
    	return krediKartlari; 
    	}
    public void setKrediKartlari(ArrayList<KrediKarti> krediKartlari) { 
    	this.krediKartlari = krediKartlari;
    	}

 // Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    public String toString() {
        return super.toString() + " Müşteri No: " + musteriNumarasi + " Hesap Sayısı: " + hesaplar.size() + " Kart Sayısı: " + krediKartlari.size();
    }
}