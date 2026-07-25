# 💱 Kalkulator Walutowy NBP i Ukrytych Kosztów

Aplikacja webowa napisana w Spring Boot 3 oraz Thymeleaf, która łączy się z oficjalnym API Narodowego Banku Polskiego (NBP), aby przeliczać waluty oraz analizować ukryte koszty wymiany (spready i prowizje).

---

## 🚀 Funkcje
- Pobieranie kursów NBP w czasie rzeczywistym: Pobiera oficjalne kursy średnie, kupna oraz sprzedaży (Tabele A i C) bezpośrednio z API NBP.
- Analiza ukrytych kosztów: Wylicza dokładny koszt (w PLN) wynikający ze spreadu walutowego oraz prowizji bankowych.
- Przeliczanie walut: Obsługuje bezpośrednie wymiany pomiędzy walutami zagranicznymi (EUR, USD, CHF, GBP itp.) oraz PLN.
- Kalkulator marży: Pozwala symulować własną prowizję banku lub kantoru.
- Nowoczesny interfejs: Ciemny motyw stworzony z wykorzystaniem Bootstrap 5.

---

## 🛠️ Stos Technologiczny
- Java 21
- Spring Boot 3.3 (Spring Web, Spring MVC)
- Thymeleaf (Silnik szablonów)
- Jackson Databind (Parsowanie JSON)
- Bootstrap 5 (Stylistyka UI)
- Maven

---

## 📂 Architektura Projektu
- CalculatorApplication.java – Główny punkt startowy aplikacji Spring Boot.
- NbpApiService.java – Serwis odpowiedzialny za pobieranie i przetwarzanie danych JSON z API NBP.
- CalculatorController.java – Kontroler obsługujący zapytania HTTP i logikę kalkulatora.
- calculator.html – Widok HTML renderowany przez silnik Thymeleaf.

---

## 💻 Jak uruchomić projekt lokalnie

1. Sklonuj repozytorium na swój dysk.
```bash
git clone [https://github.com/TWOJ_NICK/calculator.git](https://github.com/TWOJ_NICK/calculator.git)
cd calculator
```
2. Uruchom aplikację za pomocą skryptu Maven Wrapper (mvnw / mvnw.cmd).
```bash
./mvnw spring-boot:run
```
3. Otwórz przeglądarkę i wejdź pod adres:
```bash
http://localhost:8080
```
