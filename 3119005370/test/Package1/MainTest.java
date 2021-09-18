package Package1;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void main()throws IOException {
        System.out.println("相似度：" + Main.Similarity("C:\\Users\\Eins\\Desktop\\test\\orig.txt","C:\\Users\\Eins\\Desktop\\test\\orig_0.8_add.txt"));
    }
}
//"C:\\Users\\Eins\\Desktop\\test\\orig.txt","C:\\Users\\Eins\\Desktop\\test\\orig_0.8_add.txt"