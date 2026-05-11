package com.bank.app.main;

//Gerekli paketleri ve Java sınıflarını projeye dahil ediyorum.
import com.bank.app.people.*;
import com.bank.app.accounts.*;
import com.bank.app.cards.*;
import com.bank.app.service.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Bu sınıf uygulamanın başlangıç noktasıdır. 
 * İstenen senaryoların test edilebilmesi için konsol üzerinden çalışan interaktif bir menü tasarladım.
 */

public class Main {
	// Konsoldan veri almak için Scanner nesnesini ve sistemdeki müşterileri tutmak için ArrayList yapısını tanımlıyorum.
    private static Scanner input = new Scanner(System.in);
    private static BankaService service = new BankaService();
    private static ArrayList<Musteri> tumMusteriler = new ArrayList<>();
    
 // Sistemi her başlattığımda yeni veri girmekle uğraşmamak için başlangıçta kendimi varsayılan müşteri olarak ekliyorum.
    public static void main(String[] args) {
        Musteri m1 = new Musteri("Yaren", "Yoldaş", "yaren@gmail.com", "0503456789");
        m1.hesapEkle("Vadesiz");
    
        
     // Transfer ve ödeme testlerini yapabilmek için bu hesaba 5000 TL başlangıç bakiyesi tanımlıyorum.
        BankaHesabi testHesabi = m1.getHesaplar().get(0);
        testHesabi.setBakiye(5000); 
        testHesabi.islemKaydet("Test amaçlı başlangıç bakiyesi yatırıldı: 5000.0 TL");
        System.out.println("Varsayılan müşteri Yaren Yoldaş için 5000.0 TL başlangıç bakiyesi başarıyla tanımlandı.");
        
        tumMusteriler.add(m1);
        service.musteriKaydet(m1);
        
     // Programın kullanıcı çıkış yapana kadar çalışmasını sağlamak için sonsuz bir while döngüsü kullanıyorum.
        while (true) {
                System.out.println("\n-- BANKA OTOMASYON SİSTEMİ --");
                System.out.println("1 - Yeni Müşteri Kaydı");
                System.out.println("2 - Müşteri Girişi / İşlem Yap");
                System.out.println("0 - Çıkış");
                System.out.print("Seçim: ");
                
                String anaSecimStr = input.nextLine().trim();
                
                  System.out.println("");
             // Sıfır girilirse döngüyü kırıp programı sonlandırıyorum.
                if (anaSecimStr.equals("0")) {
                    System.out.println("\nBanka sisteminden başarıyla çıkış yapıldı. İyi günler dileriz!");
                    break;
                }
             // 1. Durum: Konsoldan alınan bilgilerle yeni bir Müşteri nesnesi oluşturma.
                if (anaSecimStr.equals("1")) {
                    System.out.print("Ad Soyad: "); 
                    String tamAd = input.nextLine().trim();
                    
                 // Girilen metni son boşluktan bölerek ad ve soyad bilgisini birbirinden ayırıyorum.
                    int sonBosluk = tamAd.lastIndexOf(" ");
                    String ad = (sonBosluk != -1) ? tamAd.substring(0, sonBosluk) : tamAd;
                    String soyad = (sonBosluk != -1) ? tamAd.substring(sonBosluk + 1) : "";

                    System.out.print("Email Adresi: ");
                    String email = input.nextLine().trim();

                    System.out.print("Telefon Numarası: ");
                    String telefon = input.nextLine().trim();
                    
                 // Kullanıcı harf veya boşluk girerse döngüye sokup tekrar rakam girmesini zorunlu kılıyorum.
                    while (telefon.isEmpty() || !telefon.matches("\\d+")) { 
                        System.out.print("[HATA] Telefon sadece rakamlardan oluşmalıdır. Tekrar giriniz: ");
                        telefon = input.nextLine().trim();
                    }
                    
                 // Yeni müşteriyi oluşturup listeye ve servise kaydediyorum, ardından direkt işlem menüsüne yönlendiriyorum.
                    Musteri yeni = new Musteri(ad, soyad, email, telefon);
                    tumMusteriler.add(yeni);
                    service.musteriKaydet(yeni);
                    musteriIslemMenusu(yeni);
                }
                
             // 2. Durum: Sistemde kayıtlı olan müşterileri listeleyip işlem yapmak için birini seçtirme.
                 else if (anaSecimStr.equals("2")) {
                    if (tumMusteriler.isEmpty()) { System.out.println("Kayıtlı müşteri yok."); 
                    continue;
                    }
                    
                    for (int i = 0; i < tumMusteriler.size(); i++) {
                        System.out.println((i + 1) + " - " + tumMusteriler.get(i).getAd() + " " + tumMusteriler.get(i).getSoyad());
                    }
                    
                    System.out.print("Müşteri No: ");
                    int mIdx = sayiAl() - 1;
                    
                 // IndexOutOfBounds hatası almamak için girilen sayının listede olup olmadığını kontrol ediyorum.
                    if (mIdx >= 0 && mIdx < tumMusteriler.size()) {
                        musteriIslemMenusu(tumMusteriler.get(mIdx));
                    }
                    else {
                        System.out.println("HATA! Geçersiz müşteri seçimi yaptınız!");
                    }
                }
             // ANA MENÜ İÇİN HATA KONTROLÜ
                 else {
                     System.out.println("HATA! Lütfen menüdeki seçeneklerden (0, 1, 2) birini giriniz!");
                 }
            }
            
        }
    
    
    /**
     * Seçilen müşteriye özel bankacılık işlemlerinin yürütüldüğü alt menü metodudur.
     */

