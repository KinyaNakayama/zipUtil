package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        ArrayList<ZipUtil.ZipInDto> zipInDtoArrayList = new ArrayList<>();

        // 通常ファイル
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "test.txt",
                        "test Data\n 2 line".getBytes(StandardCharsets.UTF_8)
                )
        );

        // 巨大ファイル
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "big.txt",
                        String.join("\n", Collections.nCopies(100000, "test")).getBytes(StandardCharsets.UTF_8)
                )
        );

        // フォルダ階層
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "dir/test.txt",
                        "test Data\n 2 line".getBytes(StandardCharsets.UTF_8)
                )
        );
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "dir/test2.txt",
                        "test Data\n 2 line".getBytes(StandardCharsets.UTF_8)
                )
        );
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "dir2/test.txt",
                        "test Data\n 2 line".getBytes(StandardCharsets.UTF_8)
                )
        );
        zipInDtoArrayList.add(
                new ZipUtil.ZipInDto(
                        "dir3WithSubDirectory/sub1/sub2/sub3/test.txt",
                        "test Data\n 2 line".getBytes(StandardCharsets.UTF_8)
                )
        );

        //通常ファイル
        try {
            final byte[] result = ZipUtil.zip(
                    zipInDtoArrayList
            );
            Files.write(
                    Path.of("build/test.zip"),
                    result,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //パスワード付きzip
        try {
            final byte[] result = ZipUtil.zip(
                    zipInDtoArrayList,
                    "password"
            );
            Files.write(
                    Path.of("build/testWithPassword.zip"),
                    result,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}