package org.example;

import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ZipUtil {

    static class ZipInDto {

        private final String path;
        private final byte[] data;

        ZipInDto(String path, byte[] data) {
            this.path = path;
            this.data = data;
        }

        public String getPath() {
            return path;
        }

        public byte[] getData() {
            return data;
        }
    }

    private ZipUtil() {
    }

    public static byte[] zip(
            List<ZipInDto> zipInDtoList,
            String password
    ) {

        final char[] passwordCharArray;
        final ZipParameters zipParameters = new ZipParameters();
        if (password != null) {
            passwordCharArray = password.toCharArray();
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.AES);
            zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        } else {
            passwordCharArray = null;
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(
                    byteArrayOutputStream,
                    passwordCharArray,
                    StandardCharsets.UTF_8
            )) {
                for (ZipInDto zipInDto : zipInDtoList) {
                    zipParameters.setFileNameInZip(
                            zipInDto.getPath()
                    );
                    zipOutputStream.putNextEntry(
                            zipParameters
                    );
                    zipOutputStream.write(zipInDto.getData());
                    zipOutputStream.closeEntry();
                }
                zipOutputStream.flush();
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] zip(
            List<ZipInDto> zipInDtoList
    ) {
        return ZipUtil.zip(zipInDtoList, null);
    }

}