    private static void musteriIslemMenusu(Musteri m) {
        while (true) {
                System.out.println("\n> Müşteri: " + m.getAd() + " " + m.getSoyad());
                System.out.println("1 - Hesap Aç (Vadesiz/Yatırım)");
                System.out.println("2 - Para Yatır");
                System.out.println("3 - Para Transferi");
                System.out.println("4 - Kredi Kartı İşlemleri");
                System.out.println("5 - Hesap Kapat (Sil)");
                System.out.println("6 - İşlem Geçmişini Gör");
                System.out.println("0 - Ana Menü");
                System.out.print("İşlem: ");
                
                String secim = input.nextLine().trim();
                if (secim.equals("0"))
                	break;

                switch (secim) {
                    // Senaryo 1: Müşteri adına hesap açma
                    case "1":
                        System.out.print("Hesap Türü (Vadesiz/Yatırım): ");
                        m.hesapEkle(input.nextLine().trim());
                        break;
                     // Senaryo 2: Belirtilen hesaba para yatırma işlemi
                    case "2":
                        if (m.getHesaplar().isEmpty()) { 
                        	System.out.println("Hesap yok.");
                        break; 
                        } 
                        
                     // Müşterinin mevcut hesaplarını ekrana yazdırıyorum.
                        for(int i=0; i<m.getHesaplar().size(); i++) {
                            String tip = (m.getHesaplar().get(i) instanceof VadesizHesap) ? "Vadesiz" : "Yatırım";
                            System.out.println((i+1) + " - " + tip + " | " + m.getHesaplar().get(i).getIban());
                        }
                        
                        System.out.print("Hesap seçin: ");
                        int hIdx = sayiAl() - 1;
                        
                        // Sınır Kontrolü
                        if (hIdx < 0 || hIdx >= m.getHesaplar().size()) {
                            System.out.println("HATA! Geçersiz hesap seçimi!");
                            break;
                        }

                        System.out.print("Miktar: ");
                        double miktar = Double.parseDouble(input.nextLine().trim());
                        
                     // Seçilen hesabın türüne göre (Polymorphism/Çok Biçimlilik) ilgili ekleme işlemini yapıyorum.
                        BankaHesabi seciliH = m.getHesaplar().get(hIdx);
                        if (seciliH instanceof YatirimHesabi) {
                            ((YatirimHesabi)seciliH).paraEkle(miktar);
                        }
                        else {
                            seciliH.setBakiye(seciliH.getBakiye() + miktar);
                            seciliH.islemKaydet(miktar + " TL vadesiz hesaba yatırıldı.");
                            System.out.println("Başarılı! " + miktar + " TL Vadesiz hesabına eklendi. Yeni Bakiye: " + seciliH.getBakiye() + " TL");
                        }
                        break;
                        
                     // Senaryo 3: Hesaplar arasında para transferi gerçekleştirme
                    case "3":
                        if (m.getHesaplar().isEmpty() || tumMusteriler.size() < 2) {
                            System.out.println("Transfer için yeterli hesap veya başka müşteri yok.");
                            break;
                        }
                        
                     // Gönderen hesabı seçtiriyorum.
                        System.out.println("-> Gönderen Hesap Seçin ");
                        for(int i=0; i<m.getHesaplar().size(); i++) {
                            String tip = (m.getHesaplar().get(i) instanceof VadesizHesap) ? "Vadesiz" : "Yatırım";
                            System.out.println((i+1) + " - " + tip + " | IBAN: " + m.getHesaplar().get(i).getIban() + " (" + m.getHesaplar().get(i).getBakiye() + " TL)");
                        }
                        int gIdx = sayiAl() - 1;
                        
                        // Sınır Kontrolü
                        if (gIdx < 0 || gIdx >= m.getHesaplar().size()) {
                            System.out.println("HATA! Geçersiz hesap seçimi!");
                            break;
                        }
                        
                        BankaHesabi secilenHesap = m.getHesaplar().get(gIdx);
                        if (!(secilenHesap instanceof VadesizHesap)) {
                            System.out.println("HATA! Para transferi işlemi sadece Vadesiz hesaplar üzerinden yapılabilir!");
                            break;}
                            
                        VadesizHesap gonderenH = (VadesizHesap) m.getHesaplar().get(gIdx);
                    
                        // Gönderen hesabı seçtiriyorum.
                        System.out.println("-> Alıcı Müşteri Seçin ");
                        ArrayList<Musteri> digerleri = new ArrayList<>();
                        for(Musteri kul : tumMusteriler) {
                            if(!kul.getMusteriNumarasi().equals(m.getMusteriNumarasi())) {
                                digerleri.add(kul);
                            }
                        }
                        
                        for(int i=0; i<digerleri.size(); i++) {
                            System.out.println((i+1) + " - " + digerleri.get(i).getAd() + " " + digerleri.get(i).getSoyad());
                        }
                        int aMIdx = sayiAl() - 1;
                        
                        if (aMIdx < 0 || aMIdx >= digerleri.size()) {
                            System.out.println("HATA! Geçersiz müşteri seçimi!");
                            break;
                        }
                        Musteri aliciM = digerleri.get(aMIdx);
                        
                     // Alıcı hesabını seçtiriyorum.
                        System.out.println("--- Alıcı Hesap Seçin ---");
                        for(int i=0; i<aliciM.getHesaplar().size(); i++) {
                            System.out.println((i+1) + " - " + aliciM.getAd() + " " + aliciM.getSoyad() + " | IBAN: " + aliciM.getHesaplar().get(i).getIban());
                        }
                        int aHIdx = sayiAl() - 1;
                        
                        // Sınır Kontrolü
                        if (aHIdx < 0 || aHIdx >= aliciM.getHesaplar().size()) {
                            System.out.println("HATA! Geçersiz hesap seçimi!");
                            break;
                        }
                        BankaHesabi aliciH = aliciM.getHesaplar().get(aHIdx);

                        System.out.print("Transfer Tutarı: ");
                        double transferMiktari = miktarAl();
                        
                        gonderenH.paraTransferi(aliciH, transferMiktari); 
                        break;
                       

                     // Senaryo 4 ve 5: Müşteriye kredi kartı tanımlama ve Borç ödeme
                    case "4":
                        if (m.getKrediKartlari().isEmpty()) {// Eğer müşterinin kartı yoksa otomatik olarak 50000 TL limitli bir kart oluşturuyorum.
                        	m.krediKartiEkle(50000);
                        	KrediKarti yeniKart = m.getKrediKartlari().get(0);
                            
                            // Kartın başarıyla oluşturulduğuna dair bilgilendirici çıktıyı listeliyorum.
                            System.out.println("\n[SİSTEM] Yeni kredi kartınız başarıyla tanımlanmıştır.");
                            System.out.println("Kart No: " + yeniKart.getKartNumarasi());
                            System.out.println("Tanımlanan Limit: " + yeniKart.getLimit() + " TL");
                            
                        	}
                        System.out.println(" ");
                        KrediKarti k = m.getKrediKartlari().get(0);
                        System.out.println("Borç: " + k.getGuncelBorc());
                        System.out.println("Kullanılabilir Limit: " + k.getKullanilabilirLimit() + " TL");
                        System.out.println("1- Harcama Yap | 2- Borç Öde");
                        System.out.print("Seçiminiz: ");
                        String kSec = input.nextLine().trim();
                        
                        if(kSec.equals("1")) {
                            System.out.print("Harcama Miktarı: ");
                            k.harcamaYap(Double.parseDouble(input.nextLine().trim()));
                        }
                        else if (kSec.equals("2")) {
                            // Ödeme yapılacak vadesiz hesapları filtreleyip listeliyorum.
                            ArrayList<VadesizHesap> vadesizHesaplar = new ArrayList<>();
                            for(BankaHesabi bh : m.getHesaplar()) {
                                if(bh instanceof VadesizHesap) vadesizHesaplar.add((VadesizHesap)bh);
                            }

                            if(vadesizHesaplar.isEmpty()) {
                                System.out.println("HATA! Ödeme için vadesiz hesabınız bulunmamaktadır!");
                            }
                            else {
                                System.out.println("-- Ödeme Yapılacak Hesabı Seçin --");
                                for(int i = 0; i < vadesizHesaplar.size(); i++) {
                                    System.out.println((i + 1) + " - " + vadesizHesaplar.get(i).getIban() + " (Bakiye: " + vadesizHesaplar.get(i).getBakiye() + " TL)");
                                }
                                int vIdx = sayiAl() - 1;

                                if(vIdx >= 0 && vIdx < vadesizHesaplar.size()) {
                                    System.out.print("Ödenecek Miktar: ");
                                    vadesizHesaplar.get(vIdx).krediKartiBorcOdeme(k, miktarAl());
                                } else {
                                    System.out.println("HATA! Geçersiz hesap seçimi!");
                                }
                            }
                        }
                        else {
                            System.out.println("HATA! Geçersiz seçim yaptınız! Lütfen 1 veya 2 rakamını giriniz.");
                        }
                        break;

                    case "5":
                        if (m.getHesaplar().isEmpty()) { 
                        	System.out.println("Hesap yok."); 
                        	break;
                        	}
                        for(int i=0; i<m.getHesaplar().size(); i++) {
                        	System.out.println((i+1) + " - " + m.getHesaplar().get(i).getIban() + " (" + m.getHesaplar().get(i).getBakiye() + " TL)");
                        }
                        
                        int sIdx = sayiAl() - 1;
                        
                       
                        if (sIdx < 0 || sIdx >= m.getHesaplar().size()) {
                            System.out.println("HATA! Geçersiz hesap seçimi!");
                            break;
                        }
                        m.hesapSil(m.getHesaplar().get(sIdx));
                        break;
                        
                    //Müşterinin seçtiği hesabın geçmiş işlemlerini yazdırma
                    case "6":
                        if (m.getHesaplar().isEmpty()) { 
                        	System.out.println("Hesap yok."); 
                        	break;
                        	}
                        for(int i=0; i<m.getHesaplar().size(); i++) {
                        	System.out.println((i+1) + " - " + m.getHesaplar().get(i).getIban());
                        }
                        System.out.print("Seçim: ");
                        int iIdx = sayiAl() - 1;
                        
                        // Sınır Kontrolü
                        if (iIdx < 0 || iIdx >= m.getHesaplar().size()) {
                            System.out.println("HATA! Geçersiz hesap seçimi!");
                            break;
                        }
                        m.getHesaplar().get(iIdx).islemGecmisiniYazdir();
                        break;
                        
                     // ALT MENÜ İÇİN HATA KONTROLÜ
                    default:
                        System.out.println("HATA! Menüde olmayan bir sayı girdiniz. Lütfen 0 ile 6 arasında bir seçim yapınız!");
                        break;
            }
        }
    }
        private static int sayiAl() {
            while (true) {
                String giris = input.nextLine().trim();
                if (giris.isEmpty()) {
                    System.out.print("HATA! Boş bırakılamaz, tekrar giriniz: ");
                    continue;
                }
                
                boolean sadeceRakam = true;
                for (int i = 0; i < giris.length(); i++) {
                    if (!Character.isDigit(giris.charAt(i))) {
                        sadeceRakam = false;
                        break;
                    }
                }
                
                if (sadeceRakam) {
                    return Integer.parseInt(giris);
                } else {
                    System.out.print("HATA! Harf kullanamazsınız! Lütfen geçerli bir sayı giriniz: ");
                }
            }
        }

        // Kullanıcıdan ondalıklı sayı (double) beklenen tutar/miktar gibi yerlerde harf girilmesini engellemek için yazdığım metottur.
        
        private static double miktarAl() {
            while (true) {
                String giris = input.nextLine().trim();
                if (giris.isEmpty()) {
                    System.out.print("HATA! Boş bırakılamaz, tekrar giriniz: ");
                    continue;
                }
                
                boolean gecerliMi = true;
                int noktaSayisi = 0;
                
                for (int i = 0; i < giris.length(); i++) {
                    char c = giris.charAt(i);
                    if (c == '.') {
                        noktaSayisi++;
                        if (noktaSayisi > 1) { // Bir sayıda birden fazla nokta (virgül) olamaz
                            gecerliMi = false;
                            break;
                        }
                    } else if (!Character.isDigit(c)) {
                        gecerliMi = false; // Nokta ve rakam dışındaki her şey (harf vb.) geçersizdir
                        break;
                    }
                }
                
                if (gecerliMi) {
                    return Double.parseDouble(giris);
                } else {
                    System.out.print("HATA! Lütfen geçerli bir tutar giriniz (Örn: 250 veya 150.5): ");
                }
            }
        }
    }