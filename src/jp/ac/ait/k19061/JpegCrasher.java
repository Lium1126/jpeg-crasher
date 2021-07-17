package jp.ac.ait.k19061;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class JpegCrasher {
    /**
     * メインメソッド
     */
    public static void main(String[] args) throws IOException {
        crash("Baboon.jpg", "Baboon_out.jpg", 50);
    }

    /**
     * 入力されたファイルのランダムな1Byteをランダムな1bit加算で置き換えることでファイルをクラッシュさせる
     * @param input_filePath
     * @param output_filePath
     * @param challenge
     */
    public static void crash(
            String input_filePath,
            String output_filePath,
            int challenge) throws IOException {

        // jpegファイルを壊す

        // バイナリファイルの読み出し
        byte[] out;
        try {
            out = Files.readAllBytes(Path.of(input_filePath));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        for (int i = 0; i < challenge; i += 1) {
            // ランダムな1ビットだけのデータを取得する
            byte value = (byte) (1 << (new Random().nextInt(8)));

            // 1bitの値を加算して0xFFでAND演算することで1bitの変化を加える
            int randomIndex = new Random().nextInt(out.length);
            out[randomIndex] = (byte) ((int) out[randomIndex] + (int) value & 0xFF);

            // 指定された出力パスにバイト配列を出力
            Files.write(Path.of(output_filePath), out, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
