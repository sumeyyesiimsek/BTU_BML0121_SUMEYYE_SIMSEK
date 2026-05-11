package com.bank.app.cards;
import java.util.Random;

 //Kredi kartı borç ve limit bilgilerini yönettiğim sınıftır.
 

public class KrediKarti {
	
	// Kartın temel özelliklerini veri gizliliği (Encapsulation) kurallarına uygun olarak private tanımladım.
    private String kartNumarasi; 
    private double limit;
    private double guncelBorc; 
    private double kullanilabilirLimit;

    //Constructor (yapıcı metot) ile kart oluşturulduğunda limit ve borç bilgilerini alıyorum.
    // Kart numarası sistem tarafından otomatik atanırken, kullanılabilir limiti de anlık olarak hesaplıyorum.
    
    public KrediKarti(double limit, double guncelBorc) {
        this.kartNumarasi = rastgeleKartNoUret(); // Kart numarası sistem tarafından atanıyor.
        this.limit = limit;
        this.guncelBorc = guncelBorc;
        this.kullanilabilirLimit = limit - guncelBorc; // Mevcut limiti hesaplıyorum.
    }
    
 //Random ve StringBuilder sınıflarını kullanarak, XXXX-XXXX-XXXX-XXXX formatında 16 haneli rastgele bir kredi kartı numarası üretiyorum.
    
    private String rastgeleKartNoUret() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("%04d", rnd.nextInt(10000)));
            if (i < 3) sb.append("-");
        }
        return sb.toString();
    }
    
    // 'krediKartiBorcOdeme' işlemini test edebilmek için öncelikle kartta bir borç oluşması gerekiyordu. 
    // Bu nedenle limit kontrolü yaparak güncel borcu artıran bu harcama metodunu ekledim.
    public void harcamaYap(double miktar) {
        if(miktar <= kullanilabilirLimit) {
            guncelBorc += miktar;
            kullanilabilirLimit -= miktar;
            System.out.println(miktar + " TL tutarında harcama yapıldı.");
        }
        else {
            System.out.println("Yetersiz kart limiti.");
        }
    }

 // Değişkenlere dışarıdan kontrollü erişim sağlamak için Get ve Set metotlarını kullanıyorum.
    public String getKartNumarasi() { 
    	return kartNumarasi;
    	}
    
    public void setKartNumarasi(String kartNumarasi) {
    	this.kartNumarasi = kartNumarasi;
    	}

    public double getLimit() { 
    	return limit;
    	}
    
    public void setLimit(double limit) { 
    	this.limit = limit;
    	}
    
    public double getGuncelBorc() {
    	return guncelBorc;
    	}
    
 // Borç miktarını güncellerken, matematiksel tutarlılığı sağlamak adına kullanılabilir limiti de otomatik olarak yeniden hesaplıyorum.

    public void setGuncelBorc(double guncelBorc) { 
        this.guncelBorc = guncelBorc; 
        this.kullanilabilirLimit = this.limit - this.guncelBorc;
    }

    public double getKullanilabilirLimit() {
    	return kullanilabilirLimit;
    	}
    public void setKullanilabilirLimit(double kullanilabilirLimit) {
    	this.kullanilabilirLimit = kullanilabilirLimit;
    	}
    
 // Nesnenin verilerini metin olarak ekrana yazdırmak için toString metodunu bu sınıfa özgü olacak şekilde yeniden uyarlıyorum (override).
    
    public String toString() {
        return "Kart No: " + kartNumarasi + " Limit: " + limit + " Güncel Borç: " + guncelBorc + " Kullanılabilir Limit: " + kullanilabilirLimit;
    }
}