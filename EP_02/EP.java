import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Itens{
    //atributos:
    int Px;
    int Py;
    int peso;
    int valor;

    //construtor:
    public Itens (int Px, int Py, int valor, int peso){
        this.Px = Px;
        this.Py = Py;
        this.valor = valor;
        this.peso = peso;
    }

}

public class EP {
    private static final int ANDA_RETO = 0;     //posicao livre, "."
    private static final int PAREDE = 1;        //posicao bloqueada, "X"

    public int[][] labirinto;
    private boolean[][] visitado;
    private Itens [][] itens;
    private int numItens;
    private Coordinate partida;
    private Coordinate chegada;

    public void inicilizaLabir(String inputFileName) throws FileNotFoundException {

        Scanner sc = new Scanner(new File(inputFileName));

        labirinto = new int[sc.nextInt()][sc.nextInt()];
        visitado = new boolean[getHeight()][getWidth()];

        while(sc.hasNext()) {
            sc.nextLine();
            for (int i = 0; i < getHeight(); i++) {
                String pegaSimbolo = sc.nextLine();
                String[] separaSimb = pegaSimbolo.split("");

                for (int j = 0; j < separaSimb.length; j++) {

                    if (separaSimb[j].equals(".")) {
                        labirinto[i][j] = ANDA_RETO;
                    }
                    if (separaSimb[j].equals("X")) {
                        labirinto[i][j] = PAREDE;
                    }
                }
            }

            numItens = sc.nextInt();
            itens = new Itens[getHeight()][getWidth()];
            for (int i = 0; i < numItens; i++) {
                Itens novo = new Itens(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                itens[novo.Px][novo.Py] = novo;
            }

            partida = new Coordinate(sc.nextInt(), sc.nextInt());
            chegada = new Coordinate(sc.nextInt(), sc.nextInt());
        }
    }


    public int getHeight() {
        return labirinto.length;
    }

    public int getWidth() {
        return labirinto[0].length;
    }

    public Coordinate getPartida() {
        return partida;
    }

    public Coordinate getChegada() {
        return chegada;
    }

    public Itens[][] getItens(){
        return itens;
    }

    public boolean ehChegada(int x, int y) {
        return x == chegada.getX() && y == chegada.getY();
    }

    public boolean foiExplorado(int x, int y) {
        return visitado[x][y];
    }

    public boolean ehParede(int x, int y) {
        return labirinto[x][y] == PAREDE;
    }

    public void setVisitado(int x, int y, boolean valorVerdade) {
        visitado[x][y] = valorVerdade;
    }

    public boolean ehPosicaoValida(int linhas, int colunas) {
        if (linhas < 0 || linhas >= getHeight() || colunas < 0 || colunas >= getWidth()) {
            return false;
        }
        return true;
    }

    public int calculaTamCaminho(String caminho){

        //se nao existe um caminho
        if(caminho.equals("")){
            return 0;
        }

        else {
            String[] separa = caminho.split("");
            int tamanho = (separa.length / 2) + 1;
            return tamanho;
        }
    }


    public int calculaNumItens(EP labirinto, String caminho){

        //a posicao de PARTIDA eh igual a CHEGADA
        if(labirinto.getPartida().getX() == labirinto.getChegada().getX() && labirinto.getPartida().getY() == labirinto.getChegada().getY()){
            caminho = Integer.toString(getPartida().getX()) + Integer.toString(getPartida().getY());
        }

        //se nao existe um caminho possivel
        if(caminho.equals("")){
            return 0;
        }

        int cont = 0;
        int numItens = 0;
        String[] separa = caminho.split("");

        //enquanto nao percorrer todas as posicoes do caminho
        while (cont < separa.length) {
            if (labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont + 1])] != null) {
                //soma os pesos dos itens coletados no caminho
                numItens++;
            }
            cont = cont + 2;
        }
        return numItens;
    }

    public void printPosItens(EP labirinto, String caminho){

        //a posicao de PARTIDA eh igual a CHEGADA
        if(labirinto.getPartida().getX() == labirinto.getChegada().getX() && labirinto.getPartida().getY() == labirinto.getChegada().getY()){
            caminho = Integer.toString(getPartida().getX()) + Integer.toString(getPartida().getY());
        }
        int cont = 0;
        String[] separa = caminho.split("");

        //enquanto nao percorrer todas as posicoes do caminho
        while (cont < separa.length) {
            if (labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont + 1])] != null) {
                System.out.println(separa[cont] + " " + separa[cont + 1]);
            }
            cont = cont + 2;
        }
    }

    public void printPosCaminho(String caminho){

        //se nao existe um caminho possivel
        if(caminho.equals("")){
            return;
        }

        String [] separa = caminho.split("");
        int cont = 0;
        while(cont < separa.length){
            System.out.println(separa[cont] + " " + separa[cont+1]);
            cont = cont + 2;
        }
    }


    public int calculaPeso(EP labirinto, String caminho) {

        //a posicao de PARTIDA eh igual a CHEGADA
        if(labirinto.getPartida().getX() == labirinto.getChegada().getX() && labirinto.getPartida().getY() == labirinto.getChegada().getY()){
            caminho = Integer.toString(getPartida().getX()) + Integer.toString(getPartida().getY());
        }

        //se nao existe um caminho possivel
        if(caminho.equals("")){
            return 0;
        }

        int cont = 0;
        int peso = 0;
        String[] separa = caminho.split("");

        //enquanto nao percorrer todas as posicoes do caminho
        while (cont < separa.length) {
            if (labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont + 1])] != null) {
                //soma os pesos dos itens coletados no caminho
                peso = peso + labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont + 1])].peso;
            }
            cont = cont + 2;
        }
        return peso;
    }


    public int calculaValor(EP labirinto, String caminho){

        //a posicao de PARTIDA eh igual a CHEGADA
        if(labirinto.getPartida().getX() == labirinto.getChegada().getX() && labirinto.getPartida().getY() == labirinto.getChegada().getY()){
            caminho = Integer.toString(getPartida().getX()) + Integer.toString(getPartida().getY());
        }

        //se nao existe um caminho possivel
        if(caminho.equals("")){
            return 0;
        }

        int soma = 0;
        int cont = 0;
        String[] separa = caminho.split("");

        while(cont < separa.length){
            if(labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])] != null){
                soma = soma + labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])].valor;
            }
            cont = cont + 2;
        }

        return soma;
    }


    public double calculaTempo(EP labirinto, String caminho){

        //se nao existe um caminho possivel
        if(caminho.equals("")){
            return 0;
        }

        int peso = 0;
        double tempo = 0;
        double acrescimo = Math.pow((1+(peso)/10.0), 2);

        String [] separa = caminho.split("");

        int cont = 0;

        //enquanto nao percorrer todas as posicoes do caminho
        while(cont < separa.length){

            //se a posicao tem um ITEM
            if(labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])] != null){
                tempo = tempo + acrescimo;
                peso = peso + labirinto.getItens()[Integer.parseInt(separa[cont])][Integer.parseInt(separa[cont+1])].peso;
                acrescimo = Math.pow((1+(peso/10.0)), 2);
                cont = cont + 2;
            }
            else {
                tempo = tempo + acrescimo;
                cont = cont + 2;
            }
        }
        return tempo; //atualiza variavel que guarda o TEMPO GASTO para percorrer o caminho
    }


    private static void cmc(EP labirinto, boolean[][]visitado) {  //caminhoMaisCurto
        Caminhos novo = new Caminhos();
        String caminho = novo.caminhoMaisCurto(labirinto, visitado);

        labirinto.calculaTempo(labirinto, caminho);

        System.out.println(labirinto.calculaTamCaminho(caminho) + " " + labirinto.calculaTempo(labirinto, caminho));

        //se o tamanho do caminho for diferente de ZERO
        if(labirinto.calculaTamCaminho(caminho) != 0) {
            System.out.println(labirinto.getPartida().getX() + " " + labirinto.getPartida().getY());
        }

        labirinto.printPosCaminho(caminho);

        System.out.println(labirinto.calculaNumItens(labirinto, caminho) + " " + labirinto.calculaValor(labirinto, caminho) + " " + labirinto.calculaPeso(labirinto,caminho));

        if(labirinto.calculaNumItens(labirinto, caminho) != 0){
            labirinto.printPosItens(labirinto, caminho);
        }

    }

    private static void cml(EP labirinto, boolean[][]visitado) {  //caminhoMaisLongo
        Caminhos novo = new Caminhos();
        String caminho = novo.caminhoMaisLongo(labirinto, visitado);

        labirinto.calculaTempo(labirinto, caminho);

        System.out.println(labirinto.calculaTamCaminho(caminho) + " " + labirinto.calculaTempo(labirinto, caminho));

        //se o tamanho do caminho for diferente de ZERO
        if(labirinto.calculaTamCaminho(caminho) != 0) {
            System.out.println(labirinto.getPartida().getX() + " " + labirinto.getPartida().getY());
        }

        labirinto.printPosCaminho(caminho);

        System.out.println(labirinto.calculaNumItens(labirinto, caminho) + " " + labirinto.calculaValor(labirinto, caminho) + " " + labirinto.calculaPeso(labirinto,caminho));

        if(labirinto.calculaNumItens(labirinto, caminho) != 0){
            labirinto.printPosItens(labirinto, caminho);
        }
    }

    private static void cmv(EP labirinto, boolean[][]visitado) {  //caminhoMaisValioso
        Caminhos novo = new Caminhos();
        String caminho = novo.caminhoMaisValioso(labirinto, visitado);

        labirinto.calculaTempo(labirinto, caminho);

        System.out.println(labirinto.calculaTamCaminho(caminho) + " " + labirinto.calculaTempo(labirinto, caminho));

        //se o tamanho do caminho for diferente de ZERO
        if(labirinto.calculaTamCaminho(caminho) != 0) {
            System.out.println(labirinto.getPartida().getX() + " " + labirinto.getPartida().getY());
        }

        labirinto.printPosCaminho(caminho);

        System.out.println(labirinto.calculaNumItens(labirinto, caminho) + " " + labirinto.calculaValor(labirinto, caminho) + " " + labirinto.calculaPeso(labirinto,caminho));

        if(labirinto.calculaNumItens(labirinto, caminho) != 0){
            labirinto.printPosItens(labirinto, caminho);
        }
    }

    private static void cmr(EP labirinto, boolean[][]visitado) {  //caminhoMaisRapido
        Caminhos novo = new Caminhos();
        String caminho = novo.caminhoMaisRapido(labirinto, visitado);

        labirinto.calculaTempo(labirinto, caminho);

        System.out.println(labirinto.calculaTamCaminho(caminho) + " " + labirinto.calculaTempo(labirinto, caminho));

        //se o tamanho do caminho for diferente de ZERO
        if(labirinto.calculaTamCaminho(caminho) != 0) {
            System.out.println(labirinto.getPartida().getX() + " " + labirinto.getPartida().getY());
        }

        labirinto.printPosCaminho(caminho);

        System.out.println(labirinto.calculaNumItens(labirinto, caminho) + " " + labirinto.calculaValor(labirinto, caminho) + " " + labirinto.calculaPeso(labirinto,caminho));

        if(labirinto.calculaNumItens(labirinto, caminho) != 0){
            labirinto.printPosItens(labirinto, caminho);
        }
    }



    public static void main (String[]args) throws FileNotFoundException {
        EP novo = new EP();
        novo.inicilizaLabir(args[0]);
        int criteria = Integer.parseInt(args[1]);
        
        if(criteria == 1){
            cmc(novo, novo.visitado);  //caminho mais CURTO
            return;
        }

        if(criteria == 2){
            cml(novo, novo.visitado);  //caminho mais LONGO
            return;
        }

        if(criteria == 3){
            cmv(novo, novo.visitado);  //caminho mais VALIOSO
            return;
        }

        if(criteria == 4) {
            cmr(novo, novo.visitado);  //caminho mais RAPIDO
            return;
        }

    }
}