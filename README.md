# BARBER — yeni giriş ve kayıt tasarımı

NetBeans için Java 17 / Swing Maven projesi. İç ekran 420 × 860 pikseldir. Bilgisayarda çalışan telefon boyutunda prototiptir.

## Açma
ZIP dosyasını çıkarın. NetBeans > File > Open Project > pom.xml içeren BARBER klasörü > F6. Ana sınıf sorulursa com.barber.BarberApp seçin. JDK 17 veya üstü gerekir. İlk Maven kullanımında internet gerekebilir.

Alternatif: java -jar dist/BARBER.jar. Windows: run.bat. macOS/Linux: bash run.sh.

## Demo hesabı
E-posta: demo@barber.app
Şifre: Barber123!

## Tasarım ve işlevler
Koyu mor üst alan, orijinal B amblemi, BARBER adı ve “Berberin cebinde. Zamanını yönet.” sloganı. Mint yardımcı renk. Yuvarlatılmış beyaz form kartı, odak çerçeveleri, yan yana Giriş yap ve Kayıt ol butonları. Kayıt formu aynı pencere içinde açılır; girişe dönüş bağlantısı vardır.

Kayıtta ad soyad, e-posta, en az 8 karakter şifre ve şifre tekrar kontrol edilir. Girişte boş/hatalı alan ve hesap kontrolleri yapılır. Şifre göster/gizle, Tab dolaşımı, Enter ile gönderim ve metinle geri bildirim vardır.

## Demo kapsamı
Gerçek backend yoktur. Yeni hesaplar yalnız bellekte tutulur; uygulama kapanınca silinir. Aynı oturumda kayıt ve giriş çalışır. Başarılı giriş form üzerinde bildirilir; randevu ekranı yoktur. Şifremi unuttum, servisin bağlı olmadığına ilişkin bilgi verir.

“E-postamı hatırla” Java Preferences ile yalnız e-postayı saklar. Seçim kapalı başarılı girişte önceki e-posta silinir. Parola saklanmaz. Demo hesapların SHA-256 özetleri bellekte tutulur; gerçek kullanım için sunucu tarafında tuzlanmış güçlü parola türetme, oturum yönetimi ve sıfırlama servisi gereklidir.

## Kaynaklar
src/main/java/com/barber/BarberApp.java: tasarım ve ekranlar.
src/main/java/com/barber/AuthService.java: demo hesap servisi.
src/main/resources/images/barber-logo.png: orijinal logo dosyası değişmeden saklanır. Ekranda yalnız B simgesinin bölgesi gösterilir, yeniden çizilmez ve renklendirilmez.
docs/BARBER-login.png ve BARBER-register.png: gerçek Swing önizlemeleri.
dist/BARBER.jar: çalıştırılabilir uygulama.

Ana renk BarberApp.PURPLE; yardımcı renk BarberApp.MINT. Harici UI kütüphanesi gerekmez. Tasarım Java kodundan düzenlenir; GUI Builder .form dosyası kullanılmaz.
