package com.bank.app.service;

//Kullanacağımız diğer paketlerdeki sınıfları projeye dahil ediyorum.

import com.bank.app.people.*;
import java.util.ArrayList;

/**
 * Bankadaki genel verileri tutmak ve iş mantığını yönetmek için oluşturduğum servis sınıfıdır.
 * Sistemdeki tüm müşteri ve personellerin listelerini burada merkezi olarak saklıyorum.
 */

public class BankaService {
	// Bankaya kayıtlı müşteri ve personelleri dinamik olarak tutmak için ArrayList yapılarını tanımlıyorum.
    private ArrayList<Musteri> bankaMusterileri;
    private ArrayList<BankaPersoneli> bankaPersonelleri;
    
    // Constructor (yapıcı metot) ile sınıf çağrıldığında listelerin bellekte boş olarak oluşturulmasını sağlıyorum.
    public BankaService() {
        this.bankaMusterileri = new ArrayList<>();
        this.bankaPersonelleri = new ArrayList<>(); 
    } 

    // Dışarıdan gelen yeni müşteri nesnesini bankanın genel müşteri listesine ekliyorum.
    public void musteriKaydet(Musteri musteri) {
        bankaMusterileri.add(musteri);
        System.out.println("Müşteri sisteme kaydedildi: " + musteri.getAd() + " " + musteri.getSoyad());
    }
    
    // Dışarıdan gelen yeni personel nesnesini bankanın genel personel listesine ekliyorum.
    public void personelKaydet(BankaPersoneli personel) {
        bankaPersonelleri.add(personel);
        System.out.println("Personel sisteme kaydedildi: " + personel.getAd() + " " + personel.getSoyad());
    }
    
    // For-each döngüsü kullanarak listedeki tüm müşterilerin bilgilerini konsola sırayla yazdırıyorum.
    public void musteriListesiGoruntule() {
        System.out.println("-> Banka Müşterileri");
        for(Musteri m : bankaMusterileri) {
            System.out.println(m.toString());
        }
    }
}