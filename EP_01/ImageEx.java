
/*********************************************************************/
/**   ACH2002 - Introducao a Analise de Algoritmos                  **/
/**                                                                 **/
/**   <turma 94> - Prof. Flavio Luiz Coutinho                       **/
/**                                                                 **/
/**   EP1 - Recursividade aplicada a computacao grafica             **/
/**                                                                 **/
/**   <Aluna: Melissa Akie Inui>        <N.USP: 11908865>           **/
/**                                                                 **/
/*********************************************************************/


// Classe da qual sao implementadas as novas funcionalidades de desenho
public class ImageEx extends Image {

    public ImageEx(int w, int h, int r, int g, int b){

        super(w, h, r, g, b);
    }

    public ImageEx(int w, int h){

        super(w, h);
    }


    public boolean rgbValido(int rgb) {

        //Verifica se a componente R, G ou B passado no parametro esta dentro do intervalo [0, 250]

        if(rgb >= 0 && rgb<=250){
            return true;
        }
        return false;
    }


    public void kochCurve(int Px, int Py, int Qx, int Qy, int l){
        /******************************
         Px: coordenada X do ponto P
         Py: coordenada Y do ponto P
         Qx: coordenada X do ponto Q
         Qy: coordenada Y do ponto Q
         l: valor limiar
         ******************************/

        //Comprimento do segmento PQ:
        double c = Math.sqrt((Math.pow(Qx - Px, 2) + Math.pow(Qy - Py, 2)));

        if (c < l) {
            drawLine(Px, Py, Qx, Qy);
        }

        else {

            //Calculo das coordenadas x e y dos pontos A, B e C:
            double deltaXpq = Qx - Px;
            double deltaYpq = Qy - Py;

            double Ax = Px + deltaXpq/3;
            double Ay = Py + deltaYpq/3;
            double Cx = Px + 2 * deltaXpq/3;
            double Cy = Py + 2 * deltaYpq/3;
            double Bx = (0.5 * (Px+Qx) + Math.sqrt(3) * (Py-Qy)/6);
            double By = (0.5 * (Py+Qy) + Math.sqrt(3) * (Qx-Px)/6);

            //Arredondamento dos valores das coordenadas:
            double ax = Math.round(Ax);
            double ay = Math.round(Ay);
            double cx = Math.round(Cx);
            double cy = Math.round(Cy);
            double bx = Math.round(Bx);
            double by = Math.round(By);

            //Chamada recursiva do metodo para os segmentos PA, AB, BC e CQ:
            kochCurve(Px, Py,(int)ax, (int)ay, l);
            kochCurve((int)ax, (int)ay, (int)bx, (int)by, l);
            kochCurve((int)bx, (int)by, (int)cx, (int)cy, l);
            kochCurve((int)cx, (int)cy, Qx, Qy, l);
        }
    }


    public boolean ehValido(int x, int y, int altura, int largura) {

        //Verifica se as coordenadas X e Y sao validas

        if(x >= 0 && y >= 0 && x < largura && y < altura){
            return true;
        }
        return false;
    }

    public void regionFill(int x, int y, int reference_rgb){

        int cont = 0;

        //Pinta uma linha horizontal a DIREITA do ponto inicial ate encontrar uma "parede":
        if(ehValido(x, y, this.getHeight(), this.getWidth())) {
            while (getPixel(x + cont, y) == reference_rgb) {
                setPixel(x + cont, y);

                //Verifica se o pixel acima necessita ser colorido:
                if(ehValido(x+cont, y-1, this.getHeight(),this.getWidth())) {
                    if (getPixel(x + cont, y - 1) == reference_rgb) {
                        regionFill(x + cont, y - 1, reference_rgb);
                    }
                }

                //Verifica se o pixel abaixo necessita ser colorido:
                if(ehValido(x+cont, y+1, this.getHeight(), this.getWidth())) {
                    if (getPixel(x + cont, y + 1) == reference_rgb) {
                        regionFill(x + cont, y + 1, reference_rgb);
                    }
                }

                cont++;
                if(!ehValido(x+cont, y, this.getHeight(), this.getWidth())) {
                    return;
                }
            }

            cont = 1;

            if(ehValido(x-cont, y, this.getHeight(), this.getWidth())) {

                //Pinta uma linha horizontal a ESQUERDA do ponto inicial ate encontrar uma "parede":
                while (getPixel(x - cont, y) == reference_rgb) {
                    setPixel(x - cont, y);

                    //Verifica se o pixel de cima necessita ser colorido:
                    if (ehValido(x - cont, y - 1, this.getHeight(), this.getWidth())) {
                        if (getPixel(x - cont, y - 1) == reference_rgb) {
                            regionFill(x - cont, y - 1, reference_rgb);
                        }
                    }

                    //Verifica se o pixel de baixo necessita ser colorido:
                    if (ehValido(x - cont, y + 1, this.getHeight(), this.getWidth())) {
                        if (getPixel(x - cont, y + 1) == reference_rgb) {
                            regionFill(x - cont, y + 1, reference_rgb);
                        }
                    }
                    cont++;
                    if (!ehValido(x - cont, y, getHeight(), getWidth())) {
                        return;
                    }
                }
            }
        }
    }
}

