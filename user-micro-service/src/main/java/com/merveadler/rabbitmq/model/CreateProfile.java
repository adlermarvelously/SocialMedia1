package com.merveadler.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ÇOOOOOOOOOOOOK ÖNEMLİ!!!!
 * rabbitmq gibi yapılar sınıfları iletirkeb base64'e çevirir. bu nedenle
 * bu sınıfları tanımlarken serileştirme işlemlerini yapmamız gerekir. ayrıca
 * dikkat edilmesi gereken diğer husus serileştirilen sınıfların iletilen
 * tarafta serileştirme işlemini tersine işletebilmesi için aynı paket adı içinde
 * tanımlanmış olması gereklidir. aksi halde hata alınır.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProfile implements Serializable {
    Long authid;
    String username;
    String email;
}
