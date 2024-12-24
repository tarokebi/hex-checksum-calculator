import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- App started ... ---");
        BufferedReader br = null;
        BufferedWriter bw = null;

        String srcFile = "input.txt";
        String destFile = "output.txt";

        try {
            // Open stream
            br = new BufferedReader(new FileReader(srcFile));
            bw = new BufferedWriter(new FileWriter(destFile));

            // Read file
            String input = br.readLine();

            // ---------------- Calc checksum----------------
            String[] result = new String[input.length() / 2];

            for (int i = 0; i < input.length(); i += 2) {
                result[i / 2] = input.substring(i, i + 2);
            }

            int sum = 0;

            // 16進数への変換と合計
            for (String hex : result) {
                // 16進数文字列を整数に変換
                int value = Integer.parseInt(hex, 16);
                sum += value;
            }

            // チェックサム（補数）の計算
            // 1. 合計を16進数の2桁（1バイト）に制限
            sum = sum & 0xFFFF;
            // 2. 補数を計算（2の補数）
            int checksum = ((~sum + 1) & 0xFFFF);

            System.out.println("Sum: 0x" + String.format("%02X", sum)); // 合計
            System.out.println("Checksum: 0x" + String.format("%02X", checksum)); // チェックサム

            // .--------------------------------

            // Write
            bw.write(String.valueOf(String.format("%02X", checksum)));
            bw.newLine(); // OS に合わせた改行コード
            bw.flush(); // 出力バッファ --> ファイル
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
