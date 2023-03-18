/*
Created: 24.11.2022
Modified: 06.12.2022
Project: Spoldzielnia mieszkaniowa
Model: Spoldzielnia mieszkaniowa
Company: IT, WEiTI, Politechnka Warszawska
Author: Jakub Ossowski, Miłosz Kutyła
Version: 1.0
Database: Oracle 19c
*/


-- Create tables section -------------------------------------------------

-- Table Spoldzielnie

CREATE TABLE Spoldzielnie(
  Nr_spoldzielni Integer NOT NULL,
  Nazwa Varchar2(100 ) NOT NULL,
  Data_zalozenia Date NOT NULL,
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Spoldzielnie

CREATE INDEX IX_Spoldzielnia_ma_adres ON Spoldzielnie (Nr_adresu)
/

-- Add keys for table Spoldzielnie

ALTER TABLE Spoldzielnie ADD CONSTRAINT PK_Spoldzielnia PRIMARY KEY (Nr_spoldzielni)
/

-- Table Pracownicy

CREATE TABLE Pracownicy(
  Nr_pracownika Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Data_urodzenia Date NOT NULL,
  PESEL Char(11 ),
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('K','M')),
  Data_zatrudnienia Date NOT NULL,
  Nr_konta Char(26 ),
  Email Varchar2(30 ),
  Nr_telefonu Varchar2(15 ),
  Nr_spoldzielni Integer NOT NULL,
  Nr_adresu Integer NOT NULL,
  Nr_stanowiska Integer NOT NULL
)
/

-- Create indexes for table Pracownicy

CREATE INDEX IX_Zatrudnia ON Pracownicy (Nr_spoldzielni)
/

CREATE INDEX IX_Pracownik_ma_adres ON Pracownicy (Nr_adresu)
/

CREATE INDEX IX_Pracownik_ma_stanowisko ON Pracownicy (Nr_stanowiska)
/

-- Add keys for table Pracownicy

ALTER TABLE Pracownicy ADD CONSTRAINT PK_Pracownik PRIMARY KEY (Nr_pracownika)
/

-- Table Budynki

CREATE TABLE Budynki(
  Nr_budynku Integer NOT NULL,
  Powierzchnia Float NOT NULL,
  Liczba_kondygnacji Integer,
  Liczba_lokali Integer NOT NULL,
  Liczba_skladzikow Integer,
  Nr_spoldzielni Integer NOT NULL,
  Nr_adresu Integer NOT NULL,
  Nr_rodzaju Integer NOT NULL,
  Nr_wykonawcy Integer
)
/

-- Create indexes for table Budynki

CREATE INDEX IX_Zarzadza_budynkiem ON Budynki (Nr_spoldzielni)
/

CREATE INDEX IX_Budynek_ma_adres ON Budynki (Nr_adresu)
/

CREATE INDEX IX_Budynek_ma_rodzaj ON Budynki (Nr_rodzaju)
/

CREATE INDEX IX_Budynek_ma_wykonawce ON Budynki (Nr_wykonawcy)
/

-- Add keys for table Budynki

ALTER TABLE Budynki ADD CONSTRAINT PK_Budynku PRIMARY KEY (Nr_budynku)
/

-- Table Lokale

CREATE TABLE Lokale(
  Nr_lokalu Integer NOT NULL,
  Numer Integer,
  Cena_najmu Number(10,2),
  Powierzchnia Float NOT NULL,
  Liczba_pokojow Integer NOT NULL,
  Pietro Integer NOT NULL,
  Nr_budynku Integer NOT NULL,
  Nr_spoldzielni Integer NOT NULL
)
/

-- Create indexes for table Lokale

CREATE INDEX IX_Budynek_posiada_lokal ON Lokale (Nr_budynku)
/

CREATE INDEX IX_Zarzadza_lokalem ON Lokale (Nr_spoldzielni)
/

-- Add keys for table Lokale

