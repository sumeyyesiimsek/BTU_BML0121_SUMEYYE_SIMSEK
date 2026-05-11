package com.bank.app.accounts;

/**
 * BankaHesabi sınıfından miras alarak (extends) oluşturduğum yatırım hesabı sınıfıdır.
 * Bu sınıf üzerinden yatırım amaçlı hesaba para ekleme ve para çekme işlemlerini yönetiyorum.
 */

public class YatirimHesabi extends BankaHesabi {
	// Hesap türünü sistemsel kontrollerde kullanmak için varsayılan olarak "Yatırım" atıyorum.
    private String hesapTuru= "Yatırım";
    
    // Constructor (yapıcı metot) ile hesap oluşturulduğunda, başlangıç bakiyesini super anahtar kelimesiyle üst sınıfa (BankaHesabi) gönderiyorum.
     
    public YatirimHesabi(double bakiye) {
        super(bakiye); 
    }
    
 // Hesaba dışarıdan gelen tutarı ekleyerek bakiyeyi güncelliyorum ve yapılan bu işlemi bankanın işlem geçmişine anlık olarak kaydediyorum.
    
    public void paraEkle(double miktar) {
        this.setBakiye(this.getBakiye() + miktar);
        this.islemKaydet(miktar + " TL yatırım hesabına yatırıldı.");
        System.out.println("\nBaşarılı! " + miktar + " TL Yatırım hesabına eklendi. Yeni Bakiye: " + this.getBakiye());
    }
   
    //Hesaptan para çekme işlemi yapmadan önce, girilen miktarın 0'dan büyük olmasını ve mevcut bakiyenin bu işlemi karşılamaya yetip yetmediğini kontrol ediyorum.
    
       public void paraCek(double miktar) {
        if (this.getBakiye() >= miktar && miktar > 0) {
            this.setBakiye(this.getBakiye() - miktar);
            this.islemKaydet(miktar + " TL hesaptan çekildi.");
            System.out.println(miktar + " TL yatırım hesabınızdan çekildi.");
        } 
        else {
            System.out.println("Yetersiz bakiye veya geçersiz işlem.");
        }
    }
       
    // Değişkene dışarıdan kontrollü erişim sağlamak için get ve set metotlarını tanımlıyorum.

    public String getHesapTuru() {
    	return hesapTuru; 
    	}
    public void setHesapTuru(String hesapTuru) {
    	this.hesapTuru = hesapTuru; 
    	}
    
 // Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    
        public String toString() {
        return super.toString() + ", Hesap Türü: " + hesapTuru;
    }
}