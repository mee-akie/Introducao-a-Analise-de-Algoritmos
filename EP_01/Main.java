import java.util.*;
import java.io.*;

// Programa principal
public class Main {

    public static void generateImage(String inputFileName, String outputFileName) throws IOException {

        Scanner scanner = new Scanner(new File(inputFileName));

        ImageEx image = new ImageEx(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        while(scanner.hasNext()){

            String command = scanner.next();

            if(command.equals("SET_COLOR")){

                int r = scanner.nextInt();
                int g = scanner.nextInt();
                int b = scanner.nextInt();

                if(image.rgbValido(r) == false){
                    break;
                }
                if(image.rgbValido(g) == false){
                    break;
                }
                if(image.rgbValido(b) == false){
                    break;
                }
                image.setColor(r, g, b);
            }

            if(command.equals("SET_PIXEL")){
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if(image.ehValido(x, y, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto eh invalida
                    break;
                }

                image.setPixel(x, y);
            }

            if(command.equals("DRAW_LINE")){
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                if(image.ehValido(x2, y2, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto eh invalida
                    break;
                }
                if(image.ehValido(x1, y1, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto eh invalida
                    break;
                }
                image.drawLine(x1, y1, x2, y2);
            }

            if(command.equals("KOCH_CURVE")){

                int Px = scanner.nextInt();
                int Py = scanner.nextInt();
                int Qx = scanner.nextInt();
                int Qy = scanner.nextInt();
                int l = scanner.nextInt();

                if(image.ehValido(Px, Py, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto P invalida
                    break;

                }
                if(image.ehValido(Qx, Qy, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto Q invalida
                    break;
                }
                if(l<0){
                    break;
                }
                else {
                    image.kochCurve(Px, Py, Qx, Qy, l);
                }
            }

            if(command.equals("REGION_FILL")){

                int x = scanner.nextInt();
                int y = scanner.nextInt();
                if(image.ehValido(x, y, image.getHeight(), image.getWidth()) == false){ //coordenada do ponto eh invalida
                    break;
                }

                image.regionFill(x, y, image.getPixel(x, y));
            }

        }

        image.save(outputFileName);
    }

    public static void main(String [] args){


        if(args.length != 2){

            System.out.println("Uso: java " + Main.class.getName() + " entrada.txt saida.png");
            System.exit(1);
        }

        try{
            generateImage(args[0], args[1]);
        }
        catch(IOException e){

            System.out.println("Problem generating image... :(");
            e.printStackTrace();
        }
    }
}