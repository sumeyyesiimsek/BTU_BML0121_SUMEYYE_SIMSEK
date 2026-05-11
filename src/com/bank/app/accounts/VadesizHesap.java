package com.bank.app.accounts;
import com.bank.app.cards.*;

/**
 * BankaHesabi sınıfından miras alarak (extends) oluşturduğum vadesiz hesap sınıfıdır.
 * Para transferi ve kredi kartı borç ödemesi gibi aktif bankacılık işlemlerini bu sınıf üzerinden yönetiyorum.
 */

public class VadesizHesap extends BankaHesabi {
    private String hesapTuru;
    
   // Constructor (yapıcı metot) aracılığıyla nesne oluşturulurken hesaba başlangıç bakiyesi atıyorum ve sistemdeki kontroller için hesap türünü "Vadesiz" olarak sabitliyorum.
    

    public VadesizHesap(double bakiye) { 
        super(bakiye);
        this.hesapTuru = "Vadesiz"; 
    }
    
 // İki hesap arasındaki para transferini gerçekleşttiği metottur..
    
    public void paraTransferi(BankaHesabi aliciHesap, double miktar) {
        // Gönderen hesabın bakiyesi miktar için yeterli mi diye kontrol ediliyor 
        if (this.getBakiye() >= miktar) {
            double eskiBakiye = this.getBakiye(); 
            
            // Gönderen hesaptan miktar düşülür, alıcı hesaba eklenir 
            this.setBakiye(eskiBakiye - miktar);
            aliciHesap.setBakiye(aliciHesap.getBakiye() + miktar);
            
            // Konsol çıktısında detaylı özet gösteriliyor
            System.out.println("\n-- TRANSFER BAŞARILI --");
            System.out.println("Gönderen IBAN     : " + this.getIban());
            System.out.println("Alıcı IBAN        : " + aliciHesap.getIban());
            System.out.println("İşlem Öncesi Bakiye: " + eskiBakiye + " TL");
            System.out.println("Transfer Edilen    : " + miktar + " TL");
            System.out.println("Kalan Güncel Bakiye: " +this.getBakiye() + " TL");
            System.out.println("  ");
            
            // İşlemin detaylarını her iki tarafın geçmişine kayıt atıyorum.
            this.islemKaydet(aliciHesap.getIban() + " nolu hesaba " + miktar + " TL gönderildi.");
            aliciHesap.islemKaydet(this.getIban() + " nolu hesaptan " + miktar + " TL geldi.");
        }
        
        else {
            System.out.println("\nHATA! Yetersiz bakiye! Mevcut bakiye: " + this.getBakiye() + " TL");
        }
    }
    
 // Müşterinin kredi kartı borcunu, vadesiz hesabındaki mevcut bakiyeyi kullanarak ödemesini sağlıyorum.
    public void krediKartiBorcOdeme(KrediKarti kart, double miktar) {
        if (kart.getGuncelBorc() <= 0) {
            System.out.println("HATA! Kartın güncel borcu bulunmamaktadır.");
            return;
        }
        
        if (miktar > kart.getGuncelBorc()) {
            System.out.println("HATA! Ödeme tutarı mevcut borçtan (" + kart.getGuncelBorc() + " TL) fazla olamaz.");
            return;
        }

        if (this.getBakiye() >= miktar) {
            double eskiBorc = kart.getGuncelBorc();
            this.setBakiye(this.getBakiye() - miktar);
            kart.setGuncelBorc(eskiBorc - miktar);
            
            System.out.println("\n-- BORÇ ÖDEME ÖZETİ --");
            System.out.println("Ödenen Tutar      : " + miktar + " TL");
            System.out.println("Önceki Borç       : " + eskiBorc + " TL");
            System.out.println("Kalan Borç        : " + kart.getGuncelBorc() + " TL");
            System.out.println("Hesap Kalan Bakiye: " + this.getBakiye() + " TL");
            System.out.println(" ");
            
            this.islemKaydet(kart.getKartNumarasi() + " nolu karta " + miktar + " TL ödendi.");
        } 
        else {
            System.out.println("HATA! Yetersiz hesap bakiyesi!");
        }
    }

 // Değişkene dışarıdan kontrollü erişim sağlamak için get ve set metotlarını tanımlıyorum.
    public String getHesapTuru() { 
    	return hesapTuru; 
    	}
    public void setHesapTuru(String hesapTuru) { 
    	this.hesapTuru = hesapTuru; 
    	}

    //Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    
       public String toString() {
        return super.toString() + " Hesap Türü: " + hesapTuru;
    }
}