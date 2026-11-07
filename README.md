# EventUbaya - Direktori Event & Rekrutmen Mahasiswa Universitas Surabaya

Proyek Akhir Mata Kuliah **Native Mobile Programming (1604C052) - Gasal 2026-2027**  
Program Studi Teknik Informatika, Fakultas Teknik, Universitas Surabaya (Ubaya).

---

## 📌 Ringkasan Proyek & Nilai Tambah
**EventUbaya** adalah aplikasi Android Native berbasis Kotlin dan antarmuka XML klasik untuk memudahkan mahasiswa Ubaya menemukan, mengikuti, dan mendaftar berbagai kegiatan kampus (Seminar, Workshop coding, Kompetisi/Hackathon, dan Volunteer sosial) lengkap dengan perolehan poin SKK (Sertifikat Kegiatan Kemahasiswaan).

Aplikasi ini dirancang khusus untuk memenuhi seluruh rubrik penilaian mata kuliah sekaligus siap diikutsertakan dalam **Lomba Karya Internal Universitas Surabaya** karena memiliki:
- **Keunikan & Inovasi (30%):** Memusatkan informasi kepanitiaan/event ormawa Ubaya yang selama ini tersebar di grup chat.
- **Nilai Jual / Marketability (30%):** Bermanfaat langsung bagi ribuan mahasiswa, BEM, KSM, dan UKM di lingkungan Ubaya.
- **Keberlangsungan / Sustainability (20%):** Dapat dikembangkan menjadi sistem verifikasi kehadiran dan pencatatan presensi digital ormawa.

---

## 📋 Pemenuhan Spesifikasi Teknis Tugas

| No | Spesifikasi Teknis Wajib | Implementasi pada Proyek EventUbaya | Lokasi File |
|---|---|---|---|
| 1 | **Multi-Activity & Fragment** | • 3 Activity: `MainActivity`, `DetailEventActivity`, `RegisterEventActivity`<br>• 3 Fragment: `EventFragment`, `RegisteredFragment`, `ProfileFragment` via `BottomNavigationView` | `MainActivity.kt`, `EventFragment.kt`, `RegisteredFragment.kt`, `ProfileFragment.kt` |
| 2 | **RecyclerView & CardView** | • Daftar Event (dengan CardView, badge kategori, poster via Picasso, likes, kuota)<br>• Daftar E-Tiket Pendaftaran Mahasiswa | `EventAdapter.kt`, `card_event.xml`, `RegistrationAdapter.kt`, `card_registration.xml` |
| 3 | **Komponen UI Dasar** | Form Pendaftaran Lengkap:<br>• `Spinner`: Kategori/Fakultas Peserta<br>• `RadioGroup` & `RadioButton`: Metode Hadir (Offline / Online)<br>• `CheckBox`: Syarat mahasiswa aktif & Klaim SKK<br>• `EditText`: Nama, NRP, Email, WhatsApp<br>• `Button`: Submit pendaftaran | `activity_register_event.xml`, `RegisterEventActivity.kt` |
| 4 | **Web Service & JSON (Volley)** | Koneksi HTTP REST API ke backend PHP/MySQL menggunakan pustaka `Volley` (`StringRequest`), parsing JSON via `JSONObject` & `Gson` | `get_events.php`, `register_event.php`, `set_likes.php`, `get_registrations.php` |
| 5 | **Implicit Intent** | Fitur membagikan poster & deskripsi event ke aplikasi WhatsApp / Media Sosial (`Intent.ACTION_SEND`) | `DetailEventActivity.kt` (pada tombol Bagikan) |
| 6 | **Material Design** | Warna tema merah Ubaya (`#C62828`), Material 3 NoActionBar, MaterialCardView, TextInputLayout OutlinedBox, BottomNavigationView | `colors.xml`, `themes.xml`, `activity_main.xml` |

---

## 🛠️ Panduan Menjalankan Web Service & Database

1. **Nyalakan XAMPP**:
   - Buka XAMPP Control Panel, klik **Start** pada modul **Apache** dan **MySQL**.
2. **Database MySQL**:
   - Buka `http://localhost/phpmyadmin/`
   - Buat database baru bernama `event_ubaya`
   - Impor file SQL yang ada di folder `backend/event_ubaya.sql`
3. **File Web Service (PHP)**:
   - Pastikan folder `backend/` telah disalin ke folder `C:\xampp\htdocs\event_ubaya\` (sudah terpasang otomatis).
   - Uji akses di browser: `http://localhost/event_ubaya/get_events.php`
4. **Pengaturan IP di Android (`Global.kt`)**:
   - Jika dijalankan di **Android Studio Emulator**: gunakan `http://10.0.2.2/event_ubaya/` (sudah default).
   - Jika dijalankan di **HP Fisik (Real Device)**: ganti nilai `SERVER_URL` di [Global.kt](file:///c:/Users/Stevent%20Crown/Desktop/NMP%20PROJECT/app/src/main/java/com/ubayadev/eventubaya/Global.kt) dengan IP Wi-Fi laptop Anda (misal: `http://192.168.100.9/event_ubaya/`).

---

## 📦 Lokasi File Deliverables

1. **Source Code Lengkap**: Seluruh source code proyek Android Studio di folder ini.
2. **File Backend & Database SQL**: Terletak di folder `backend/` (`event_ubaya.sql` dan 4 file PHP).
3. **File APK Siap Pasang**:
   - `app/build/outputs/apk/debug/app-debug.apk`
