package lancamento_de_produtos.utils;

import java.util.Random;

public class GeradorDeMatricula {
    
    public static String gerarMatricula(int quantidadeDigitos) {
        Random random = new Random();
        int min = (int) Math.pow(10, quantidadeDigitos - 1);
        int max = (int) Math.pow(10, quantidadeDigitos) - 1;
        int numero = min + random.nextInt(max - min + 1);
        return String.valueOf(numero);
    }
}
