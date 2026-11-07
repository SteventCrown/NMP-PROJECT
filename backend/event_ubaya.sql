CREATE DATABASE IF NOT EXISTS `event_ubaya`;
USE `event_ubaya`;

DROP TABLE IF EXISTS `registrations`;
DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(150) NOT NULL,
  `category` VARCHAR(50) NOT NULL,
  `organizer` VARCHAR(100) NOT NULL,
  `date_time` VARCHAR(100) NOT NULL,
  `location` VARCHAR(150) NOT NULL,
  `quota` INT NOT NULL,
  `registered_count` INT DEFAULT 0,
  `description` TEXT NOT NULL,
  `poster_url` VARCHAR(255) NOT NULL,
  `likes` INT DEFAULT 0,
  `format` VARCHAR(20) NOT NULL,
  `skk_points` INT DEFAULT 10
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `registrations` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `event_id` INT NOT NULL,
  `student_name` VARCHAR(100) NOT NULL,
  `student_nrp` VARCHAR(20) NOT NULL,
  `student_email` VARCHAR(100) NOT NULL,
  `student_phone` VARCHAR(20) NOT NULL,
  `participant_type` VARCHAR(50) NOT NULL,
  `attendance_method` VARCHAR(30) NOT NULL,
  `claim_skk` INT DEFAULT 1,
  `registration_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`event_id`) REFERENCES `events`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `events` (`title`, `category`, `organizer`, `date_time`, `location`, `quota`, `registered_count`, `description`, `poster_url`, `likes`, `format`, `skk_points`) VALUES
('Ubaya Tech Fest: AI & Mobile Trends 2027', 'Seminar', 'KSM Teknik Informatika Ubaya', '18 Desember 2026, 09.00 WIB', 'Gedung PF Lt. 6 Kampus Tenggilis Ubaya', 150, 42, 'Seminar teknologi tahunan yang mengupas tuntas perkembangan Artificial Intelligence, Mobile Development, dan industri digital terkini bersama praktisi tech terkemuka di Indonesia.', 'https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=600&auto=format&fit=crop', 28, 'Hybrid', 15),
('Hands-on Workshop: Android Native with Kotlin', 'Workshop', 'Google Developer Student Club Ubaya', '22 Desember 2026, 13.00 WIB', 'Lab Komputer TC-4D Ubaya', 40, 31, 'Sesi coding intensif membangun aplikasi Android Native menggunakan bahasa pemrograman Kotlin, XML layout modern, dan integrasi REST API secara langsung.', 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600&auto=format&fit=crop', 45, 'Offline', 20),
('Ubaya National Hackathon 2027', 'Kompetisi', 'BEM Fakultas Teknik Ubaya', '10 Januari 2027, 08.00 WIB', 'Perpustakaan Lt. 5 Kampus Tenggilis', 100, 18, 'Ajang kompetisi membuat solusi perangkat lunak inovatif dalam waktu 24 jam untuk memecahkan permasalahan nyata di masyarakat perkotaan. Total hadiah puluhan juta rupiah!', 'https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=600&auto=format&fit=crop', 60, 'Offline', 25),
('Volunteer Pengabdian Masyarakat: Desa Cerdas', 'Volunteer', 'Direktorat Kemahasiswaan Ubaya', '16 Januari 2027, 07.00 WIB', 'Balai Desa Trawas, Mojokerto', 30, 12, 'Program aksi nyata mahasiswa Ubaya dalam membantu digitalisasi UMKM dan pelatihan literasi teknologi bagi warga desa binaan.', 'https://images.unsplash.com/photo-1559027615-cd4628902d4a?w=600&auto=format&fit=crop', 19, 'Offline', 30),
('International Webinar: Startup Pitching & Funding', 'Seminar', 'Fakultas Bisnis dan Ekonomika Ubaya', '20 Januari 2027, 10.00 WIB', 'Live Zoom Meeting & YouTube Streaming', 300, 85, 'Pelajari strategi memikat investor venture capital serta cara menyusun pitch deck kelas dunia bersama founder startup unicorn Asia Tenggara.', 'https://images.unsplash.com/photo-1475721027785-f74eccf877e2?w=600&auto=format&fit=crop', 33, 'Online', 10);
