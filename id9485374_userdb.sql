-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 14, 2019 at 09:41 AM
-- Server version: 10.3.14-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id9485374_userdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `banyan_komentar`
--

CREATE TABLE `banyan_komentar` (
  `id` int(11) NOT NULL,
  `post_id` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `komentar` text COLLATE utf8_unicode_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `banyan_komentar`
--

INSERT INTO `banyan_komentar` (`id`, `post_id`, `user_id`, `komentar`, `date`) VALUES
(3, '11', '23', 'Joss!!!', '2019-06-11 21:00:05'),
(6, '11', '23', 'Mantap, lanjutkan!', '2019-06-12 11:29:29'),
(7, '11', '42', 'Wahh lucu sekalii!!', '2019-06-12 11:47:00'),
(8, '11', '42', 'Kerenn!!', '2019-06-12 13:12:22'),
(9, '16', '23', 'Wah keren!!', '2019-06-12 22:05:47'),
(10, '16', '42', 'Terimakasiihh :D', '2019-06-12 22:06:29'),
(11, '16', '43', 'Waaaagelazehh', '2019-06-12 22:57:29'),
(12, '7', '23', 'setting', '2019-06-13 00:35:39'),
(13, '26', '23', 'gufigrbrb', '2019-06-13 00:36:45');

-- --------------------------------------------------------

--
-- Table structure for table `banyan_pengguna`
--

CREATE TABLE `banyan_pengguna` (
  `id` int(11) NOT NULL,
  `nama` varchar(30) CHARACTER SET latin1 NOT NULL,
  `email` varchar(50) CHARACTER SET latin1 NOT NULL,
  `password` text CHARACTER SET latin1 NOT NULL,
  `photo` text CHARACTER SET latin1 DEFAULT NULL,
  `ttl` date DEFAULT NULL,
  `alamat` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `notelp` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `banyan_pengguna`
--

INSERT INTO `banyan_pengguna` (`id`, `nama`, `email`, `password`, `photo`, `ttl`, `alamat`, `notelp`) VALUES
(21, 'Bondan Eka Nugraha', 'bondan@banyan.com', '$2y$10$viGfzwWXnv/6lAvRoNYkO.kKli0heyTmkCNQbdEdy50s7fksgCPHu', 'http://bonbon28.000webhostapp.com/banyan/foto_profil/21.jpeg', '1999-10-28', 'Semarang', '082324383996'),
(23, 'Bondan EN', 'b', '$2y$10$1Vui5JbIoEGVhc0KrcNDheClsR/0I6VC98KTWtShS6lfexprHkznG', 'http://bonbon28.000webhostapp.com/banyan/foto_profil/23.jpeg', '1999-10-28', 'Semarang', '082324383996'),
(25, '', '', '$2y$10$YvGBHovm..zAQWH.12sNaei09H/CaN3cJJFJPjOAIPGne0nnh./2.', NULL, NULL, NULL, NULL),
(32, 'c', 'c@banyan.com', '$2y$10$XKVKzOOeMnjU7OMn3XP67.hOzKO.lVXZMW.JjfrSwFJu37fAE0Jka', 'http://bonbon28.000webhostapp.com/banyan/foto_profil/32.jpeg', '1970-01-01', 'null', 'null'),
(42, 'Gandha', 'g', '$2y$10$73Jjc./fsAb6VzHJXiXtZePfIXhlaOX09xEo9KMWt.IGbZIf4/0Ze', 'http://bonbon28.000webhostapp.com/banyan/foto_profil/42.jpeg', '2019-05-24', 'sfbsfbaf', '45945642'),
(43, 'Ocik', 'o', '$2y$10$mZLipxpVARFpb2gCcSM2B.flI0C.HbJfcb7LUseipaFtarDhCil3e', 'http://bonbon28.000webhostapp.com/banyan/foto_profil/43.jpeg', '1999-05-30', 'Jepara', '+62123456'),
(44, 'A', 'A', '$2y$10$v2NwUPQW.ThGTulhkL8m8uu7SmAQtHEtAystBdjUlFVQ9CUy0oxE.', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `banyan_post`
--

CREATE TABLE `banyan_post` (
  `id` int(11) NOT NULL,
  `user_id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `judul` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `deskripsi` text COLLATE utf8_unicode_ci NOT NULL,
  `gambar` text COLLATE utf8_unicode_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `banyan_post`
--

INSERT INTO `banyan_post` (`id`, `user_id`, `judul`, `deskripsi`, `gambar`, `date`) VALUES
(1, '42', 'Ini Judul 1', 'Deskripsi 1', 'http://bonbon28.000webhostapp.com/banyan/foto_post/42.Ini Judul 1.jpeg', '2019-05-27 16:43:12'),
(3, '42', 'Posting 3', 'Ini yang ketiga', 'http://bonbon28.000webhostapp.com/banyan/foto_post/42.Posting 3.jpeg', '2019-05-27 17:03:02'),
(4, '23', 'Ini Arumku', 'Yakkdessss', 'http://bonbon28.000webhostapp.com/banyan/foto_post/.2019-06-13 00:21:40.jpeg', '2019-05-28 09:57:28'),
(5, '23', 'Choco', 'Malezzz', 'http://bonbon28.000webhostapp.com/banyan/foto_post/.2019-06-13 00:25:44.jpeg', '2019-05-28 14:26:11'),
(6, '23', 'Tes Tanggal edit', '21:27 28 May 2019 edited', 'http://bonbon28.000webhostapp.com/banyan/foto_post/.2019-06-13 00:20:52.jpeg', '2019-05-28 14:27:54'),
(7, '23', 'Setting timezone', '21:32', 'http://bonbon28.000webhostapp.com/banyan/foto_post/23.Setting timezone.jpeg', '2019-05-28 14:32:59'),
(8, '23', 'Cek Timestamp', '21:59', 'http://bonbon28.000webhostapp.com/banyan/foto_post/23.Cek Timestamp.jpeg', '2019-05-28 22:59:45'),
(9, '23', 'Cek timezone Jakarta', '22:02', 'http://bonbon28.000webhostapp.com/banyan/foto_post/23.Cek timezone Jakarta.jpeg', '2019-05-28 22:02:15'),
(10, '23', 'Peduli Lingkungan', 'Udut di t3ngAh tamAn adlh sbwah kenIqmaTAn yaNk haqieqie\na\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk\nl\nm\nn\no\np', 'http://bonbon28.000webhostapp.com/banyan/foto_post/.2019-06-13 00:29:43.jpeg', '2019-05-28 23:49:32'),
(11, '43', 'Alhamdulillah, sehat', 'Setelah sekian lamaku berbudidaya rumput, akhirnya sekarang bisa dipakai untuk lapangan sepakbola. terimakasih klinik tonkpeng.', 'http://bonbon28.000webhostapp.com/banyan/foto_post/43.Alhamdulillah, sehat.jpeg', '2019-05-29 00:29:02'),
(16, '42', 'Berkebun Dengan Teknologi???', 'Bisa dongg!! Kini berkebun dapat di lakukan darimana saja, segera unduh aplikasi Tosca hanya di Google Play Store!', 'http://bonbon28.000webhostapp.com/banyan/foto_post/42.2019-06-12 22:03:34.jpeg', '2019-06-12 22:03:34');

-- --------------------------------------------------------

--
-- Table structure for table `banyan_tanaman`
--

CREATE TABLE `banyan_tanaman` (
  `id` int(11) NOT NULL,
  `nama` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `jenis` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `ketinggian` int(11) NOT NULL,
  `struktur_tanah` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `suhu` float NOT NULL,
  `ph` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `note` text COLLATE utf8_unicode_ci NOT NULL,
  `color` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `date` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`id`, `title`, `note`, `color`, `date`) VALUES
(1, 'Catatan pertamaku!', 'Ini diaa!!!!', '-2184710', '2019-05-26 12:27:52'),
(2, 'Biru', 'putih', '-2234644', '2019-05-26 13:00:47'),
(3, 'Catatan Gua', 'Hehehhijau', '-13963914', '2019-05-26 14:13:18'),
(4, 'Aku pink!', 'pinkk', '-1222758', '2019-05-26 15:39:43');

-- --------------------------------------------------------

--
-- Table structure for table `tabel_karyawan`
--

CREATE TABLE `tabel_karyawan` (
  `id` int(11) NOT NULL,
  `nama` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `jeniskelamin` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `usia` int(11) NOT NULL,
  `alamat` text COLLATE utf8_unicode_ci NOT NULL,
  `divisi` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `gaji` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tabel_karyawan`
--

INSERT INTO `tabel_karyawan` (`id`, `nama`, `jeniskelamin`, `usia`, `alamat`, `divisi`, `gaji`) VALUES
(1, 'Bondan Eka Nugraha', 'Laki-laki', 19, 'Gunungpati, Semarang', 'Umum', 20000000),
(2, 'Gandha Arum Syafira', 'Perempuan', 20, 'Yogyakarta', 'Pemasaran', 15000000),
(3, 'Valentino Rossi', 'Laki-laki', 45, 'Italia', 'Produksi', 10000000),
(4, 'Marc Marquez', 'Laki-laki', 25, 'Spanyol', 'Pembelanjaan', 13000000),
(5, 'Andrea Dovizioso', 'Laki-laki', 28, 'Italia', 'Umum', 11000000),
(6, 'Elena', 'Perempuan', 22, 'Jerman', 'Personalia', 8000000),
(7, 'Selena Gomez', 'Perempuan', 24, 'Amerika', 'Pembelanjaan', 9000000),
(8, 'Justin Bieber', 'Laki-laki', 24, 'Amerika', 'Produksi', 12000000),
(9, 'bb', 'Laki-laki', 13, 'nsnsj', 'Produksi', 346949868),
(10, 'Doni Tata', 'Laki-laki', 28, 'Indonesia', 'Umum', 7000000),
(11, 'Akbar Rais', 'Laki-laki', 32, 'Jakarta', 'Personalia', 12000000);

-- --------------------------------------------------------

--
-- Table structure for table `tabel_pengguna`
--

CREATE TABLE `tabel_pengguna` (
  `id` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jam` time NOT NULL,
  `nama` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `alamat` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `pekerjaan` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tabel_pengguna`
--

INSERT INTO `tabel_pengguna` (`id`, `tanggal`, `jam`, `nama`, `alamat`, `pekerjaan`) VALUES
(1, '2019-05-21', '12:23:01', 'Bondan', 'Semarang', 'Mahasiswa'),
(2, '2019-05-21', '12:24:07', 'Eka', 'Batang', 'Siswa'),
(3, '2019-05-21', '12:25:07', 'Choco', 'Gunungpati', 'Busy Cat'),
(4, '2019-05-21', '12:28:40', 'Spiderman', 'Patemon', 'Superhero'),
(5, '2019-05-22', '13:58:24', 'Farhan', 'Jogja', 'Tukang bobok'),
(6, '2019-05-28', '02:48:52', 'svsb', 'hehe', 'bhdhs');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banyan_komentar`
--
ALTER TABLE `banyan_komentar`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `banyan_pengguna`
--
ALTER TABLE `banyan_pengguna`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `banyan_post`
--
ALTER TABLE `banyan_post`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `banyan_tanaman`
--
ALTER TABLE `banyan_tanaman`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `tabel_karyawan`
--
ALTER TABLE `tabel_karyawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tabel_pengguna`
--
ALTER TABLE `tabel_pengguna`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banyan_komentar`
--
ALTER TABLE `banyan_komentar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `banyan_pengguna`
--
ALTER TABLE `banyan_pengguna`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `banyan_post`
--
ALTER TABLE `banyan_post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `banyan_tanaman`
--
ALTER TABLE `banyan_tanaman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tabel_karyawan`
--
ALTER TABLE `tabel_karyawan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tabel_pengguna`
--
ALTER TABLE `tabel_pengguna`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
