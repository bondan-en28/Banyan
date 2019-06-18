-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 18, 2019 at 03:35 AM
-- Server version: 10.3.15-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `banyan`
--

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

--
-- Dumping data for table `banyan_tanaman`
--

INSERT INTO `banyan_tanaman` (`id`, `nama`, `jenis`, `ketinggian`, `struktur_tanah`, `suhu`, `ph`) VALUES
(1, 'Sri Rejeki', 'Tanaman Hias', 350, 'Lembap', 20, 7),
(2, 'Melati', 'Bunga', 500, 'Sedikit Kering', 22, 6),
(3, 'Lavender ', 'Bunga', 1000, 'Lembap', 20, 7),
(4, 'Bougenville', 'Bunga', 900, 'Sedikit Kering', 25, 7),
(5, 'Daisy', 'Bunga', 500, 'Lembap', 24, 7),
(6, 'Cabai', 'Sayuran', 400, 'Sedikit Kering', 26, 7),
(7, 'Lilly', 'Bunga', 600, 'Lembap', 23, 7),
(8, 'Buttercup', 'Bunga', 500, 'Lembap', 24, 7);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banyan_tanaman`
--
ALTER TABLE `banyan_tanaman`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banyan_tanaman`
--
ALTER TABLE `banyan_tanaman`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