ALTER TABLE Lokale ADD CONSTRAINT PK_Lokalu PRIMARY KEY (Nr_lokalu)
/

-- Table Zgloszenia

CREATE TABLE Zgloszenia(
  Nr_zgloszenia Integer NOT NULL,
  Data_zgloszenia Date NOT NULL,
  Data_zamkniecia Date,
  Opis Varchar2(500 ),
  Koszt Number(10,2),
  Nr_spoldzielni Integer NOT NULL,
  Nr_typu Integer NOT NULL
)
/

-- Create indexes for table Zgloszenia

CREATE INDEX IX_Obsluguje ON Zgloszenia (Nr_spoldzielni)
/

CREATE INDEX IX_Zgloszenie_ma_typ ON Zgloszenia (Nr_typu)
/

-- Add keys for table Zgloszenia

ALTER TABLE Zgloszenia ADD CONSTRAINT PK_Zgloszenia PRIMARY KEY (Nr_zgloszenia)
/

-- Table Lokatorzy

CREATE TABLE Lokatorzy(
  Nr_lokatora Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Data_urodzenia Date NOT NULL,
  PESEL Char(11 ),
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('K','M')),
  Email Varchar2(30 ),
  Nr_telefonu Varchar2(15 )
)
/

-- Add keys for table Lokatorzy

ALTER TABLE Lokatorzy ADD CONSTRAINT PK_Lokatora PRIMARY KEY (Nr_lokatora)
/

-- Table Wlasciciele

