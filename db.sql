-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 06, 2022 at 03:10 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java-endterm`
--

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `content` text NOT NULL,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `content`, `sender`, `receiver`, `createdAt`) VALUES
(1, 'hello', 4, 5, '2022-07-06 10:50:57'),
(2, 'hello', 5, 4, '2022-07-06 10:50:57'),
(3, 'hi', 1, 4, '2022-07-06 12:25:55'),
(4, 'hi', 6, 1, '2022-07-06 12:35:40'),
(5, 'hello', 1, 6, '2022-07-06 12:35:47'),
(6, 'hi', 1, 6, '2022-07-06 12:38:04'),
(7, 'hello', 6, 1, '2022-07-06 12:38:09'),
(8, '3', 1, 6, '2022-07-06 12:38:16'),
(9, 'uar alo', 6, 1, '2022-07-06 12:38:23'),
(10, 'hehe', 1, 6, '2022-07-06 12:38:28'),
(11, 'ngon uif', 6, 1, '2022-07-06 12:38:40'),
(12, 'hi', 1, 6, '2022-07-06 12:42:08'),
(13, 'hi', 1, 6, '2022-07-06 12:46:56'),
(14, 'hế lô', 6, 1, '2022-07-06 12:47:05'),
(15, ' ngon ùi', 1, 6, '2022-07-06 12:47:13'),
(16, 'yeahh', 6, 1, '2022-07-06 12:47:19');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(122) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(300) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `createdAt`) VALUES
(1, 'ncuy0110', 'e6893d6323d3e1e238ad3a16c383241721b3ca5d4e227dccded05f98a2af7a5a', '2022-07-03 18:56:06'),
(4, 'ncuy', 'e6893d6323d3e1e238ad3a16c383241721b3ca5d4e227dccded05f98a2af7a5a', '2022-07-03 19:22:14'),
(5, 'abcd123', '88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589', '2022-07-06 08:28:52'),
(6, 'test', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', '2022-07-06 12:35:16');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `senderId` (`sender`),
  ADD KEY `receiverId` (`receiver`),
  ADD KEY `receiver` (`receiver`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(122) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
