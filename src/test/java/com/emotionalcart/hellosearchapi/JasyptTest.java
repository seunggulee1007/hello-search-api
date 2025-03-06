package com.emotionalcart.hellosearchapi;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JasyptTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;

    @Test
    @DisplayName("암호화 테스트")
    void encrypt() {
        // given
        String text = "changeit";
        // when
        String encryptedText = jasyptStringEncryptor.encrypt(text);
        System.err.println(encryptedText);
        String decryptedText = jasyptStringEncryptor.decrypt(encryptedText);
        // then
        assertThat(text).isEqualTo(decryptedText);

    }

}