CREATE TABLE Wlasciciele(
  Nr_wlasciciela Integer NOT NULL,
  Imie Varchar2(20 ) NOT NULL,
  Nazwisko Varchar2(30 ) NOT NULL,
  Data_urodzenia Date NOT NULL,
  PESEL Char(11 ),
  Plec Char(1 ) NOT NULL
        CHECK (Plec IN ('K','M')),
  Email Varchar2(30 ),
  Nr_telefonu Varchar2(15 ),
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Wlasciciele

CREATE INDEX IX_Wlasciciel_ma_adres ON Wlasciciele (Nr_adresu)
/

-- Add keys for table Wlasciciele

ALTER TABLE Wlasciciele ADD CONSTRAINT PK_Wlasciciel PRIMARY KEY (Nr_wlasciciela)
/

-- Table Oplaty

CREATE TABLE Oplaty(
  Nr_oplaty Integer NOT NULL,
  Data Date NOT NULL,
  Wysokosc Number(10,2) NOT NULL,
  Nr_konta Char(26 ),
  Status Char(11 ) NOT NULL
        CHECK (Status IN ('Oplacono','Nieoplacono')),
  Nr_lokalu Integer NOT NULL
)
/

-- Create indexes for table Oplaty

CREATE INDEX IX_Oplata_przypisana_do_lokalu ON Oplaty (Nr_lokalu)
/

-- Add keys for table Oplaty

ALTER TABLE Oplaty ADD CONSTRAINT PK_Oplaty PRIMARY KEY (Nr_oplaty)
/

-- Table Czlonkowie_zarzadu

CREATE TABLE Czlonkowie_zarzadu(
  Nr_pracownika Integer NOT NULL,
  Od_kiedy Date NOT NULL,
  Do_kiedy Date NOT NULL,
  Rola Char(256 ) NOT NULL
        CHECK (Rola IN ('Prezes', 'Z-ca Prezesa', 'Członek Zarządu'))
)
/

-- Add keys for table Czlonkowie_zarzadu

ALTER TABLE Czlonkowie_zarzadu ADD CONSTRAINT Unique_Identifier11 PRIMARY KEY (Nr_pracownika,Od_kiedy)
/

-- Table Lokatorzy_Lokale

CREATE TABLE Lokatorzy_Lokale(
  Nr_lokatora Integer NOT NULL,
  Nr_lokalu Integer NOT NULL
)
/

-- Table Wlasciciele_Lokale

CREATE TABLE Wlasciciele_Lokale(
  Nr_wlasciciela Integer NOT NULL,
  Nr_lokalu Integer NOT NULL
)
/

-- Table Zgloszenia_Budynki

CREATE TABLE Zgloszenia_Budynki(
  Nr_zgloszenia Integer NOT NULL,
  Nr_budynku Integer NOT NULL
)
/

-- Table Zgloszenia_Lokale

CREATE TABLE Zgloszenia_Lokale(
  Nr_zgloszenia Integer NOT NULL,
  Nr_lokalu Integer NOT NULL
)
/

-- Table Adresy

CREATE TABLE Adresy(
  Nr_adresu Integer NOT NULL,
  Ulica Varchar2(50 ) NOT NULL,
  Nr_budynku Varchar2(5 ) NOT NULL,
  Nr_lokalu Varchar2(4 ),
  Miasto Varchar2(30 ) NOT NULL
)
/

-- Add keys for table Adresy

ALTER TABLE Adresy ADD CONSTRAINT PK_Adresy PRIMARY KEY (Nr_adresu)
/

-- Table and Columns comments section

COMMENT ON COLUMN Adresy.Nr_adresu IS 'Unikatowy identyfikator adresu'
/
COMMENT ON COLUMN Adresy.Ulica IS 'Ulica'
/
COMMENT ON COLUMN Adresy.Nr_budynku IS 'Numer budynku
'
/
COMMENT ON COLUMN Adresy.Nr_lokalu IS 'Numer lokalu
'
/
COMMENT ON COLUMN Adresy.Miasto IS 'Miasto'
/

-- Table Stanowiska

CREATE TABLE Stanowiska(
  Nr_stanowiska Integer NOT NULL,
  Nazwa_stanowiska Varchar2(20 ) NOT NULL,
  Opis Varchar2(400 ) NOT NULL
)
/

-- Add keys for table Stanowiska

ALTER TABLE Stanowiska ADD CONSTRAINT PK_Stanowiska PRIMARY KEY (Nr_stanowiska)
/

ALTER TABLE Stanowiska ADD CONSTRAINT Nazwa_stanowiska UNIQUE (Nazwa_stanowiska)
/

-- Table Rodzaje_budynkow

CREATE TABLE Rodzaje_budynkow(
  Nr_rodzaju Integer NOT NULL,
  Rodzaj Varchar2(50 ) NOT NULL,
  Opis Varchar2(200 ) NOT NULL
)
/

-- Add keys for table Rodzaje_budynkow

ALTER TABLE Rodzaje_budynkow ADD CONSTRAINT PK_Rodzaje_budynkow PRIMARY KEY (Nr_rodzaju)
/

ALTER TABLE Rodzaje_budynkow ADD CONSTRAINT Rodzaj UNIQUE (Rodzaj)
/

-- Table and Columns comments section

COMMENT ON COLUMN Rodzaje_budynkow.Rodzaj IS 'Rodzaj budynku'
/
COMMENT ON COLUMN Rodzaje_budynkow.Opis IS 'Opis rodzaju budynku'
/

-- Table Typy_zgloszen

CREATE TABLE Typy_zgloszen(
  Nr_typu Integer NOT NULL,
  Typ Varchar2(100 ) NOT NULL,
  Opis Varchar2(200 ) NOT NULL
)
/

-- Add keys for table Typy_zgloszen

ALTER TABLE Typy_zgloszen ADD CONSTRAINT PK_Typy_zgloszen PRIMARY KEY (Nr_typu)
/

ALTER TABLE Typy_zgloszen ADD CONSTRAINT Typ UNIQUE (Typ)
/

-- Table and Columns comments section

COMMENT ON COLUMN Typy_zgloszen.Nr_typu IS 'Numer identyfikujący typ zgłoszenia'
/
COMMENT ON COLUMN Typy_zgloszen.Typ IS 'Typ zgłoszenia'
/
COMMENT ON COLUMN Typy_zgloszen.Opis IS 'Opis typu zgłoszenia'
/

-- Table Wykonawcy

CREATE TABLE Wykonawcy(
  Nr_wykonawcy Integer NOT NULL,
  Nazwa Varchar2(200 ) NOT NULL,
  Opis Varchar2(200 ),
  Nr_adresu Integer NOT NULL
)
/

-- Create indexes for table Wykonawcy

CREATE INDEX IX_Wykonawca_ma_adres ON Wykonawcy (Nr_adresu)
/

-- Add keys for table Wykonawcy

ALTER TABLE Wykonawcy ADD CONSTRAINT PK_Wykonawcy PRIMARY KEY (Nr_wykonawcy)
/

-- Table and Columns comments section

COMMENT ON COLUMN Wykonawcy.Nazwa IS 'Nazwa wykonawcy'
/
COMMENT ON COLUMN Wykonawcy.Opis IS 'Opis wykonawcy'
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE Budynki ADD CONSTRAINT Zarzadza_budynkiem FOREIGN KEY (Nr_spoldzielni) REFERENCES 
  Spoldzielnie (Nr_spoldzielni)
/



ALTER TABLE Pracownicy ADD CONSTRAINT Zatrudnia FOREIGN KEY (Nr_spoldzielni) REFERENCES 
  Spoldzielnie (Nr_spoldzielni)
/



ALTER TABLE Oplaty ADD CONSTRAINT ma_oplate FOREIGN KEY (Nr_lokalu) REFERENCES 
  Lokale (Nr_lokalu)
/



ALTER TABLE Lokale ADD CONSTRAINT Budynek_posiada_lokal FOREIGN KEY (Nr_budynku) REFERENCES 
  Budynki (Nr_budynku)
/



ALTER TABLE Lokale ADD CONSTRAINT Zarzadza_lokalem FOREIGN KEY (Nr_spoldzielni) REFERENCES 
  Spoldzielnie (Nr_spoldzielni)
/



ALTER TABLE Zgloszenia ADD CONSTRAINT Obsluguje FOREIGN KEY (Nr_spoldzielni) REFERENCES 
  Spoldzielnie (Nr_spoldzielni)
/



ALTER TABLE Spoldzielnie ADD CONSTRAINT Spoldzielnia_ma_adres FOREIGN KEY (Nr_adresu) REFERENCES 
  Adresy (Nr_adresu)
/



ALTER TABLE Pracownicy ADD CONSTRAINT Pracownik_ma_adres FOREIGN KEY (Nr_adresu) REFERENCES 
  Adresy (Nr_adresu)
/



ALTER TABLE Budynki ADD CONSTRAINT Budynek_ma_adres FOREIGN KEY (Nr_adresu) REFERENCES 
  Adresy (Nr_adresu)
/



ALTER TABLE Wlasciciele ADD CONSTRAINT Wlasciciel_ma_adres FOREIGN KEY (Nr_adresu) REFERENCES 
  Adresy (Nr_adresu)
/



ALTER TABLE Pracownicy ADD CONSTRAINT ma_stanowisko FOREIGN KEY (Nr_stanowiska) REFERENCES 
  Stanowiska (Nr_stanowiska)
/



ALTER TABLE Budynki ADD CONSTRAINT Budynek_ma_rodzaj FOREIGN KEY (Nr_rodzaju) REFERENCES 
  Rodzaje_budynkow (Nr_rodzaju)
/



ALTER TABLE Zgloszenia ADD CONSTRAINT Zgloszenie_ma_typ FOREIGN KEY (Nr_typu) REFERENCES 
  Typy_zgloszen (Nr_typu)
/



ALTER TABLE Budynki ADD CONSTRAINT ma_wykonawce FOREIGN KEY (Nr_wykonawcy) REFERENCES 
  Wykonawcy (Nr_wykonawcy)
/



ALTER TABLE Wykonawcy ADD CONSTRAINT Wykonawca_ma_adres FOREIGN KEY (Nr_adresu) REFERENCES 
  Adresy (Nr_adresu)
/





