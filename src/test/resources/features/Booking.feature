Feature: Kullanici olarak otel rezervasyonu olusturabilirim

  Background:
    Given Kullanici yeni bir rezervasyon olusturuyor
    And Kullanici rezervasyon icin gereken bilgileri veriyor
    When Kullanici otel rezervasyonu yaratiyor
    Then Rezervasyon basarili sekildi olusturuldu

  Scenario: Kullanici bir otel rezervasyonu olusturabilir ve rezervasyonu silebilir
    And Kullanici olustulan rezervasyonu iptal ediyor
    Then Rezervasyon basarili sekilde silindi

    Scenario: Kullanici Rezervasyonu güncelleyebilir
      And  Kullanici olusturulan rezervasyonu güncelliyor
      Then Rezervasyon basarili sekilde guncellendi

    Scenario: Kullanici rezervasyonu kismi olarak guncelleyebilir
      And Kullanici rezervasyonu kismi olarak guncelliyor
      Then Sadece guncellenen alanlar degismis olmali